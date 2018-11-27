/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Servers;

// import classroom.blockChain.ServerService;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class MiningServerListener extends Thread {

        ServerSocket server;

        public MiningServerListener(int port ) throws Exception {
            server = new ServerSocket(port);

        }

        @Override
        public void run() {
           //::::::::::::::::::: T O   P R O G R A M M I N G:::::::::::::::::::::::: 
           // esperar  por clientes
           //lacar o Server Service
           while(true){
               try {
                   Socket client = server.accept();
                   //new ServerService(client).start();
               } catch (Exception ex) {
                   Logger.getLogger(MiningServerListener.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
        }

    }
