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
    NodeAddress myAdress;
    boolean connectedToNetwork = false;

    TreeSet<NodeAddress> links;

    // Lista dos listeners do nó
    List<NodeEventListener> listeners;

    public LocalNetworkListener(TreeSet<NodeAddress> links, NodeAddress myAddress, List<NodeEventListener> listeners) throws Exception
    {
        this.listeners = listeners;
        this.myAdress = myAddress;
        this.myAdress.setUDP_Port(MULTICAST_PORT);
        this.links = links;

        listener = new MulticastSocket(MULTICAST_PORT);
        listener.joinGroup(InetAddress.getByName(MULTICAST_IP));

        // enviar uma mensagem para o grupo a pedir conexão
        MessageUDP msg = new MessageUDP(MessageUDP.CONNECT, myAdress);
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
        MessageUDP msg = new MessageUDP("OK", myAdress);
        msg.sendUDP(
                myAdress.getIP(),
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
                NodeAddress nodeAdress = (NodeAddress) msg.getContent();

                switch ( msg.getType() )
                {
                    case MessageUDP.CONNECT:

                        // Adiciona o novo nodo à lista de nodos na rede
                        if ( !links.contains(nodeAdress) )
                        {
                            links.add(nodeAdress);

                            msg.setContent(myAdress);
                            msg.sendUDP(MULTICAST_IP, MULTICAST_PORT);

                            NodeEventListener.notifyConnect(listeners, nodeAdress);
                        }
                        break;

                    case MessageUDP.DISCONNECT:
                        
                        // Retira o mesmo da lista da rede.
                        links.remove(nodeAdress);

                        NodeEventListener.notifyDisconnect(listeners, nodeAdress);
                        break;

                    case MessageUDP.SYNC_BLOCKCHAIN_INFO:

                        // Vai dar override ao antigo porque este contem informação nova da blockChain
                        links.add(myAdress);
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
