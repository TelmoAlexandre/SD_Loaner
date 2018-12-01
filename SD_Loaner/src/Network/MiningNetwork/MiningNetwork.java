/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.MiningNetwork;

import BlockChain.Block;
import Network.Message.Message;
import Network.NodeAddress;
import Network.SocketManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zulu
 */
public class MiningNetwork
{

    List<NodeAddress> network; // rede de mineração
    Block block; // bloco para minerar
    List<MiningLink> miningLinks;

    public MiningNetwork(List<NodeAddress> network)
    {
        this.network = network;
    }

    public Block mine(Block block) throws Exception
    {
        this.block = block;

        // Guarda referencia a todos os MiningLinks
        miningLinks = new ArrayList<>();
        
        //criar os links para os servidores da rede
        MiningLink threads[] = new MiningLink[network.size()];

        for ( int i = 0; i < threads.length; i++ )
        {
            threads[i] = new MiningLink(
                    network.get(i).getIP(),
                    network.get(i).getServicePort(),
                    block,
                    miningLinks
            );
            
            miningLinks.add(threads[i]);
            
            threads[i].start();
        }

        // Aguarda o fim de todas as Threads
        for ( MiningLink thread : threads )
        {
            thread.join();

            if ( thread.isSolvedByMe() )
            {
                this.block = thread.getBlock();
            }
        }
        
        // Pepara uma Message com o bloco minado para ser espalhado pela rede.
        Message msg = new Message(
                Message.MINEDBLOCK, 
                this.block
        );
        
        // Espalha o bloco minado pela rede
        for ( NodeAddress address : network )
        {
            // Cria a conecção
            SocketManager socketManager = new SocketManager(
                    address.getIP(), 
                    address.getServicePort()
            );
            
            // Envia o bloco minado e fecha a conexão
            socketManager.sendObject(msg);
            socketManager.close();
        }
        
        return this.block;
    }

}
