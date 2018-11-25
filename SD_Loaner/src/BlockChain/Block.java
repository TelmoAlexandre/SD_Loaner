/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountManager;
import GUI.GUI_Main;
import Miner.Miner;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *  Bloco que será inserido na BlockChain.
 * 
 * @author Telmo
 */
public class Block
{
    public AccountManager content;
    // previousHash é o hash do bloco anterior. hash é o hash do bloco
    public String previousHash, hashCode;
    public int nounce = 0;
    private final MessageDigest messageDigest;

    public Block(Block last, AccountManager message) throws NoSuchAlgorithmException
    {
        this.content = message;
        this.previousHash = last == null ? "0" : last.hashCode;

        // Definir o algoritmo para o hashCode
        messageDigest = MessageDigest.getInstance("SHA-512");
        
        // Cria a hash provisória
        hashCode = Base64.getEncoder().encodeToString(messageDigest.digest((previousHash + message + nounce).getBytes())
        );
    }

    /**
     * Retorna os conteúdos do bloco em forma de String.
     * 
     * @return content + previousHash + hashCode
     */
    @Override
    public String toString()
    {
        return "{\n" + content.toString() + "\n    Previous Hash: " + previousHash + "\n    HashCode: " + hashCode + "\n}";
    }

    /**
     * Verifica a integridade do bloco.
     *
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public boolean checkBlock() throws NoSuchAlgorithmException
    {
        String msg = content.toString() + previousHash;
        MessageDigest sha = MessageDigest
                    .getInstance("SHA-512");

            sha.update(msg.getBytes());
            sha.update(("" + nounce).getBytes());

            String newCalculatedHash = Base64.getEncoder().encodeToString(sha.digest());
            
            byte[] digest = sha.digest();
        
        return hashCode.equals(newCalculatedHash);
    }
    
    /**
     * Cria o objeto Miner que por sua vez irá criar e gerir as Threads de mineração.
     *
     * @param main
     * @throws java.lang.InterruptedException
     */
    public void mine(GUI_Main main) throws InterruptedException 
    {
        String msg = content.toString() + previousHash;
        Miner miner = new Miner(msg, main, this);
        miner.execute();
        nounce = miner.getNounce();
    }
    
    /**
     * Retorna a public key do movimento que se encontra dentro do bloco.
     * 
     * @return 
     */
    public Key getPublicKey()
    {
        return content.getPublicKey();
    }
}
