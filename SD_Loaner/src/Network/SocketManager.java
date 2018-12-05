/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import SecureUtils.SecurityUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

/**
 * Faz a gestão de um socket TCP.
 *
 * @author Telmo
 */
public class SocketManager
{

    // Socket
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    // RSA
    PublicKey myPublicKeyRSA;
    PrivateKey myPrivateKeyRSA;
    PublicKey tcpDestinationPublicKey;

    // Chave de sessão
    Key sessionKey;

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

        // Cria Input e Output stream juntamente com as chaves necessárias às comunicações
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

        // Cria Input e Output stream juntamente com as chaves necessárias às comunicações
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
        createRSA_KeyPair();
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
     * Cria um par de chaves RSA.
     *
     * @throws java.lang.Exception
     */
    public void createRSA_KeyPair() throws Exception
    {
        KeyPair keys = SecurityUtils.generateKeyPair(1024);

        // Guarda as chaves geradas
        this.myPublicKeyRSA = keys.getPublic();
        this.myPrivateKeyRSA = keys.getPrivate();
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
        byte[] receivedData = null;
        in.read(receivedData);
        
        receivedData = SecureUtils.SecurityUtils.decrypt(receivedData, sessionKey);
        
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
        
        byteObj = SecureUtils.SecurityUtils.encrypt(byteObj, sessionKey);
        
        out.write(byteObj);
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

    /**
     * Recebe a chave publica RSA do no e retorna a sua.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void prepareSecureConnection_Receiver() throws Exception
    {
        // Recebe a chave publica do destino
        tcpDestinationPublicKey = (PublicKey) in.readObject();

        // Cria uma chave de sessão
        sessionKey = SecureUtils.SecurityUtils.generateKey("AES", 256);

        // Encripta a chave
        byte[] encryptedData = encryptRSA(sessionKey);

        // Envia a chave de sessão encriptada com RSA
        out.write(encryptedData);
        out.flush();
    }

    /**
     * Envia a sua chave publica RSA e aguarda a chave publica do outro no.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void prepareSecureConnection_Sender() throws Exception
    {
        // Envia a sua chave públic
        out.writeObject(myPublicKeyRSA);
        out.flush();

        // Aguarda chave de sessão encriptada
        byte[] seKey = null;
        in.read(seKey);
        
        // Guarda a chave de sessão
        sessionKey = (Key) decryptRSA(seKey);
    }
    
    /**
     * Encripta um objecto
     * 
     * @param obj
     * @return
     * @throws Exception 
     */
    private byte[] encryptRSA(Object obj) throws Exception
    {
        // Cria a Cipher
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, tcpDestinationPublicKey);

        // Output Streams
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CipherOutputStream encOut = new CipherOutputStream(bos, cipher);
        ObjectOutputStream objOut = new ObjectOutputStream(encOut);

        // Carrega a sessionKey
        objOut.writeObject(sessionKey);
        objOut.flush();

        // Fecha os output streams
        objOut.close();
        encOut.close();
        bos.close();

        return bos.toByteArray();
    }
    
    /**
     * Desencripta um array de bytes para um objecto.
     * 
     * @param encryptedData
     * @return
     * @throws Exception 
     */
    private Object decryptRSA(byte[] encryptedData) throws Exception
    {
        // Desencripta
        ByteArrayInputStream byteIn = new ByteArrayInputStream(encryptedData);
        
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, myPrivateKeyRSA);
        
        CipherInputStream encIn = new CipherInputStream(byteIn, cipher);
        ObjectInputStream objIn = new ObjectInputStream(encIn);
        
        return objIn.readObject();
    }
}
