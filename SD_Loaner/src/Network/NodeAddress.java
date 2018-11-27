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

import java.io.Serializable;

/**
 * Representa o endereço do nodo.
 *
 */
public class NodeAddress implements Serializable, Comparable<NodeAddress>
{

    private String IP;
    private int udpListenerPort;
    private int tcpServerPort;

    public NodeAddress(String IP, int... port)
    {
        this.IP = IP;
        this.udpListenerPort = port[0];
        this.tcpServerPort = port[1];
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
            
            return adress.udpListenerPort == this.udpListenerPort && adress.IP.equalsIgnoreCase(this.IP);
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
     * Retorna o IP.
     *
     * @return IP
     */
    public String getIP()
    {
        return IP;
    }

    /**
     * Define o IP
     *
     * @param IP IP
     */
    public void setIP(String IP)
    {
        this.IP = IP;
    }

    /**
     * Retorna o porto UDP.
     *
     * @return Porto UDP
     */
    public int getPort()
    {
        return udpListenerPort;
    }

    /**
     * Retorna o porto TCP.
     *
     * @return Porto TCP
     */
    public int getServicePort()
    {
        return tcpServerPort;
    }

    /**
     * Define o porto UDP.
     *
     * @param port Porto UDP
     */
    public void setPort(int port)
    {
        this.udpListenerPort = port;
    }

    /**
     * Retorna [IP:PORT].
     *
     * @return [IP:PORT]
     */
    @Override
    public String toString()
    {
        return this.IP + ":" + this.udpListenerPort;
    }
}
