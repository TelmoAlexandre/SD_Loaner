/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountManager;
import BankServices.Service;
import Network.BlockChainSynchronizer.BlockChainInfo;
import Network.BlockChainSynchronizer.BlockChainSynchronizer;
import Network.Node;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
    
    AtomicBoolean isSynchronizing;

    public BlockChain(Node node, AtomicBoolean isSynchronizing)
    {
        this.node = node;
        chain = new ArrayList<>();
        this.isSynchronizing = isSynchronizing;
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
    public void addMinedBlock(Block block) throws Exception
    {
        char[] difficultyChars = new char[block.difficulty];

        Arrays.fill(difficultyChars, '0');

        String test = new String(difficultyChars);

        if ( block.hashCode.startsWith(test) )
        {
            if ( !chain.isEmpty() )
            {
                // Não adicionar o bloco caso este não encaixe na rede
                if ( !(getLast().hashCode.equals(block.previousHash)) )
                {
                    return;
                }
            }

            chain.add(block);

            // Atualiza e re-envia a informação da blockChain para a rede
            node.getMyAdress().getBlockChainInfo().setBlockChainSize(chain.size());
            BlockChainSynchronizer.notifyNetworkOfBlockChainChanges(node.getMyAdress());
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
                // Se o bloco estiver corrompido, corta a chain
                if ( !chain.get(i).checkBlock() )
                {
                    // Limpar a lista a partir do bloco corrompido
                    chain = chain.subList(0, i);

                    // Vai consultar a rede para receber uma BlockChain melhor
                    node.synchronizeBlockChain(isSynchronizing);

                    return chain.get(i - 1);
                }
            }
            // Último bloco quando não está corrompido
            return chain.get(chain.size() - 1);
        }

        return null;
    }

    /**
     * Verifica a integridade da blockChain e sincroniza-a.
     *
     */
    public void synchronize()
    {
        // Caso não se encontre já a sincronizar
        if ( !isSynchronizing.get() )
        {
            try
            {
                // Percorre a chain, bloco a bloco
                if ( !chain.isEmpty() )
                {
                    for ( int i = 1; i < chain.size(); i++ )
                    {
                        // Se o bloco estiver corrompido, corta a chain
                        if ( !chain.get(i).checkBlock() )
                        {
                            // Limpar a lista a partir do bloco corrompido
                            chain = chain.subList(0, i);

                            break;
                        }
                    }
                }
            }
            catch ( NoSuchAlgorithmException ex )
            {
                System.out.println("Erro na validação da BlockChain - BlockChain.isValid\n");
                System.out.println(ex.getMessage() + "\n\n");
            }

            // Vai consultar a rede para receber uma BlockChain melhor
            node.synchronizeBlockChain(isSynchronizing);
        }
    }

    /**
     * Atualiza as informações da blockChain
     *
     */
    public void alertNetworkAboutCorruptedBlockChain()
    {
        try
        {
            BlockChainInfo bcInfo = node.getMyAdress().getBlockChainInfo();

            // Define as novas informações
            bcInfo.setBlockChainSize(chain.size());
            bcInfo.setTimestamp(Utilities.NetworkTime.getTime());

            // Atualiza a rede com as suas informações
            BlockChainSynchronizer.notifyNetworkOfBlockChainChanges(node.getMyAdress());
        }
        catch ( Exception ex )
        {
            System.out.println("Erro ao atualizar as informações da BlockChain\n");
            System.out.println(ex.getMessage() + "\n\n");
        }
    }

    /**
     * Referencia ao isSynchronizing
     * 
     * @return 
     */
    public AtomicBoolean getIsSynchronizing()
    {
        return isSynchronizing;
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
