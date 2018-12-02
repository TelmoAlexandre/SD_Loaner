/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountManager;
import BankServices.Service;
import Network.Node;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BlockChain que irá conter blocos. O primeiro bloco de todos contém as
 * informações do cliente, nomeadamente o Nome e a Hash da Password.
 * <p>
 * Todos os outros blocos iram conter movimentos de conta.
 *
 * @author Telmo
 */
public class BlockChain
{
    public List<Block> chain;
    Node node;

    public BlockChain(Node node)
    {
        this.node = node;
        chain = new ArrayList<>();
    }

    /**
     * Adiciona um novo bloco à block chain.
     *
     * @param content
     * @throws Exception
     */
    public void add(AccountManager content) throws Exception
    {
        // Cria o primeiro bloco assim que um objeto BlockChain for criado.
        Block last = getLast();
        Block block = new Block(last, content);

        // Caso seja um serviço, este terá que ser validado
        if ( content instanceof Service )
        {
            // Individualizar a instancia
            Service service = (Service) block.content;

            // Caso o service não seja validado, este nunca chega a ser minado
            if ( !service.validate(this) )
            {
                throw new RuntimeException("Not enough money to complete the transaction.");
            }
        }

        // Inicia uma Thread que pede para minar
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    node.mineBlock(block);
                }
                catch ( Exception ex )
                {
                    throw new RuntimeException(ex.getMessage());
                }
            }
        }.start();        
    }

    /**
     * Adiciona bloco minado.
     *
     * @param block
     */
    public void addMinedBlock(Block block)
    {
        char[] difficultyChars = new char[block.difficulty];

        Arrays.fill(difficultyChars, '0');

        String test = new String(difficultyChars);

        if ( block.hashCode.startsWith(test) )
        {
            chain.add(block);
        }
        else
        {
            throw new RuntimeException("The block can't be added to the BlockChain because it isn't mined.");
        }
    }

    /**
     * Retorna o último bloco da chain. Verifica a integridade dos blocos à
     * medida que percorre a chain.
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public Block getLast() throws NoSuchAlgorithmException
    {
        if ( !chain.isEmpty() )
        {
            for ( int i = 1; i < chain.size(); i++ )
            {
                // Se o bloco estiver corrompido, termina a chain
                if ( !chain.get(i).checkBlock() )
                {
                    // Limpar a lista a partir do bloco corrompido
                    chain = chain.subList(0, i);
                    return chain.get(i - 1);
                }
            }
            // Último bloco quando não está corrompido
            return chain.get(chain.size() - 1);
        }

        return null;
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

        for ( Block block : chain )
        {
            txt.append(block.toString()).append("\n\n");
        }

        return txt.toString();
    }
}
