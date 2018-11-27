/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.MiningNetwork;

import BlockChain.Block;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zulu
 */
public class MiningLink extends Thread
{

    Block block;
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket socket;
    boolean isSolvedByMe;

    public MiningLink(String ip, int port, Block block) throws Exception
    {
        // Cria o socket
        socket = new Socket(ip, port);

        // client OUT-IN
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        // Recolhe os restantes parâmetros
        this.block = block;

        //Escrever o bloco do socket
        out.writeObject(block);
        out.flush();
    }

    public void close()
    {
        //::::::::::::::::::: T O   P R O G R A M M I N G:::::::::::::::::::::::: 
        //fechar o socket

    }

    @Override
    public void run()
    {
        try
        {
                //Ler o bloco do servidor
                Block minedBlock = (Block) in.readObject();
                
                // Constroi a String de teste com o numero de zeros no inicio
                char[] difficultyChars = new char[minedBlock.difficulty];
                Arrays.fill(difficultyChars, '0');
                String test = new String(difficultyChars);

                if ( minedBlock.hashCode.startsWith(test) )
                {
                    isSolvedByMe = true;
                    this.block = minedBlock;
                }
        }
        catch ( IOException | ClassNotFoundException ex )
        {
            Logger.getLogger(MiningLink.class.getName()).log(Level.SEVERE, null, ex);
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
