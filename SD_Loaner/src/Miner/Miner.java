/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miner;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Telmo
 */
public class Miner
{

    private final String msg;
    private int nounce = 0;

    public Miner(String msg)
    {
        this.msg = msg;
    }

    public int getNounce()
    {
        return nounce;
    }

    public String mine() throws InterruptedException
    {
        AtomicInteger nonceSequence = new AtomicInteger(0);
        AtomicBoolean isSolved = new AtomicBoolean(false);

        int processors = Runtime.getRuntime().availableProcessors();

        MinerThread[] threads = new MinerThread[processors];

        for (int i = 0; i < threads.length; i++)
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

        for (MinerThread thread : threads)
        {
            thread.join();
            if (thread.isSolvedByMe())
            {
                this.nounce = thread.getSolution();
                return thread.getHash();
            }

        }

        return "";
    }
}
