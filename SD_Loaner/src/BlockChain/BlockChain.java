/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountManager;
import BankServices.Service;
import GUI.GUI_Main;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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

    public BlockChain()
    {
        chain = new ArrayList<>();
    }

    /**
     * Adiciona um novo bloco à block chain.
     *
     * @param content
     * @param main
     * @throws Exception
     */
    public void add(AccountManager content, GUI_Main main) throws Exception
    {
        // Cria o primeiro bloco assim que um objeto BlockChain for criado.
        Block last = getLast();
        Block b = new Block(last, content);

        // Caso seja um movimento de conta
        if ( content instanceof Service )
        {
            // Individualizar a instancia
            Service msg = (Service) b.content;

            // Valida o movimento, garantindo que existe dinheiro na conta e que a assinatura é verdadeira
            if ( msg.validate(this, msg.getPublicKey()) )
            {
                b.mine(main);
                chain.add(b);
            }
            else
            {
                throw new RuntimeException("Not enough money to complete the transaction.");
            }
        }
        else
        {
            // Neste caso, será um bloco de informação, cujo qual não necessita de ser validado da mesma forma que o de movimento necessita
            b.mine(main);
            chain.add(b);
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
