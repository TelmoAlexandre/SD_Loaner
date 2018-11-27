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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Classe que representa uma mensagem a ser trocada na rede.
 *
 */
public class Message implements Serializable
{
    public static final String CONNECT = "CONNECT";
    public static final String DISCONNECT = "DISCONNECT";

    String comand; // CONNECT/DISCONNECT
    Object content; // Conteudo da Message

    public Message(String comand, Object content)
    {
        this.comand = comand;
        this.content = content;
    }

    /**
     * Retorna o tipo de comando presente na mensagem.
     *
     * @return Comando da mensagem
     */
    public String getComand()
    {
        return comand;
    }

    /**
     * Define o tipo de comando da mensagem.
     *
     * @param comand Comando a ser definido.
     */
    public void setComand(String comand)
    {
        this.comand = comand;
    }

    /**
     * Retorna o conteudo da mensagem.
     *
     * @return Conteudo da mensagem.
     */
    public Object getContent()
    {
        return content;
    }

    /**
     * Define o conteudo da mensagem.
     *
     * @param content Conteudo da mensagem.
     */
    public void setContent(Object content)
    {
        this.content = content;
    }

    /**
     * Envia a própria instância do Message por UDP para o IP:PORT.
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
     * Recebe uma instância do Message por UDP.
     *
     * @param socket UDP socket
     * @return Message
     * @throws Exception
     */
    public static Message receiveUDP(DatagramSocket socket) throws Exception
    {
        // Criação do array de bytes para se poder definir o tipo de packet a ser recebido
        byte[] data = new byte[512];
        
        // Constroi o packet a ser recebido
        DatagramPacket pak = new DatagramPacket(data, data.length);
        
        // Pede ao socket para receber o pacote
        socket.receive(pak);
        
        // Retira o objeto do pacote e retorna o mesmo como Message
        ByteArrayInputStream bis = new ByteArrayInputStream(pak.getData());
        ObjectInputStream in = new ObjectInputStream(bis);
        
        return (Message) in.readObject();
    }

    /**
     * String com os conteudos da Message.
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        return comand + " " + content.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201811170706L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2018  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
