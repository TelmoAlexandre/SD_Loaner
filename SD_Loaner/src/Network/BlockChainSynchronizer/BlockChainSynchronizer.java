/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.BlockChainSynchronizer;

import BlockChain.Block;
import BlockChain.BlockChain;
import Network.Message.Message;
import Network.Message.MessageUDP;
import Network.NodeAddress;
import Network.ServersListeners.LocalNetworkListener;
import Network.SocketManager;
import static java.lang.Thread.sleep;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe que partilha as informações da blockChain local com a rede.
 *
 * @author Telmo
 */
public class BlockChainSynchronizer extends Thread
{

    NodeAddress nodeAddress;
    TreeSet<NodeAddress> network;
    BlockChain blockChain;
    AtomicBoolean tcpListening;

    public BlockChainSynchronizer(
            NodeAddress nodeAddress, TreeSet<NodeAddress> network,
            BlockChain blockChain)
    {
        this.nodeAddress = nodeAddress;
        this.network = network;
        this.blockChain = blockChain;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                // 5 minutos
                sleep(30_0000);

                // Partilha o nodeAddress com a rede
                sendNodeAddresToNetwork(nodeAddress);

                // Verifica se existe algum nó na rede que contenha uma blockChain melhor que a local
                // Caso exista, inicia um processo de sincronização com esse nó
                checkRemoteBlockChains();
            }
        }
        catch (Exception ex)
        {
            System.out.println("BlockChainSynchronizer - Erro");
        }
    }

    public void checkRemoteBlockChains()
    {
        // Percorre a informação que este nó tem sobre as blockChains da rede
        for (NodeAddress address : network)
        {
            // Individualiza o BlockChainInfo do nó da rede em questão
            BlockChainInfo chainInfo = address.getBlockChainInfo();

            // Caso a blockChain do nó da rede seja melhor que a local
            if (chainInfo.isBetterThan(nodeAddress.getBlockChainInfo()))
            {
                synchronizeBlockChain(address);
                return;
            }
        }
    }

    private void synchronizeBlockChain(NodeAddress address)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    // Cria o socket
                    SocketManager socket = new SocketManager(address.getIP(), address.getTCP_Port());

                    // Envia uma mensagem a anunciar uma sincronização de BlockChain
                    // Envia também o ultimo bloco da chain, para que o nó remoto
                    // saiba onde começar a enviar blocos
                    Message msg = new Message(Message.SYNC_BLOCKCHAIN_INFO, blockChain.getLast());
                    socket.sendObject(msg);

                    while (true)
                    {
                        Block block = (Block) socket.readObject();

                        // Enquanto receber blocos instanciados
                        if (block != null)
                        {
                            blockChain.addMinedBlock(block);
                        }
                        else
                        {
                            // Quando chegar um null, acaba a sincronização
                            // porque chegamos ao fim da chain do nó remoto
                            socket.close();
                            break;
                        }
                    }
                }
                catch (Exception ex)
                {
                    System.out.println("Erro na sincronização - Receiver");
                }

            }
        }.start();
    }

    public static void sendNodeAddresToNetwork(NodeAddress nodeAddress) throws Exception
    {
        // Partilha o nodeAddres com a rede
        MessageUDP msg = new MessageUDP(MessageUDP.SYNC_BLOCKCHAIN_INFO, nodeAddress);
        msg.sendUDP(LocalNetworkListener.MULTICAST_IP, LocalNetworkListener.MULTICAST_PORT);
    }
}
