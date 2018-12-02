/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Miner;

import BlockChain.Block;
import GUI.GUI_Login;
import GUI.GUI_Main;
import Network.SocketManager;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.SwingWorker;

/**
 *
 * @author zulu
 */
public class MinerService extends SwingWorker<String, Integer>
{
    GUI_Main guiMain;
    GUI_Login guiLogin;

    // Ligação ao clioente
    SocketManager socketManager;
    
    AtomicBoolean miningDone;

    // Necessário para minar 
    public Block block;
    private final int difficulty = 4;

    public MinerService(SocketManager socketManager, Block block, GUI_Main guiMain, GUI_Login guiLogin, AtomicBoolean miningDone) throws Exception
    {
        // Recebe os parametros
        this.socketManager = socketManager;
        this.block = block;
        this.guiMain = guiMain;
        this.guiLogin = guiLogin;
        this.miningDone = miningDone;

        // Prepara as GUIs para a mineração
        this.guiLogin.disableButtons();
        this.guiMain.disableButtons();        
        this.guiMain.displayReceivedBlock(block.toString());
    }

    @Override
    protected String doInBackground() throws Exception
    {
        AtomicInteger atomicNonce = new AtomicInteger(0);

        int processors = Runtime.getRuntime().availableProcessors();

        MinerThread[] threads = new MinerThread[processors];

        // Define a dificuldade com que o bloco será minado
        block.setDifficulty(difficulty);

        // Constroi a String a ser minada
        String toMine = block.content.toString() + block.previousHash + block.difficulty;

        for ( int i = 0; i < threads.length; i++ )
        {
            threads[i] = new MinerThread(
                    toMine,
                    difficulty,
                    miningDone,
                    atomicNonce,
                    socketManager
            );

            threads[i].start();
        }

        for ( MinerThread thread : threads )
        {
            thread.join();

            if ( thread.isSolvedByMe() )
            {
                // Junta a informação que foi minada
                block.setHashCode(thread.getHash());
                block.setNonce(thread.getSolution());

                // Envia o bloco para o nodo que pediu a mineração
                socketManager.sendObject(block);
            }
        }

        guiMain.enableButtons();
        guiLogin.enableButtons();

        return "";
    }
}
