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

import Network.BlockChainSynchronizer.BlockChainInfo;
import Network.ServersListeners.LocalNetworkListener;
import java.io.Serializable;

/**
 * Representa o endereço do nodo.
 *
 */
public class NodeAddress implements Serializable, Comparable<NodeAddress>
{

    private String ip;
    private int udpPort;
    private int tcpPort;
    private BlockChainInfo blockChainInfo;
    
    public NodeAddress(String ip)
    {
        this.ip = ip;
        this.udpPort = LocalNetworkListener.MULTICAST_PORT;
        blockChainInfo = new BlockChainInfo(0, 0);
    }

    /**
     * Compara com outro objecto.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if ( obj instanceof NodeAddress )
        {
            NodeAddress adress = (NodeAddress) obj;
            
            return adress.ip.equalsIgnoreCase(this.ip);
        }
        return false;
    }

    /**
     * Compara com outro NodeAddress.
     *
     * @param nodeAdress
     * @return the value 0 if the argument string is equal to this string; a
     * value less than 0 if this string is lexicographically less than the
     * string argument; and a value greater than 0 if this string is
     * lexicographically greater than the string argument.
     */
    @Override
    public int compareTo(NodeAddress nodeAdress)
    {
        return this.toString().compareTo(nodeAdress.toString());
    }

    /**
     * Retorna o ip.
     *
     * @return ip
     */
    public String getIP()
    {
        return ip;
    }

    /**
     * Define o ip
     *
     * @param IP ip
     */
    public void setIP(String IP)
    {
        this.ip = IP;
    }

    /**
     * Retorna o porto UDP.
     *
     * @return Porto UDP
     */
    public int getUDP_Port()
    {
        return udpPort;
    }

    /**
     * Retorna o porto TCP.
     *
     * @return Porto TCP
     */
    public int getTCP_Port()
    {
        return tcpPort;
    }

    /**
     * Define o porto UDP.
     *
     * @param port Porto UDP
     */
    public void setUDP_Port(int port)
    {
        this.udpPort = port;
    }
    
    /**
     * Define o porto TCP.
     *
     * @param port Porto TCP
     */
    public void setTCP_Port(int port)
    {
        this.tcpPort = port;
    }

    public BlockChainInfo getBlockChainInfo()
    {
        return blockChainInfo;
    }

    public void setBlockChainInfo(BlockChainInfo blockChainInfo)
    {
        this.blockChainInfo = blockChainInfo;
    }
        
    /**
     * Retorna [ip:PORT].
     *
     * @return [ip:PORT]
     */
    @Override
    public String toString()
    {
        return this.ip + ":" + this.udpPort + " || TCP: " + this.tcpPort;
    }
}
