/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Faz a gestão de um socket TCP.
 *
 * @author Telmo
 */
public class SocketManager
{
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public SocketManager(String ip, int tcpPort) throws IOException
    {
        this.socket = new Socket(ip, tcpPort);

        createObjectStreams();
    }

    public SocketManager(Socket socket) throws IOException
    {
        this.socket = socket;

        createObjectStreams();
    }

    /**
     * Inicializa o in e o out.
     *
     */
    private void createObjectStreams() throws IOException
    {
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

    }

    /**
     * Faz a leitura de um objecto que chegue pelo socket. Bloqueante.
     *
     * @return Objecto recebido
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object readObject() throws IOException, ClassNotFoundException
    {
        return in.readObject();
    }

    /**
     * Envia o objecto recebido por parametro.
     *
     * @param obj Objecto a ser enviado
     * @throws IOException
     */
    public void sendObject(Object obj) throws IOException
    {
        out.writeObject(obj);
        out.flush();
    }

    /**
     * Fecha a conexão.
     *
     * @throws IOException
     */
    public void close() throws IOException
    {
        socket.close();
    }

    /**
     * Indica se o socket se encontra fechado.
     *
     * @return Se existe conecção.
     */
    public boolean isClosed()
    {
        return socket.isClosed();
    }
}
