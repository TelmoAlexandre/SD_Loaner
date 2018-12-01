/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miner;

import BlockChain.Block;
import GUI.GUI_Main;
import Network.SocketManager;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.SwingWorker;

/**
 *
 * @author zulu
 */
public class MinerService extends SwingWorker<String, Integer>
{

    // Ligação ao clioente
    SocketManager socketManager;

    // Necessário para minar 
    public Block block;
    private final int difficulty = 3;
    private String nonce;
    private String hashCode;

    public MinerService(SocketManager socketManager, Block block, GUI_Main main) throws Exception
    {
        this.socketManager = socketManager;
        this.block = block;
        
        main.displayReceivedBlock(block.toString());
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

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new MinerThread(
                    toMine,
                    difficulty,
                    isSolved,
                    socketManager
            );

            threads[i].start();
        }

        for (MinerThread thread : threads)
        {
            thread.join();

            if (thread.isSolvedByMe())
            {
                nonce = thread.getSolution();
                hashCode = thread.getHash();
            }

        }

        // Junta a informação que foi minada
        block.setHashCode(hashCode);
        block.setNonce(nonce);

        // Envia o bloco para o nodo que pediu a mineração
        socketManager.sendObject(block);

        return "";
    }
}
