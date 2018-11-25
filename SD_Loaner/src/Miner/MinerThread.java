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

/**
 *
 * @author Telmo
 */
public class MinerThread extends Thread
{

    private final String toMine;
    private final int difficulty;
    private final AtomicBoolean isSolved;
    private String solution;
    private boolean solvedByMe;
    private String calculatedHash;

    public MinerThread(
            String toMine, int difficulty,
            AtomicBoolean isSolved
    )
    {
        this.toMine = toMine;
        this.difficulty = difficulty;
        this.isSolved = isSolved;
    }

    /**
     * Retorna o nonce calculado.
     *
     * @return
     */
    public String getSolution()
    {
        return solution;
    }

    /**
     * Retorna o hash minado.
     *
     * @return
     */
    public String getHash()
    {
        return calculatedHash;
    }

    /**
     * Indica se a esta foi a Thread que resolveu a mineração.
     *
     * @return
     */
    public boolean isSolvedByMe()
    {
        return solvedByMe;
    }

    public String calculateHash(String message, String nonce)
    {
        try
        {
            MessageDigest sha = MessageDigest
                    .getInstance("SHA-512");

            sha.update(message.getBytes());
            sha.update((nonce).getBytes());

            byte[] digest = sha.digest();
            
            return Base64.getEncoder()
                    .encodeToString(digest);

        }
        catch ( NoSuchAlgorithmException e )
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

        while ( !isSolved.get() )
        {
            String nonce = new String();
            
            // Adiciona mais lixo ao nonce
            nonce = nonce.concat((Math.random() * 1_000_000) + "");
            nonce = nonce.concat((Math.random() * 1_000_000) + "");
                        
            //System.out.println(nonce);

            String hash = this.calculateHash(this.toMine, nonce);
            
            if ( hash.startsWith(test) )
            {
                this.solution = nonce;
                this.calculatedHash = hash;
                isSolved.set(true);
                solvedByMe = true;
            }
        }

    }

}
