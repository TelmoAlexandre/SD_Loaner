/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miner;

import BlockChain.Block;
import GUI.GUI_Main;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.SwingWorker;

/**
 *  Cria e gere Threads mineiras de acordo com o numero de cores do CPU.
 * 
 * @author Telmo
 */
public class Miner extends SwingWorker<String, Integer>
{
    private final GUI_Main main;
    private final Block block;
    private final String msg;
    private int nounce = 0;
    private String hashCode;

    public Miner(String msg, GUI_Main main, Block block)
    {
        this.msg = msg;
        this.main = main;
        this.block = block;
    }

    /**
     * Retorna o nounce do Miner.
     * 
     * @return 
     */
    public int getNounce()
    {
        return nounce;
    }

    /**
     * Mina e retorna a hash minada.
     * 
     * @return
     * @throws Exception 
     */
    public String mine() throws Exception
    {
        return doInBackground();
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
        // Desabilita os butões de movimentos de conta
        if ( main != null )
        {
            main.disableButtons();
        }

        AtomicInteger nonceSequence = new AtomicInteger(0);
        AtomicBoolean isSolved = new AtomicBoolean(false);

        int processors = Runtime.getRuntime().availableProcessors();

        MinerThread[] threads = new MinerThread[processors];

        for ( int i = 0; i < threads.length; i++ )
        {
            MinerThread thr = new MinerThread(
                    this.msg,
                    3,
                    nonceSequence,
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
                this.nounce = thread.getSolution();
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
        //abortar threads

        block.hashCode = hashCode;
        block.nounce = nounce;

        if ( main != null )
        {
            main.enableButtons();
            main.giveNormalFeedback("Mining has finished.");
        }
    }
}
