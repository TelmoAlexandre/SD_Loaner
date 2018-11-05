/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountInformation;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author Telmo
 */
public class Accounts
{
    public ArrayList<BlockChain> accounts;

    public Accounts() throws NoSuchAlgorithmException, InterruptedException, Exception
    {
        accounts = new ArrayList<>();
        
        // Cria o primeiro cliente
        createFirstClientAccount();
    }
    
    /**
     * Cria a conta do primeiro cliente.
     * 
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     */
    private void createFirstClientAccount() throws NoSuchAlgorithmException, InterruptedException, Exception
    {
        // Cria a informação do primeiro cliente
        AccountInformation clientInfo = new AccountInformation(
                "Telmo Alexandre", // Nome do cliente
                SecureUtils.SecurityUtils.loadB64Key("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDtcERsbYsU4ThzKETVaGLcHmXoKesdMhzZoe9bOVJ8wSioWaV92NjGDQUezMvbZM2ZAferjSWF47vqm/r63iDB2nxH0dpZL0qB+pI8BvGhdIin5+8RXtkEHi68mCtzGwS+22eUjwg5veVQDW+vGpg5b8KJW9HsUiDcnjwCVhehwIDAQAB", "RSA"),
                "123qwe" // Password do cliente
        );
        
        // Cria a blockChain do cliente, criando o primeiro bloco que irá conter informações do cliente.
        // Todas as transações do cliente seram guardadas nesta blockChain.
        BlockChain blockChain = new BlockChain(clientInfo);
        
        // Adiciona a nova BlockChain do cliente às contas de clientes.
        accounts.add(blockChain);
    }
    
    @Override
   public String toString()
   {
       return accounts.get(0).toString();
   }
}
