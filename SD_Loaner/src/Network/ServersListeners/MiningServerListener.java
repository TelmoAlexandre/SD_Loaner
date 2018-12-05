/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.ServersListeners;

// import classroom.blockChain.MinerService;
import AccountManager.AccountInformation;
import BlockChain.Block;
import GUI.GUI_Login;
import GUI.GUI_Main;
import Network.Miner.MinerService;
import Network.Message.Message;
import Network.NodeAddress;
import Network.SocketManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * Escuta a rede por uma ligação TCP. Assim que estabelcer a ligação, recebe um
 * bloco.
 * <p>
 * Caso esse bloco necessite mineração, inicia o processo de mineração por
 * Threads.
 * <p>
 * Caso esse bloco já esteja minado, adiciona-o à BlockChain deste nó.
 *
 * @author Telmo
 */
public class MiningServerListener extends Thread
{
    ServerSocket server;
    SocketManager socketManager;
    GUI_Main guiMain;
    GUI_Login guiLogin;

    // Para sinalizar as Threads de quando devem parar a mineração
    AtomicBoolean miningDone = new AtomicBoolean(false);

    public MiningServerListener(GUI_Main guiMain, GUI_Login guiLogin, NodeAddress myAddress) throws Exception
    {
        this.guiMain = guiMain;
        this.guiLogin = guiLogin;

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
        // esperar  por clientes
        // lacar o Server Service
        while ( true )
        {
            try
            {
                socketManager = new SocketManager(
                        server.accept(), // Aguarda uma ligação (Bloqueante)
                        SocketManager.RECEIVER
                );
                
                Message msg = (Message) socketManager.readObject();
                Block block = (Block) msg.getContent();
                
                if ( msg.getType().equals(Message.TOMINE) )
                {
                    // MinerSerive vai receber o bloco a ser minerado
                    // e vai criar Threads que o irao minar
                    // Assim que a mineração for concluida, o bloco será enviado
                    // novamente para o solicitador da mineração
                    miningDone.set(false);
                    new MinerService(socketManager, block, guiMain, guiLogin, miningDone).execute();
                }
                else if ( msg.getType().equals(Message.MINEDBLOCK) )
                {
                    // Sinaliza as Threads que devem de parar de minar pois já chegou um bloco minado
                    miningDone.set(true);
                    
                    guiMain.addMinedBlockToBlockChain(block);
                    guiMain.writeMinedBlock(block.hashCode);
                    guiMain.giveNormalFeedback(null, "A new block was added to the BlockChain.");
                    guiMain.printBlockChain();
                    
                    // Ativa os butoes das GUIs
                    guiMain.enableButtons();
                    guiLogin.enableButtons();
                }

            }
            catch ( Exception ex )
            {
                Logger.getLogger("Mining Listener terminado");
            }
        }
    }

}
