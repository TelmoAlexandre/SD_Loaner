/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountManager;
import AccountServices.AccountMovment;
import AccountServices.Service;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * BlockChain que irá conter blocos. O primeiro bloco de todos contém as informações do cliente, nomeadamente o Nome e a Hash da Password.
 * <p>Todos os outros blocos iram conter movimentos de conta.
 * 
 * @author Telmo
 */
public class BlockChain
{
    public ArrayList<Block> chain;

    public BlockChain(AccountManager message) throws NoSuchAlgorithmException, InterruptedException
    {
        chain = new ArrayList<>();
        
        // Cria o primeiro bloco assim que um objeto BlockChain for criado.
        Block block = new Block(null, message);
        block.mine();
        chain.add(block);
    }
    
    public void add(AccountManager message) throws Exception
    {
        // Cria o primeiro bloco assim que um objeto BlockChain for criado.
        Block b = new Block(null, message);
        Service msg = (Service)b.message;
        if (msg.validate(this))
        {
            b.mine();
            chain.add(b);
        } else
        {
            throw new RuntimeException("Not enough money to complete the transaction.");
        }
    }
    
    /**
     * Retorna os conteudos de cada bloco da BlockChain.
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        StringBuilder txt = new StringBuilder();

        chain.forEach((block) ->
        {
            txt.append(block.toString()).append("\n\n");
        });
        return txt.toString();
    }
}
