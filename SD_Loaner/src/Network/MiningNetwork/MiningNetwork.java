/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.MiningNetwork;

import BlockChain.Block;
import Network.NodeAddress;
import java.util.List;

/**
 *
 * @author zulu
 */
public class MiningNetwork
{

    List<NodeAddress> network; // rede de mineração
    Block block; // bloco para minerar

    public MiningNetwork(List<NodeAddress> network)
    {
        this.network = network;
    }

    public Block mine(Block block, NodeAddress me) throws Exception
    {
        this.block = block;

        //criar os links para os servidores da rede
        MiningLink threads[] = new MiningLink[network.size()];

        for ( int i = 0; i < threads.length; i++ )
        {
            threads[i] = new MiningLink(
                    network.get(i).getIP(),
                    network.get(i).getServicePort(),
                    block
            );
            
            threads[i].start();
        }

        for ( MiningLink thread : threads )
        {
            thread.join();

            if ( thread.isSolvedByMe() )
            {
                this.block = thread.getBlock();
            }

        }

        return this.block;
    }

}
