/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.ServersListeners;

import BankServices.AccountMovement;
import GUI.GUI_AccountMovement;
import GUI.GUI_Login;
import GUI.GUI_NewLoan;
import Utilities.SecurityUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author Telmo
 */
public class AndroidServerListener extends Thread
{
    GUI_Login guiLogin;
    ServerSocket server;

    // Chaves do cliente
    PublicKey pbK;
    PrivateKey pvK;

    public AndroidServerListener(GUI_Login guiLogin)
    {
        this.guiLogin = guiLogin;
        String pbS = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDtcERsbYsU4ThzKETVaGLcHmXoKesdMhzZoe9bOVJ8wSioWaV92NjGDQUezMvbZM2ZAferjSWF47vqm/r63iDB2nxH0dpZL0qB+pI8BvGhdIin5+8RXtkEHi68mCtzGwS+22eUjwg5veVQDW+vGpg5b8KJW9HsUiDcnjwCVhehwIDAQAB";
        String pvS = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIO1wRGxtixThOHMoRNVoYtweZegp6x0yHNmh71s5UnzBKKhZpX3Y2MYNBR7My9tkzZkB96uNJYXju+qb+vreIMHafEfR2lkvSoH6kjwG8aF0iKfn7xFe2QQeLryYK3MbBL7bZ5SPCDm95VANb68amDlvwolb0exSINyePAJWF6HAgMBAAECgYBoeXaxY2be2E0Sky+913Hx2oEIzm3SdTw/lbfcgRGLzBIEMsTd9kNG6n79NBCQ8XkRbt1zPHoICJ3f7DElUT3geZNMWYP9ruzSLEbKnHVHPBnHxrIAvlBb0r6DEaxtv+9uTnEce7anojYf0UHf8sD2KUN3fmKxMnSyZJMUeL+raQJBAPbEfQ3zw9DZvBP5n+xBxRdVdCi0CRDakSbmMCIuWeKnCw5qDqz4biVyP3s8+NGBtlULg/skEYtSbNewDwNNrcUCQQCIo0HnGjijbDY8lN4+IjC9R2dqnY+UiEUIPZSvm9aGGNCu9pNZgp3qh0AiXIhWrpKIV1+HQKuTvl5MjoS/zEvbAkEArA5eX18KtlFKeOcBIZrOEDHt9v9ons62jFfNUdfdplHwPJGoP+RL8GITbxsZJgL6HZwU3wPME8dZyp2gKh58PQJAUDXGUiwKY6T6kcWyUTcw9WwdQXENAFyeaZ/80LnizQV0O8Fz7m/G1A5hj8pSHtCMJI1l/rfwAOMX6EkhlJYRUQJBAIO35niFNnqFJn4rZ9BpMqOhWXFCXrLVuiPJr1Vtj9q3yIyFykQlaYa3D5lhVqmz0vGZbCKBvRLUObrHt0Vq0TE=";

        try
        {
            pbK = (PublicKey) SecurityUtils.getPublicKey(pbS);
            pvK = (PrivateKey) SecurityUtils.getPrivateKey(pvS);
        }
        catch ( Exception ex )
        {
            System.out.println("\nError creating Keys - AndroidServerListener");
            System.out.println("\n" + ex.getMessage());
            //Logger.getLogger(AndroidServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        try
        {
            server = new ServerSocket(21_150);

        }
        catch ( IOException ex )
        {
            System.out.println("\nError creating ServerSocket - AndroidServerListener");
            System.out.println("\n" + ex.getMessage());
            //Logger.getLogger(AndroidServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run()
    {
        while ( !server.isClosed() )
        {
            try
            {
                Socket sckt = server.accept();

                // Receber o JSON e converter para Objecto Json (Gson)
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(sckt.getInputStream())
                );
                String content = br.readLine();
                JsonObject json = new JsonParser().parse(content).getAsJsonObject();

                String type = json.get("type").getAsString();
                double amount = json.get("amount").getAsDouble();

                if ( type.equals("LOAN") )
                {
                    GUI_NewLoan newLoan = new GUI_NewLoan(guiLogin.getGuiMain());
                    newLoan.startLoanProcess(pvK, amount);
                }
                else
                {
                    GUI_AccountMovement accountMov = new GUI_AccountMovement(guiLogin.getGuiMain(), json.get("type").getAsString());
                    accountMov.createMovement(pvK, amount);
                }

            }
            catch ( Exception ex )
            {
                System.out.println("\nError receiving TCP connection - AndroidServerListener");
                System.out.println("\n" + ex.getMessage());
                //Logger.getLogger(AndroidServerListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Termina a Thread.
     *
     * @throws IOException
     */
    public void disconnect() throws IOException
    {
        server.close();
    }
}
