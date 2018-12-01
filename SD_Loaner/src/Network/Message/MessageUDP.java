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
package Network.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Classe que representa uma mensagem a ser trocada na rede.
 *
 */
public class MessageUDP extends Message
{
    public static final String CONNECT = "CONNECT";
    public static final String DISCONNECT = "DISCONNECT";

    public MessageUDP(String type, Object content)
    {
        super(type, content);
    }

    /**
     * Envia a própria instância do MessageUDP por UDP para o IP:PORT.
     *
     * @param ip IP de destino.
     * @param port Porto de destino.
     * @throws Exception
     */
    public void sendUDP(String ip, int port) throws Exception
    {
        DatagramSocket socket = new DatagramSocket();
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        
        // Converte esta mensagem num array de bytes
        out.writeObject(this);
        out.flush();
        out.close();
        
        byte[] data = bos.toByteArray();
        
        // Cria o pacote que contem esta mensagem
        DatagramPacket pak = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
        
        // Envia o pacote e fecha o socket
        socket.send(pak);
        socket.close();
    }

    /**
     * Recebe uma instância do MessageUDP por UDP.
     *
     * @param socket UDP socket
     * @return MessageUDP
     * @throws Exception
     */
    public static MessageUDP receiveUDP(DatagramSocket socket) throws Exception
    {
        // Criação do array de bytes para se poder definir o tipo de packet a ser recebido
        byte[] data = new byte[512];
        
        // Constroi o packet a ser recebido
        DatagramPacket pak = new DatagramPacket(data, data.length);
        
        // Pede ao socket para receber o pacote
        socket.receive(pak);
        
        // Retira o objeto do pacote e retorna o mesmo como MessageUDP
        ByteArrayInputStream bis = new ByteArrayInputStream(pak.getData());
        ObjectInputStream in = new ObjectInputStream(bis);
        
        return (MessageUDP) in.readObject();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201811170706L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2018  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
