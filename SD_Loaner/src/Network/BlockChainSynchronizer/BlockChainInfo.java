/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.BlockChainSynchronizer;

import java.io.Serializable;

/**
 * Contem informações sobre a BlockChain de um nodo.
 *
 * @author Telmo
 */
public class BlockChainInfo implements Serializable
{
    private int blockChainSize;
    private long timestamp;

    public BlockChainInfo(int blockChainSize, long timestamp)
    {
        this.blockChainSize = blockChainSize;
        this.timestamp = timestamp;
    }    
    
    /**
     * Define o tamanho da blockChain local.
     * 
     * @param blockChainSize 
     */
    public void setBlockChainSize(int blockChainSize)
    {
        this.blockChainSize = blockChainSize;
    }

    /**
     * Define a ultima hora em que a BlockChain foi corrompida.
     * 
     * @param timestamp 
     */
    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * Retorna o tamanho da blockChain
     * 
     * @return 
     */
    public int getBlockChainSize()
    {
        return blockChainSize;
    }

    /**
     * Retorna a hora da ultima corrupção.
     * 
     * @return 
     */
    public long getTimestamp()
    {
        return timestamp;
    }

    /**
     * Compara o próprio objeto com o que chega por parâmetro , e dita se o
     * objeto que chega por parâmetro  é melhor do que o próprio.
     * 
     * @param info
     * @return 
     */
    public boolean isBetterThan(BlockChainInfo info)
    {
        // Caso o meu nó não tenha block chain
        if (info == null){
            return true;
        }
        
        // Se for maior, ganha automaticamente
        if ( blockChainSize > info.getBlockChainSize() )
        {
            return true;
        }
        
        // Caso seja igual, desempata por timestamp
        if ( blockChainSize == info.getBlockChainSize() )
        {
            return timestamp < info.getTimestamp();
        }
        return false;
    }
}
