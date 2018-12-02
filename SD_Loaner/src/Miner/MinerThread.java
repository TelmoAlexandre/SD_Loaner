/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miner;

import Network.SocketManager;
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

    private final String toMine;
    private final int difficulty;
    
    private AtomicBoolean isSolved;
    private AtomicInteger atomicNonce;
    
    private String solution;
    private boolean solvedByMe;
    private String calculatedHash;

    SocketManager socketManager;

    public MinerThread(String toMine, int difficulty, AtomicBoolean isSolved, AtomicInteger atomicNonce, SocketManager socketManager)
    {
        this.toMine = toMine;
        this.difficulty = difficulty;
        this.isSolved = isSolved;
        this.atomicNonce = atomicNonce;
        this.socketManager = socketManager;
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

        while (!isSolved.get() && socketManager.isConnected())
        {
            StringBuilder nonce = new StringBuilder();

            // Adiciona mais lixo ao nonce
            nonce.append((int) (Math.random() * 1_000_000));
            nonce.append((int) (Math.random() * 1_000_000));
            nonce.append(atomicNonce.getAndIncrement());
            
            // Duplica o seu tamanho
            nonce.append(nonce.toString());

            String hash = this.calculateHash(this.toMine, nonce.toString());

            if (hash.startsWith(test))
            {
                this.solution = nonce.toString();
                this.calculatedHash = hash;
                isSolved.set(true);
                solvedByMe = true;
            }
        }
    }
}
