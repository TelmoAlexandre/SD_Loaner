/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountsAndLoans;

import BankServices.AccountMovment;
import BlockChain.BlockChain;
import Information.AccountInformation;
import GUI.GUI_Main;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Faz a gestão das contas dos clientes.
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
        BlockChain blockChain = new BlockChain(clientInfo, null);

        // Adiciona a nova BlockChain do cliente às contas de clientes.
        accounts.add(blockChain);
    }

    /**
     * Cria uma nova conta de cliente.
     * <p>Cria uma nova BlockChain já com o bloco de informação do cliente.
     * 
     * @param name
     * @param publicKey
     * @param password
     * @param main
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException 
     */
    public void createAccount(String name, Key publicKey, String password, GUI_Main main) throws NoSuchAlgorithmException, InterruptedException
    {
        // Cria a informação do cliente
        AccountInformation clientInfo = new AccountInformation(
                name, // Nome do cliente
                publicKey,
                password // Password do cliente
        );

        // Cria a blockChain do cliente, criando o primeiro bloco que irá conter informações do cliente.
        // Todas as transações do cliente seram guardadas nesta blockChain.
        BlockChain blockChain = new BlockChain(clientInfo, main);

        // Adiciona a nova BlockChain do cliente às contas de clientes.
        accounts.add(blockChain);
    }

    
    /**
     * Retorna a BlockChain inteira do cliente como String.
     * 
     * @param publicKey
     * @param passwordHash
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public String toString(Key publicKey, String passwordHash) throws NoSuchAlgorithmException
    {
        // Booleano para verificar se foi encontrada a conta do cliente.
        boolean found = false;

        for ( BlockChain bc : accounts )
        {
            // Individualiza o primeiro bloco da chain. Este bloco apenas contem informação
            AccountInformation info = (AccountInformation) bc.chain.get(0).message;

            if ( info.comparePublicKeys(publicKey) )
            {
                // Se entrar aqui, então encontrou a conta do cliente
                found = true;

                // Caso o utilizador tenha introduzido a sua password correta
                if ( info.authenticateLogin(passwordHash) )
                {
                    return bc.toString();
                }
                else
                {
                    throw new RuntimeException("Wrong password.");
                }
            }
        }

        if ( !found )
        {
            throw new RuntimeException("Account not found.");
        }

        return "Account not found.";
    }

    /**
     * Retorna o bloco de informação de todos os clientes.
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        StringBuilder txt = new StringBuilder();
                
        for ( BlockChain bc : accounts )
        {
            txt.append(bc.chain.get(0).toString()).append("\n");
            txt.append("Total Money: ").append(AccountMovment.getMyMoney(bc)).append("€\n\n");
        }

        return txt.toString();
    }
}
