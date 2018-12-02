//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2018   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package Network;

import BlockChain.Block;
import Network.Message.MessageUDP;
import Network.MiningNetwork.MiningNetwork;
import Network.ServersListeners.LocalNetworkListener;
import Network.ServersListeners.MiningServerListener;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created on 15/nov/2018, 19:03:40
 *
 * @author zulu
 */
public class Node
{
    NodeAddress myAddress;
    TreeSet<NodeAddress> network = new TreeSet<>();
    List<NodeEventListener> listeners = new ArrayList<>();
    
    // Server Listeners
    LocalNetworkListener localNetworkListener;
    MiningServerListener miningServerListener;

    /**
     * Avisa a rede da sua entrada na mesma. Inicia o servidor de escuta de blocos a serem minados.
     * 
     * @param main
     * @throws Exception 
     */
    public void startServer(GUI.GUI_Main main) throws Exception
    {
        network = new TreeSet<>();
        
        // Cria um endereço para o nodo
        this.myAddress = new NodeAddress(
                InetAddress.getLocalHost().getHostAddress()
        );

        // Cria os listeners da rede e do Mineiro
        miningServerListener = new MiningServerListener(main, myAddress);
        miningServerListener.start();
        
        localNetworkListener = new LocalNetworkListener(network, myAddress, listeners);
        localNetworkListener.start();
    }

    /**
     * Adiciona um event listener para a lista de listeners.
     * 
     * @param l 
     */
    public void addNodeListener(NodeEventListener l)
    {
        listeners.add(l);
    }

    /**
     * Retorna o objeto de endereço do nodo.
     * 
     * @return NodeAddress
     */
    public NodeAddress getMyAdress()
    {
        return myAddress;
    }

    /**
     * Retorna a lista de nodos na rede.
     * 
     * @return 
     */
    public Set<NodeAddress> getNetwork()
    {
        return network;
    }

    /**
     * Disconecta o nodo da rede.
     * 
     * @throws java.lang.Exception
     */
    public void disconnect() throws Exception
    {
        // Envia uma mensagem multicast para avisar que o nó se irá desconectar
        MessageUDP msg = new MessageUDP(MessageUDP.DISCONNECT, myAddress);
        msg.sendUDP(LocalNetworkListener.MULTICAST_IP, LocalNetworkListener.MULTICAST_PORT);
        
        // Termina os listeners
        localNetworkListener.disconnect();
        miningServerListener.disconnect();
    }

    public void linkTo(String host, int port) throws Exception
    {
        //::::::::::::::::::: T O   P R O G R A M M I N G:::::::::::::::::::::::: 
        // enviar um pedido de coneção para o endereço   
    }

    /**
     * Faz a mineração de um bloco na rede. Em seguida faz a sua distribuição na rede.
     * 
     * @param block
     * @throws Exception 
     */
    public void mineBlock(Block block) throws Exception
    {
        //Executar a mineração na rede            
        MiningNetwork miningNetwork = new MiningNetwork(
                new ArrayList<>(network)
        );

        miningNetwork.mine(block);
    }

}