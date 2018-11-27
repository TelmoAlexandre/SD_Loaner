/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Servers;

// import classroom.blockChain.MinerService;
import Miner.MinerService;
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

    public MiningServerListener(int port) throws Exception
    {
        server = new ServerSocket(port);
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
                Socket client = server.accept();
                
                // MinerSerive vai receber o bloco a ser minerado
                // e vai criar Threads que o irao minar
                // Assim que a mineração for concluida, o bloco será enviado
                // novamente para o solicitador da mineração
                new MinerService(client).execute();
            }
            catch ( Exception ex )
            {
                Logger.getLogger(MiningServerListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
