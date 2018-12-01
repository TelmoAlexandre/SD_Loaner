/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountManager;
import BankServices.Service;
import GUI.GUI_Main;
import GUI.GUI_NewLoan;
import Network.Node;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
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
    Node node;

    public BlockChain(Node node)
    {
        chain = new ArrayList<>();
        this.node = node;
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
        Block block = new Block(last, content);

        // Caso seja um movimento de conta
        if ( content instanceof Service )
        {
            // Individualizar a instancia
            Service msg = (Service) block.content;

            // Valida o movimento, garantindo que existe dinheiro na conta e que a assinatura é verdadeira
            if ( msg.validate(this, msg.getPublicKey()) )
            {
                // Mina o bloco. Este será adicionado quando o miner terminar e chamar o metodo AddMinedBlock
                node.mineBlock(block);
            }
            else
            {
                throw new RuntimeException("Not enough money to complete the transaction.");
            }
        }
        else
        {
            // Neste caso, será um bloco de informação, cujo qual não necessita de ser validado da mesma forma que o de movimento necessita
            node.mineBlock(block);
        }
    }

    /**
     * Adiciona ambos os blocos de criação de emprestimo. O primeiro é o bloco de informação do emprestimo.
     * <p>O segundo é o movimento de conta para o cliente com entrada do dinheiro proveniente do emprestimo.
     *
     * @param content Informação do emprestimo.
     * @param content2 Movimento de conta de entrada do dinheiro na conta do cliente.
     * @param guiMain 
     * @param guiLoan
     * @throws Exception
     */
    public void addLoanBlocks(AccountManager content, AccountManager content2, GUI_Main guiMain, GUI_NewLoan guiLoan) throws Exception
    {
        // Cria o primeiro bloco assim que um objeto BlockChain for criado.
        Block last = getLast();
        Block b = new Block(last, content);
        
        // Neste caso, será um bloco de informação, cujo qual não necessita de ser validado da mesma forma que o de movimento necessita
        b.mineLoanInformation_WithAccountMovment(this, content2, guiMain, guiLoan);
    }
    
    /**
     * Adiciona bloco minado. 
     * 
     * @param block
     */
    public void addMinedBlock (Block block)
    {
        char[] difficultyChars = new char[block.difficulty];

        Arrays.fill(difficultyChars, '0');

        String test = new String(difficultyChars);
                
        if (block.hashCode.startsWith(test)){
            chain.add(block);
        }else{
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
        if ( chain.size() != 0 )
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
