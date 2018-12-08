/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.ServersListeners;

// import classroom.blockChain.MinerService;
import BlockChain.Block;
import BlockChain.BlockChain;
import GUI.GUI_Login;
import GUI.GUI_Main;
import Network.Miner.MinerService;
import Network.Message.Message;
import Network.NodeAddress;
import Network.SocketManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
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
public class TCPServerListener extends Thread
{

    ServerSocket server;
    GUI_Login guiLogin;
    TreeSet<NodeAddress> network;

    // Para sinalizar as Threads de quando devem parar a mineração
    AtomicBoolean miningDone = new AtomicBoolean(false);

    public TCPServerListener(GUI_Login guiLogin,
            NodeAddress myAddress, TreeSet<NodeAddress> network) throws Exception
    {
        this.guiLogin = guiLogin;
        this.network = network;

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
        while ( !server.isClosed() )
        {
            try
            {
                SocketManager socketManager = new SocketManager(
                        server.accept() // Aguarda uma ligação (Bloqueante)
                );

                // Cria uma nova Thread que irá interpretar o tipo de mensagem chegada
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        startConnection(socketManager);
                    }
                }.start();
            }
            catch ( Exception ex )
            {
                System.out.println("TCPServerListener has ended.");
            }
        }
    }

    private void startConnection(SocketManager socketManager)
    {
        try
        {
            // Desmembra os conteudos que chegam
            Message msg = (Message) socketManager.readObject();
            Block block = (Block) msg.getContent();

            switch ( msg.getType() )
            {
                case Message.TOMINE:
                    
                    // MinerSerive vai receber o bloco a ser minerado
                    // e vai criar Threads que o irao minar
                    // Assim que a mineração for concluida, o bloco será enviado
                    // novamente para o solicitador da mineração
                    miningDone.set(false);
                    new MinerService(network, block, guiLogin, miningDone).execute();
                    break;

                case Message.MINEDBLOCK:

                    // Sinaliza as Threads que devem de parar de minar pois já chegou um bloco minado
                    miningDone.set(true);

                    GUI_Main guiMain = guiLogin.getGuiMain();
                    guiMain.blockChain.addMinedBlock(block);
                    guiMain.writeMinedBlock(block.hashCode);
                    guiMain.giveNormalFeedback(null, "A new block was added to the BlockChain.");
                    guiMain.printBlockChain();

                    // Ativa os butoes das GUIs
                    guiMain.enableButtons();
                    guiLogin.enableButtons();
                    break;

                case Message.SYNC_BLOCKCHAIN_INFO:

                    synchronizeBlockChain(block, socketManager);
                    break;

                default:
                    break;
            }
        }
        catch ( Exception ex )
        {
            Logger.getLogger(TCPServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void synchronizeBlockChain(Block block, SocketManager socketManager)
    {

        try
        {
            BlockChain blockChain = guiLogin.getBlockChain();

            // Caso o bloco que chegue, que supostamente será o ultimo da chain do no remoto,
            // seja null, significa que a chain remota não tem blocos, logo envia a
            // blockChain local inteira
            boolean found = (block == null);

            for ( Block b : blockChain.chain )
            {
                // A partir do momento que o ultimo bloco do nó remoto
                // for encontrado, todos os blocos seguintes seram enviados
                if ( found )
                {
                    socketManager.sendObject(b);
                }

                // Encontra o bloco recebido.
                // Este bloco será o ultimo bloco na blockChain remota
                // Todos os blocos após esse bloco seram enviados para o nó remoto
                if ( block != null )
                {
                    if ( b.hashCode.equals(block.hashCode) )
                    {
                        found = true;
                    }
                }
            }

            // Envia um null para terminar a sincronização no nó remoto
            // Assim o nó remoto sabe que não existem mais blocos nesta blockChain
            socketManager.sendObject(null);
        }
        catch ( Exception ex )
        {
            System.out.println("Erro na sincronização - Receiver");
        }

    }

}
