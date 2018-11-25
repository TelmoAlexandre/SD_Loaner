/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miner;

import AccountManager.AccountManager;
import BlockChain.Block;
import BlockChain.BlockChain;
import GUI.GUI_Main;
import GUI.GUI_NewLoan;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.SwingWorker;

/**
 * Cria e gere Threads mineiras de acordo com o numero de cores do CPU.
 *
 * @author Telmo
 */
public class Miner extends SwingWorker<String, Integer>
{
    private final GUI_Main guiMain;
    private GUI_NewLoan guiLoan;
    private final Block block;    
    private AccountManager secondContent;
    private final BlockChain blockChain;
    private int difficulty = 4;
    private String nonce;
    private String hashCode;

    /**
     * Mina um bloco e adiciona-o à chain.
     *
     * @param block Bloco a ser minado
     * @param blockChain BlockChain à qual o bloco será adicionado
     * @param main GUI_Main
     */
    public Miner(Block block, BlockChain blockChain, GUI_Main main)
    {
        this.guiMain = main;
        this.block = block;
        this.blockChain = blockChain;
    }

    /**
     * Mina um bloco e adiciona-o à chain. Recebe um segundo AccountManager para ser inserido na blockChain.
     *
     * @param block Bloco a ser minado
     * @param blockChain BlockChain à qual o bloco será adicionado
     * @param gui_Main GUI_Main
     */
    public Miner(Block block, AccountManager secondContent, BlockChain blockChain, GUI_Main gui_Main, GUI_NewLoan guiLoan)
    {
        this.guiMain = gui_Main;
        this.block = block;
        this.blockChain = blockChain;
        this.guiLoan = guiLoan;
        this.secondContent = secondContent;
    }
    
    /**
     * Retorna o nonce do Miner.
     *
     * @return
     */
    public String getnonce()
    {
        return nonce;
    }

    /**
     * Define a dificuldade.
     *
     * @param difficulty
     */
    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }

    /**
     * Cria as Threads mineiras e sincroniza as mesmas.
     *
     * @return
     * @throws Exception
     */
    @Override
    protected String doInBackground() throws Exception
    {
        guiMain.disableButtons();

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

        return "";
    }

    /**
     * Faz os últimos acertos da mineração.
     *
     */
    @Override
    public void done()
    {
        block.setHashCode(hashCode);
        block.setNonce(nonce);

        blockChain.addMinedBlock(block);
        
        guiMain.enableButtons();
        guiMain.giveNormalFeedback("Mining has finished.");

        if (guiLoan != null)
        {
            guiLoan.addLoanToClientAccount(secondContent);
        }
    }
}
