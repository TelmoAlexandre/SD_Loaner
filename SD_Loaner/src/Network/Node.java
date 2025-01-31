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
import GUI.GUI_Login;
import Network.BlockChainSynchronizer.BlockChainSynchronizer;
import Network.Message.MessageUDP;
import Network.Miner.MiningNetwork;
import Network.ServersListeners.AndroidServerListener;
import Network.ServersListeners.LocalNetworkListener;
import Network.ServersListeners.TCPServerListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

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
    boolean isConnected = false;

    // Server Listeners
    LocalNetworkListener localNetworkListener;
    TCPServerListener tcpServerListener;
    AndroidServerListener androidServerListener;

    // Synchronizer
    BlockChainSynchronizer blockChainSynchronizer;

    public Node()
    {
        try
        {
            // Cria um endereço para o nodo
            this.myAddress = new NodeAddress(
                    InetAddress.getLocalHost().getHostAddress()
            );
        }
        catch ( UnknownHostException ex )
        {
            System.out.println("Error creating NodeAddress\n");
            System.out.println(ex.getMessage() + "\n\n");
        }
    }    
    
    /**
     * Avisa a rede da sua entrada na mesma. Inicia o servidor de escuta de
     * blocos a serem minados.
     *
     * @param guiLogin
     * @throws Exception
     */
    public void startServer(GUI_Login guiLogin) throws Exception
    {
        network = new TreeSet<>();

        // Cria os listeners da rede e do Mineiro
        tcpServerListener = new TCPServerListener(guiLogin, myAddress, network);
        tcpServerListener.start();
        
        androidServerListener = new AndroidServerListener(guiLogin);
        androidServerListener.start();

        localNetworkListener = new LocalNetworkListener(network, myAddress, listeners);
        localNetworkListener.start();

        // Cria a thread de sincronização da blockChain
        if ( blockChainSynchronizer == null )
        {
            blockChainSynchronizer = new BlockChainSynchronizer(myAddress, network, guiLogin.getBlockChain());
            blockChainSynchronizer.start();
        }

        // Afirma estar conectado
        isConnected = true;
    }

    /**
     * Faz a sincronização da blockChain com a rede.
     * 
     * @param isSynchronizing 
     */
    public void synchronizeBlockChain(AtomicBoolean isSynchronizing)
    {
        blockChainSynchronizer.checkRemoteBlockChains(isSynchronizing);
    }

    /**
     * Adiciona um event listener para a lista de listeners.
     *
     * @param l
     */
    public void addNodeListener(NodeEventListener l)
    {
        if ( !listeners.contains(l) )
        {
            listeners.add(l);
        }
    }

    /**
     * Referência ao objeto de endereço do nodo.
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
     * Retorna se o nodo se encontra conectado à rede.
     *
     * @return
     */
    public boolean isConnected()
    {
        return isConnected;
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
        tcpServerListener.disconnect();
        androidServerListener.disconnect();
        blockChainSynchronizer.disconnect();

        // Não se encontra conectado
        isConnected = false;
    }

    /**
     * Faz a mineração de um bloco na rede. Em seguida faz a sua distribuição na
     * rede.
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
