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
 *
 * @author Telmo
 */
public class Miner extends SwingWorker<String, Integer>
{
    GUI_Main main;
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

    public int getNounce()
    {
        return nounce;
    }

    public String mine() throws Exception
    {
        return doInBackground();
    }

    @Override
    protected String doInBackground() throws Exception
    {
        AtomicInteger nonceSequence = new AtomicInteger(0);
        AtomicBoolean isSolved = new AtomicBoolean(false);

        int processors = Runtime.getRuntime().availableProcessors();

        MinerThread[] threads = new MinerThread[processors];

        for ( int i = 0; i < threads.length; i++ )
        {
            MinerThread thr = new MinerThread(
                    this.msg,
                    4,
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

    @Override
    public void done()
    {
        //abortar threads

        block.hashCode = hashCode;
        block.nounce = nounce;
        
        if ( main != null )
        {
            main.showClientAccountMovments();
        }
    }
}
