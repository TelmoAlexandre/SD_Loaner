/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountManager;
import Miner.Miner;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author Telmo
 */
public class Block
{
    public AccountManager message;
    // previousHash é o hash do bloco anterior. hash é o hash do bloco
    String previousHash, hashCode;
    int nounce = 0;
    MessageDigest messageDigest;

    public Block(Block last, AccountManager message) throws NoSuchAlgorithmException
    {
        this.message = message;
        this.previousHash = last == null ? "0" : last.hashCode;

        // Definir o algoritmo para o hashCode
        messageDigest = MessageDigest.getInstance("SHA-512");
        
        // Cria a hash provisória
        hashCode = Base64.getEncoder().encodeToString(messageDigest.digest((previousHash + message + nounce).getBytes())
        );
    }

    /**
     * Retorna os conteudos do bloco em forma de String.
     * 
     * @return message + previousHash + hashCode
     */
    @Override
    public String toString()
    {
        return "{\n" + message.toString() + "\n    Previous Hash: " + previousHash + "\n    HashCode: " + hashCode + "\n}";
    }

    /**
     * Verifica a integridade do bloco.
     *
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public boolean checkBlock() throws NoSuchAlgorithmException
    {
        String msg = message.toString() + previousHash;
        MessageDigest sha = MessageDigest
                    .getInstance("SHA-256");

            sha.update(msg.getBytes());
            sha.update(("" + nounce).getBytes());

            String newCalculatedHash = Base64.getEncoder().encodeToString(sha.digest());
            
            byte[] digest = sha.digest();
        
        return hashCode.equals(newCalculatedHash);
    }
    
    /**
     * Mina a transação.
     *
     * @throws java.lang.InterruptedException
     */
    public void mine() throws InterruptedException 
    {
        String msg = message.toString() + previousHash;
        Miner miner = new Miner(msg);
        hashCode = miner.mine();
        nounce = miner.getNounce();
    }
}
