/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountManager;
import java.io.Serializable;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Bloco que será inserido na BlockChain.
 *
 * @author Telmo
 */
public class Block implements Serializable
{
    public AccountManager content;
    // previousHash é o hash do bloco anterior. hash é o hash do bloco
    public String previousHash, hashCode;
    public int difficulty = 0;
    public String nonce;

    public Block(Block last, AccountManager message) throws NoSuchAlgorithmException
    {
        this.content = message;
        this.previousHash = last == null ? "0" : last.hashCode;
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
        String msg = content.toString() + previousHash + difficulty;
        MessageDigest sha = MessageDigest
                .getInstance("SHA-512");

        sha.update(msg.getBytes());
        sha.update(("" + nonce).getBytes());

        String newCalculatedHash = Base64.getEncoder().encodeToString(sha.digest());

        byte[] digest = sha.digest();

        return hashCode.equals(newCalculatedHash);
    }

    /**
     * Define o nonce.
     *
     * @param nonce Valor do nonce
     */
    public void setNonce(String nonce)
    {
        this.nonce = nonce;
    }

    /**
     * Define o hashCode
     *
     * @param hashCode Hash Code minado
     */
    public void setHashCode(String hashCode)
    {
        this.hashCode = hashCode;
    }

    /**
     * Define a dificuldade com que o bloco foi minado.
     * 
     * @param difficulty 
     */
    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
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
