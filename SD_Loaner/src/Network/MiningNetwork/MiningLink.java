/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.MiningNetwork;

import BlockChain.Block;
import Network.Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author zulu
 */
public class MiningLink extends Thread
{

    Block block;
    List<MiningLink> miningLinks = new ArrayList<>();
    boolean isSolvedByMe;
    
    // Network
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    
    public MiningLink(String ip, int port, Block block, List<MiningLink> miningLinks) throws Exception
    {
        // Cria o socket
        socket = new Socket(ip, port);

        // client OUT-IN
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        // Recolhe os restantes parâmetros
        this.block = block;
        this.miningLinks = miningLinks;

        Message msg = new Message(Message.TOMINE, block);
        
        // Enviar o bloco a ser minado
        out.writeObject(msg);
        out.flush();
    }

    /**
     * Envia o bloco minado pela ligação TCP.
     * 
     * @throws IOException 
     */
    public void sendMinedBlock() throws IOException
    {
        out.writeObject(block);
        out.flush();
    }

    /**
     * Fecha o socket TCP.
     * 
     * @throws IOException 
     */
    public void close() throws IOException
    {
        socket.close();
    }

    @Override
    public void run()
    {
        try
        {
            //Ler o bloco do servidor
            block = (Block) in.readObject();

            isSolvedByMe = true;
            
            // Enviar o bloco minado e fechar a ligação com os restantes nodos
            for (MiningLink link : miningLinks)
            {
                link.close();
            }
            
        } catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger("Mining has finished");
        }

    }

    public boolean isSolvedByMe()
    {
        return isSolvedByMe;
    }
    
    public Block getBlock()
    {
        return this.block;
    }
}
