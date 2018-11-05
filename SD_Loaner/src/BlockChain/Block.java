/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountManager;

/**
 *
 * @author Telmo
 */
public class Block
{
    AccountManager message;
    // previousHash é o hash do bloco anterior. hash é o hash do bloco
    String previousHash, hash;

    public Block(AccountManager message)
    {
        this.message = message;
    }
    
}
