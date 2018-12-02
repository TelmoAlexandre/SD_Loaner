/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.ServersListeners;

// import classroom.blockChain.MinerService;
import BlockChain.Block;
import GUI.GUI_Main;
import Miner.MinerService;
import Network.Message.Message;
import Network.NodeAddress;
import Network.SocketManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

/**
 * Escuta a rede por uma ligação TCP. Assim que estabelcer a ligação, recebe um bloco.
 * <p>Caso esse bloco necessite mineração, inicia o processo de mineração por Threads.
 * <p>Caso esse bloco já esteja minado, adiciona-o à BlockChain deste nó.
 * 
 * @author Telmo
 */
public class MiningServerListener extends Thread
{
    ServerSocket server;
    SocketManager socketManager;
    GUI_Main main;

    public MiningServerListener(GUI_Main main, NodeAddress myAddress) throws Exception
    {
        this.main = main;
        
        // Requesita um porto disponivel
        server = new ServerSocket(0);
        
        // define esse porto no myAddress
        myAddress.setTCP_Port(server.getLocalPort());
    }

    public void disconnect() throws IOException
    {
        server.close();
    }
    
    @Override
    public void run()
    {
        //::::::::::::::::::: T O   P R O G R A M M I N G:::::::::::::::::::::::: 
        // esperar  por clientes
        //lacar o Server Service
        while ( true )
        {
            try
            {
                socketManager = new SocketManager(
                        
                        server.accept() // Aguarda uma ligação (Bloqueante)
                        
                );

                Message msg = (Message) socketManager.readObject();
                Block block = (Block) msg.getContent();

                if ( msg.getType().equals(Message.TOMINE) )
                {
                    // MinerSerive vai receber o bloco a ser minerado
                    // e vai criar Threads que o irao minar
                    // Assim que a mineração for concluida, o bloco será enviado
                    // novamente para o solicitador da mineração
                    new MinerService(socketManager, block, main).execute();
                }
                else if ( msg.getType().equals(Message.MINEDBLOCK) )
                {
                    main.addMinedBlockToBlockChain(block);
                    main.writeMinedBlock(block.hashCode);
                    main.giveNormalFeedback(null, "A new block was added to the BlockChain.");
                    main.printBlockChain();
                    main.enableButtons();
                }

            }
            catch ( Exception ex )
            {
                Logger.getLogger("Mining Listener terminado");
            }
        }
    }

}
