/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankServices;

import AccountManager.AccountManager;
import BlockChain.BlockChain;
import java.security.Key;

/**
 *  Classe abstrata que contem métodos de movimento de conta.
 * 
 * @author Telmo
 */
public abstract class Service extends AccountManager
{
    
    public Service (Key publicKey)
    {
        super(publicKey);
    }
    
    public abstract boolean validate(BlockChain bc, Key pbK) throws Exception;
}
