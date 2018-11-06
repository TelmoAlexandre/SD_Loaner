/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountManager;
import AccountServices.Service;
import GUI.GUI_Main;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * BlockChain que irá conter blocos. O primeiro bloco de todos contém as informações do cliente, nomeadamente o Nome e a Hash da Password.
 * <p>Todos os outros blocos iram conter movimentos de conta.
 * 
 * @author Telmo
 */
public class BlockChain
{
    public List<Block> chain;

    public BlockChain(AccountManager message, GUI_Main main) throws NoSuchAlgorithmException, InterruptedException
    {
        chain = new ArrayList<>();
        
        // Cria o primeiro bloco assim que um objeto BlockChain for criado.
        Block block = new Block(null, message);
        block.mine(null);
        chain.add(block);
    }
    
    /**
     * Adiciona um novo bloco à block chain.
     * 
     * @param message
     * @param main
     * @throws Exception 
     */
    public void add(AccountManager message, GUI_Main main) throws Exception
    {
        // Cria o primeiro bloco assim que um objeto BlockChain for criado.
        Block last = getLast();
        Block b = new Block(last, message);
        Service msg = (Service)b.message;
        if (msg.validate(this))
        {
            b.mine(main);
            chain.add(b);
        } else
        {
            throw new RuntimeException("Not enough money to complete the transaction.");
        }
    }
    
    public Block getLast() throws NoSuchAlgorithmException
    {
        for (int i = 1; i < chain.size(); i++)
        {
            // Se o bloco estiver corrompido, termina a chain
            if (!chain.get(i).checkBlock())
            {
                // Limpar a lista a partir do bloco corrompido
                chain = chain.subList(0, i);
                return chain.get(i - 1);
            }
        }
        // Último bloco quando não está corrompido
        return chain.get(chain.size() - 1);
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
