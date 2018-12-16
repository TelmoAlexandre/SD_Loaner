/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Miner;

import BlockChain.Block;
import GUI.GUI_Login;
import GUI.GUI_Main;
import Network.Message.Message;
import Network.NodeAddress;
import Network.SocketManager;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.SwingWorker;

/**
 * Cria as Threads Mineiras.
 * 
 */
public class MinerService extends SwingWorker<String, Integer>
{

    GUI_Main guiMain;
    GUI_Login guiLogin;
    TreeSet<NodeAddress> network;

    AtomicBoolean miningDone;

    // Necessário para minar 
    public Block block;
    private final int difficulty = 3;

    public MinerService(TreeSet<NodeAddress> network, Block block, GUI_Login guiLogin, AtomicBoolean miningDone) throws Exception
    {
        // Recebe os parametros
        this.network = network;
        this.block = block;
        this.guiMain = guiLogin.getGuiMain();
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
        // Preparação para as Threads
        int processors = Runtime.getRuntime().availableProcessors()-1;
        AtomicInteger atomicNonce = new AtomicInteger(0);
        MinerThread[] threads = new MinerThread[processors];

        // Define a dificuldade com que o bloco será minado
        block.setDifficulty(difficulty);

        // Constroi a String a ser minada
        String toMine = block.content.toString() + block.previousHash + block.difficulty;

        // Cria as Threads de mineração
        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new MinerThread(
                    toMine,
                    difficulty,
                    miningDone,
                    atomicNonce
            );

            threads[i].start();
        }

        // Aguarda o fim das Threads
        for (MinerThread thread : threads)
        {
            thread.join();

            if (thread.isSolvedByMe())
            {
                // Junta a informação que foi minada
                block.setHashCode(thread.getHash());
                block.setNonce(thread.getSolution());

                // Cria a mensagem que contem o bloco minada para o espalhar pela rede
                Message msg = new Message(Message.MINEDBLOCK, block);

                for (NodeAddress address : network)
                {
                    // Cria a conecção
                    SocketManager socketManager = new SocketManager(
                            address.getIP(),
                            address.getTCP_Port()
                    );

                    // Envia o bloco minado e fecha a conexão
                    socketManager.sendObject(msg);
                    socketManager.close();
                }
            }
        }
        
        guiLogin.enableButtons();
        guiMain.enableButtons();
        
        return "";
    }
}
