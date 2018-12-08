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

    NodeAddress myNodeAddress;
    TreeSet<NodeAddress> network;
    BlockChain blockChain;
    AtomicBoolean tcpListening;

    public BlockChainSynchronizer(
            NodeAddress myNodeAddress, TreeSet<NodeAddress> network,
            BlockChain blockChain)
    {
        this.myNodeAddress = myNodeAddress;
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
                sleep(300_000);

                // Partilha o myNodeAddress com a rede
                sendNodeAddresToNetwork(myNodeAddress);

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
        // Incialmente, considera-se a melhor blockChain
        NodeAddress bestBlockChain = myNodeAddress;

        // Percorre a informação que este nó tem sobre as blockChains da rede
        for (NodeAddress address : network)
        {
            // Salta a verificação consigo mesmo
            if (address.getBlockChainInfo() != myNodeAddress.getBlockChainInfo())
            {
                // Individualiza o BlockChainInfo do nó da rede em questão
                BlockChainInfo chainInfo = address.getBlockChainInfo();

                // Caso a blockChain do nó da rede seja melhor do que 
                // é currentemente considerada melhor
                if (chainInfo.isBetterThan(bestBlockChain.getBlockChainInfo()))
                {
                    bestBlockChain = address;
                }
            }
        }

        // Sincroniza com a melhor blockChain
        if (bestBlockChain != null)
        {
            synchronizeBlockChain(bestBlockChain);
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
                    
                    // Alertar a rede da corrupção
                    blockChain.alertNetworkAboutCorruptedBlockChain();
                }
                catch (Exception ex)
                {
                    System.out.println("Erro na sincronização - Receiver\n");
                    System.out.println(ex.getMessage()+"\n\n");
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
