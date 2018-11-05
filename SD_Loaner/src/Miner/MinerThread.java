/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Telmo
 */
public class MinerThread extends Thread
{

    private final String msg;
    private final int difficulty;
    private final AtomicInteger nonceSequence;
    private final AtomicBoolean isSolved;
    private int solution;
    private boolean solvedByMe;
    private String calculatedHash;

    public int getSolution()
    {
        return solution;
    }

    public String getHash()
    {
        return calculatedHash;
    }

    public boolean isSolvedByMe()
    {
        return solvedByMe;
    }

    public MinerThread(
            String msg, int difficulty,
            AtomicInteger nonceSequence,
            AtomicBoolean isSolved
    )
    {
        this.msg = msg;
        this.difficulty = difficulty;
        this.nonceSequence = nonceSequence;
        this.isSolved = isSolved;
    }

    public String calculateHash(String message, int nonce)
    {
        try
        {
            MessageDigest sha = MessageDigest
                    .getInstance("SHA-256");

            sha.update(message.getBytes());
            sha.update(("" + nonce).getBytes());

            byte[] digest = sha.digest();

            return Base64.getEncoder()
                    .encodeToString(digest);

        } catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run()
    {
        char[] difficultyChars = new char[this.difficulty];

        Arrays.fill(difficultyChars, '0');

        String test = new String(difficultyChars);

        while (!isSolved.get())
        {
            int nounce = nonceSequence.getAndIncrement();

            String hash = this.calculateHash(this.msg, nounce);

            if (hash.startsWith(test))
            {
                this.solution = nounce;
                this.calculatedHash = hash;
                isSolved.set(true);
                solvedByMe = true;
            }
        }

    }

}
