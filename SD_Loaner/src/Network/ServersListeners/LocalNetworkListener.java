/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.ServersListeners;

import Network.Message.MessageUDP;
import Network.NodeAddress;
import Network.NodeEventListener;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;
import java.util.TreeSet;

/**
 * Fica à escuta de alguem que se junte à rede. Quando recebe uma nova ligação,
 * retorna o seu endereço.
 *
 *
 * @author dell
 */
public class LocalNetworkListener extends Thread
{

    public static final String MULTICAST_IP = "230.0.0.0";
    public static final int MULTICAST_PORT = 4321;

    MulticastSocket listener;
    NodeAddress myAddress;
    boolean connectedToNetwork = false;

    TreeSet<NodeAddress> network;

    // Lista dos listeners do nó
    List<NodeEventListener> listeners;

    public LocalNetworkListener(TreeSet<NodeAddress> network, NodeAddress myAddress, List<NodeEventListener> listeners) throws Exception
    {
        this.listeners = listeners;
        this.myAddress = myAddress;
        this.myAddress.setUDP_Port(MULTICAST_PORT);
        this.network = network;

        listener = new MulticastSocket(MULTICAST_PORT);
        listener.joinGroup(InetAddress.getByName(MULTICAST_IP));

        // enviar uma mensagem para o grupo a pedir conexão
        MessageUDP msg = new MessageUDP(MessageUDP.CONNECT, this.myAddress);
        msg.sendUDP(MULTICAST_IP, MULTICAST_PORT);

        // Encontra-se conectado à rede
        connectedToNetwork = true;
    }

    /**
     * Termina a Thread de escuta na rede.
     *
     * @throws java.lang.Exception
     */
    public void disconnect() throws Exception
    {
        // Isto vai fazer a condição do loop da Thread ser falso, logo termina a Thread
        this.connectedToNetwork = false;

        // Envia uma mensagem UDP a si mesmo para forçar a Thread sair da instrução bloqueante de escuta.
        MessageUDP msg = new MessageUDP("OK", myAddress);
        msg.sendUDP(myAddress.getIP(),
                MULTICAST_PORT
        );
    }

    @Override
    public void run()
    {
        while ( connectedToNetwork )
        {
            try
            {
                // Receber um pacote
                MessageUDP msg = MessageUDP.receiveUDP(listener);

                // Isolar o NodeAddress                  
                NodeAddress receivedNodeAdress = (NodeAddress) msg.getContent();

                switch ( msg.getType() )
                {
                    case MessageUDP.CONNECT:

                        // Adiciona o novo nodo à lista de nodos na rede
                        if ( !network.contains(receivedNodeAdress) )
                        {
                            network.add(receivedNodeAdress);

                            msg.setContent(myAddress);
                            msg.sendUDP(MULTICAST_IP, MULTICAST_PORT);

                            NodeEventListener.notifyConnect(listeners, receivedNodeAdress);
                        }
                        break;

                    case MessageUDP.DISCONNECT:

                        // Retira o mesmo da lista da rede.
                        network.remove(receivedNodeAdress);

                        NodeEventListener.notifyDisconnect(listeners, receivedNodeAdress);
                        break;

                    case MessageUDP.SYNC_BLOCKCHAIN_INFO:

                        // Vai dar override ao BlockChainInfo antigo do nó remoto, porque este contem informação nova da blockChain
                        for ( NodeAddress remoteAddress : network )
                        {
                            if ( remoteAddress.getIP().equals(receivedNodeAdress.getIP()) && remoteAddress.getTCP_Port() == receivedNodeAdress.getTCP_Port() )
                            {
                                remoteAddress.setBlockChainInfo(
                                        receivedNodeAdress.getBlockChainInfo()
                                );
                                break;
                            }
                        }

                        break;

                    default:
                        break;
                }
            }
            catch ( Exception ex )
            {
                System.out.println(ex.getMessage());
            }
        }
    }
}
