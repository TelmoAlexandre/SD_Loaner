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
    List<NodeEventListener> listeners = new ArrayList<>();

    NodeAddress myAdress;

    TreeSet<NodeAddress> links = new TreeSet<>();

    public void startServer(int port, int service) throws Exception
    {
        this.myAdress = new NodeAddress(InetAddress.getLocalHost().getHostAddress(),
                port, service);

        // lancar o servico de escuta do grupo multicaste
        links = new TreeSet<>();

        //new LocalNetWorkListener(links,myAdress,listeners).start();
        //new MiningServerListener(service).start();
    }

    public void addNodeListener(NodeEventListener l)
    {
        listeners.add(l);
    }

    public NodeAddress getMyAdress()
    {
        return myAdress;
    }

    public Set<NodeAddress> getLinks()
    {
        return links;
    }

    void disconnect()
    {
        //::::::::::::::::::: T O   P R O G R A M M I N G:::::::::::::::::::::::: 
        //desconectar o no

    }

    public void linkTo(String host, int port) throws Exception
    {
        //::::::::::::::::::: T O   P R O G R A M M I N G:::::::::::::::::::::::: 
        // enviar um pedido de coneção para o endereço   
    }

    public void mineMessage(Block blk) throws Exception
    {
        //::::::::::::::::::: T O   P R O G R A M M I N G:::::::::::::::::::::::: 
        //Executar a mineração na rede            
        //MiningNetWork mn = new MiningNetWork(
        // new ArrayList<>(links)
        //);

        // blk = mn.mine(blk);
    }

}
