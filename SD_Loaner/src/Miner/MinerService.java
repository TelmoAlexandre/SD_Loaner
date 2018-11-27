/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miner;

import BlockChain.Block;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author zulu
 */
public class MinerService extends SwingWorker<String, Integer>
{
    // Ligação ao clioente
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    // Necessário para minar 
    private Block block;
    private int difficulty = 3;
    private String nonce;
    private String hashCode;

    public MinerService(Socket socket) throws Exception
    {
        this.socket = socket;

        // open streams
        // server IN - OUT
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());

        // Ler o bloco para minerar
        this.block = (Block) in.readObject();
    }

    @Override
    protected String doInBackground() throws Exception
    {
        AtomicBoolean isSolved = new AtomicBoolean(false);

        int processors = Runtime.getRuntime().availableProcessors();

        MinerThread[] threads = new MinerThread[processors];

        // Define a dificuldade com que o bloco será minado
        block.setDifficulty(difficulty);

        // Constroi a String a ser minada
        String toMine = block.content.toString() + block.previousHash + block.difficulty;

        for ( int i = 0; i < threads.length; i++ )
        {
            MinerThread thr = new MinerThread(
                    toMine,
                    difficulty,
                    isSolved
            );

            threads[i] = thr;

            thr.start();
        }

        for ( MinerThread thread : threads )
        {
            thread.join();

            if ( thread.isSolvedByMe() )
            {
                nonce = thread.getSolution();
                hashCode = thread.getHash();
            }

        }

        // Junta a informação que foi minada
        block.setHashCode(hashCode);
        block.setNonce(nonce);

        // Envia o bloco para o nodo que pediu a mineração
        out.writeObject(block);
        out.flush();

        // Fecha a conecção
        socket.close();

        return "";
    }
}
