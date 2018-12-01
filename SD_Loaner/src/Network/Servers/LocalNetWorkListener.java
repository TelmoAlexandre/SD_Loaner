/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Servers;

import Network.Message.MessageUDP;
import Network.NodeAddress;
import Network.NodeEventListener;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Fica à escuta de alguem que se junte à rede. Quando recebe uma nova ligação, retorna o seu endereço.
 * 
 * 
 * @author dell
 */
public class LocalNetWorkListener extends Thread
{

    public static final String MULTICAST_IP = "230.0.0.0";
    public static final int MULTICAST_PORT = 4321;

    MulticastSocket listener;
    NodeAddress myAdress;

    TreeSet<NodeAddress> links;

    // Lista dos listeners do nó
    List<NodeEventListener> listeners;

    public LocalNetWorkListener(TreeSet<NodeAddress> links, NodeAddress myAddress, List<NodeEventListener> listeners) throws Exception
    {
        this.listeners = listeners;
        this.myAdress = myAddress;
        this.links = links;

        listener = new MulticastSocket(MULTICAST_PORT);
        listener.joinGroup(InetAddress.getByName(MULTICAST_IP));

        //::::::::::::::::::: T O   P R O G R A M M I N G:::::::::::::::::::::::: 
        // enviar uma mensagem para o grupo a pedir conexão
        MessageUDP msg = new MessageUDP(MessageUDP.CONNECT, myAdress);
        msg.sendUDP(MULTICAST_IP, MULTICAST_PORT);
    }

    @Override
    public void run()
    {
        while ( true )
        {
            try
            {
                //::::::::::::::::::: T O   P R O G R A M M I N G::::::::::::::::::::::::
                //receber um pacote
                MessageUDP msg = MessageUDP.receiveUDP(listener);

                if ( msg.getContent() instanceof NodeAddress )
                {
                    NodeAddress node = (NodeAddress) msg.getContent();
                    
                    if ( !links.contains(node) )
                    {
                        links.add(node);
                        
                        msg.setContent(myAdress);
                        msg.sendUDP(MULTICAST_IP, MULTICAST_PORT);
                        
                        NodeEventListener.notifyConnect(listeners, node);
                    }
                    //tratar o pacote
                }
            }
            catch ( Exception ex )
            {
                Logger.getLogger(LocalNetWorkListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}//server UDP
