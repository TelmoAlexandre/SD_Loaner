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
    
    public void setBlockChainSize(int blockChainSize)
    {
        this.blockChainSize = blockChainSize;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public int getBlockChainSize()
    {
        return blockChainSize;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public boolean isBetterThan(BlockChainInfo info)
    {
        // Caso o meu nó não tenha block chain
        if (info == null){
            return true;
        }
        
        // Se for maior, ganha automaticamente
        if ( blockChainSize >= info.getBlockChainSize() )
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
