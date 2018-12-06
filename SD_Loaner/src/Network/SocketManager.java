/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Faz a gestão de um socket TCP.
 *
 * @author Telmo
 */
public class SocketManager
{
    // Socket
    Socket socket;
    DataOutputStream dataOut;
    DataInputStream dataIn;
    String sessionKey = "XR59bCRH6xPtTdiSPTsJQUqxFJCRXziXGFqawfe2XlM=";

    /**
     * Instancia o SocketManager e cria um novo socket com o ip e a porta TCP
     * recebidos por parametro.
     *
     * @param ip
     * @param tcpPort
     * @throws IOException
     */
    public SocketManager(String ip, int tcpPort) throws IOException, Exception
    {
        this.socket = new Socket(ip, tcpPort);
        
        // Cria Input e Output stream
        inic();    
    }

    /**
     * Instacia o SocketManager já com um socket pré-criado.
     *
     * @param socket
     * @throws IOException
     */
    public SocketManager(Socket socket) throws IOException, Exception
    {
        this.socket = socket;

        // Cria Input e Output stream
        inic();
    }

    /**
     * Cria Input e Output stream juntamente com as chaves necessárias às
     * comunicações.
     *
     */
    private void inic() throws IOException, Exception
    {
        createObjectStreams();
    }

    /**
     * Inicializa o in e o out.
     *
     */
    private void createObjectStreams() throws IOException
    {
        this.dataOut = new DataOutputStream(socket.getOutputStream());
        this.dataIn = new DataInputStream(socket.getInputStream());
    }

    /**
     * Faz a leitura de um objecto que chegue pelo socket. Bloqueante.
     *
     * @return Objecto recebido
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object readObject() throws Exception
    {
        // Receber data encriptada
        int length = dataIn.readInt();
        byte[] receivedData = new byte[length];
        dataIn.readFully(receivedData, 0, receivedData.length);

        receivedData = Utilities.SecurityUtils.decrypt(receivedData, sessionKey);

        ByteArrayInputStream bis = new ByteArrayInputStream(receivedData);
        ObjectInput objIn = new ObjectInputStream(bis);

        return objIn.readObject();
    }

    /**
     * Envia o objecto recebido por parametro.
     *
     * @param obj Objecto a ser enviado
     * @throws IOException
     */
    public void sendObject(Object obj) throws Exception
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput objOut = new ObjectOutputStream(bos);

        objOut.writeObject(obj);
        objOut.flush();

        byte[] byteObj = bos.toByteArray();

        byteObj = Utilities.SecurityUtils.encrypt(byteObj, sessionKey);

        dataOut.writeInt(byteObj.length);
        dataOut.write(byteObj);
        dataOut.flush();
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
}
