/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Servers;

// import classroom.blockChain.MinerService;
import BlockChain.Block;
import GUI.GUI_Main;
import Miner.MinerService;
import Network.Message.Message;
import Network.SocketManager;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class MiningServerListener extends Thread
{
    ServerSocket server;
    SocketManager socketManager;
    GUI_Main main;

    public MiningServerListener(int port, GUI_Main main) throws Exception
    {
        server = new ServerSocket(port);
        this.main = main;
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
                    main.writeMinedBlock(block.hashCode);
                }

            }
            catch ( Exception ex )
            {
                Logger.getLogger(MiningServerListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
