/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Miner;

import BlockChain.Block;
import Network.Message.Message;
import Network.NodeAddress;
import Network.SocketManager;
import java.util.List;

/**
 * Envia um bloco a ser minado para toda a rede.
 */
public class MiningNetwork
{

    List<NodeAddress> network; // rede de mineração
    Block block; // bloco para minerar

    public MiningNetwork(List<NodeAddress> network)
    {
        this.network = network;
    }

    /**
     * Pede à rede para minar o bloco.
     * 
     * @param block
     * @throws Exception 
     */
    public void mine(Block block) throws Exception
    {
        this.block = block;
        
        // Pepara uma Message com o bloco a minar para ser espalhado pela rede.
        Message msg = new Message(
                Message.TOMINE, 
                this.block
        );
        
        // Espalha o bloco a minar pela rede
        for ( NodeAddress address : network )
        {
            // Cria a conecção
            SocketManager socketManager = new SocketManager(
                    address.getIP(), 
                    address.getTCP_Port()                  
            );
            
            // Envia o bloco a minar e fecha a conexão
            socketManager.sendObject(msg);
            socketManager.close();
        }
    }

}
