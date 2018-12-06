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
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author zulu
 */
public class MiningLink extends Thread
{

    Block block;
    List<MiningLink> miningLinks = new ArrayList<>();
    boolean isSolvedByMe;

    SocketManager socketMng;

    public MiningLink(NodeAddress nodeAddress, Block block, List<MiningLink> miningLinks) throws Exception
    {
        // Recolhe os parâmetros
        this.block = block;
        this.miningLinks = miningLinks;

        // Cria o socket
        socketMng = new SocketManager(nodeAddress.getIP(), nodeAddress.getTCP_Port());

        // Constroi mensagem que contem o bloco com informação de que este é para ser minado
        Message msg = new Message(Message.TOMINE, block);

        // Cria uma chave de sessão e envia-a encriptada com a chave publica RSA do destino
        socketMng.sendObject(msg);
    }

    /**
     * Fecha o socket TCP.
     *
     * @throws IOException
     */
    public void close() throws IOException
    {
        socketMng.close();
    }

    @Override
    public void run()
    {
        try
        {
            //Ler o bloco do servidor
            block = (Block) socketMng.readObject();

            isSolvedByMe = true;

            // Enviar o bloco minado e fechar a ligação com os restantes nodos
            for ( MiningLink link : miningLinks )
            {
                link.close();
            }

        }
        catch ( Exception ex )
        {
            Logger.getLogger("Mining has finished");
        }

    }

    public boolean isSolvedByMe()
    {
        return isSolvedByMe;
    }

    public Block getBlock()
    {
        return this.block;
    }
}
