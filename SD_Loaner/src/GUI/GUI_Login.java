/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import AccountManager.AccountInformation;
import AccountManager.AccountManager;
import BlockChain.Block;
import BlockChain.BlockChain;
import Network.Node;
import Utilities.SecurityUtils;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Telmo
 */
public class GUI_Login extends javax.swing.JFrame
{
    // BlockChain
    private GUI_Main guiMain;
    private BlockChain blockChain;

    // Network
    private Node myNode;

    // Dados do cliente
    private Key publicKey;
    public String passwordHash;

    /**
     * Creates new form GUI_Login
     */
    public GUI_Login()
    {
        initComponents();

        // Centra a janela
        this.setLocationRelativeTo(null);

        try
        {
            // Cria instancias
            myNode = new Node();
            blockChain = new BlockChain(myNode);
            guiMain = new GUI_Main(blockChain, this, myNode);

            // Connectar à rede
            myNode.startServer(guiMain, this, blockChain);

            //colocar a interface a escutar o nó
            myNode.addNodeListener(guiMain);

            AtomicBoolean active = new AtomicBoolean(true);
            syncAnimation(active);

            new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        sleep(1_000);

                        // Sincroniza a blockChain
                        myNode.synchronizeBlockChain();

                        // Feedback ao cliente
                        active.set(false);
                    }
                    catch ( InterruptedException ex )
                    {
                        Logger.getLogger(GUI_Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }.start();

        }
        catch ( Exception ex )
        {
            Logger.getLogger(GUI_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jbLogin = new javax.swing.JButton();
        jbCreateAccount = new javax.swing.JButton();
        jbExit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jlName = new javax.swing.JLabel();
        jlPassword = new javax.swing.JLabel();
        jlFeedback = new javax.swing.JLabel();
        jpfPassword = new javax.swing.JPasswordField();
        jbLoadPublicKey = new javax.swing.JButton();
        jlLOANerLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jbLogin.setText("Login");
        jbLogin.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbLoginActionPerformed(evt);
            }
        });

        jbCreateAccount.setText("Create Account");
        jbCreateAccount.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbCreateAccountActionPerformed(evt);
            }
        });

        jbExit.setText("Exit");
        jbExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCreateAccount)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jbCreateAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlName.setText("Publick Key:");

        jlPassword.setText("Password:");

        jlFeedback.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jlFeedback.setText("Welcome");

        jbLoadPublicKey.setText("Load");
        jbLoadPublicKey.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbLoadPublicKeyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlFeedback)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlName)
                            .addComponent(jlPassword))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jpfPassword)
                            .addComponent(jbLoadPublicKey, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jlFeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlName)
                    .addComponent(jbLoadPublicKey))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPassword)
                    .addComponent(jpfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlLOANerLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/LOANer.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jlLOANerLogo)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlLOANerLogo)
                .addGap(8, 8, 8)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbLoadPublicKeyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbLoadPublicKeyActionPerformed
    {//GEN-HEADEREND:event_jbLoadPublicKeyActionPerformed
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File("."));
        int i = file.showOpenDialog(null);
        if ( i == JFileChooser.APPROVE_OPTION )
        {
            try
            {
                publicKey = SecurityUtils.loadKey(file.getSelectedFile().getAbsolutePath(), "RSA");
            }
            catch ( IOException ex )
            {
                jlFeedback.setText(ex.getMessage());
            }
        }
    }//GEN-LAST:event_jbLoadPublicKeyActionPerformed

    private void jbLoginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbLoginActionPerformed
    {//GEN-HEADEREND:event_jbLoginActionPerformed
        // Verifica se o campo password foi preenchido
        if ( !(new String(jpfPassword.getPassword()).equals("")) )
        {
            // Por fim verifica se foi carregada uma publicKey
            if ( publicKey != null )
            {
                // Cria o hash da password e guarda o mesmo no atributo passwordHash
                setPasswordHash(new String(jpfPassword.getPassword()));

                // Caso o cliente tenha conta e a autenticação tenha sido feita com sucesso
                if ( clientHasAccount() )
                {
                    // Carrega as informações do cliente para o GUI_Main
                    guiMain.setClientPasswordHash(passwordHash);
                    guiMain.setPublicKey(publicKey);
                    guiMain.printClientBlocks();

                    // Mostra o main e esconde a janela de login
                    guiMain.setVisible(true);
                    jlFeedback.setText("Welcome");
                    this.setVisible(false);

                    // Limpa as informações do cliente
                    clearClientData();
                }
            }
            else
            {
                giveAlertFeedback("A public key is required to be loaded.");
            }
        }
        else
        {
            giveAlertFeedback("Passwords field is empty.");
        }
    }//GEN-LAST:event_jbLoginActionPerformed

    private void jbCreateAccountActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbCreateAccountActionPerformed
    {//GEN-HEADEREND:event_jbCreateAccountActionPerformed
        createNewClientAccount();
    }//GEN-LAST:event_jbCreateAccountActionPerformed

    private void jbExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbExitActionPerformed
    {//GEN-HEADEREND:event_jbExitActionPerformed
        disconnectNode();
        System.exit(0);
    }//GEN-LAST:event_jbExitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for ( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels() )
            {
                if ( "Nimbus".equals(info.getName()) )
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        }
        catch ( ClassNotFoundException ex )
        {
            java.util.logging.Logger.getLogger(GUI_Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        catch ( InstantiationException ex )
        {
            java.util.logging.Logger.getLogger(GUI_Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        catch ( IllegalAccessException ex )
        {
            java.util.logging.Logger.getLogger(GUI_Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        catch ( javax.swing.UnsupportedLookAndFeelException ex )
        {
            java.util.logging.Logger.getLogger(GUI_Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new GUI_Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbCreateAccount;
    private javax.swing.JButton jbExit;
    private javax.swing.JButton jbLoadPublicKey;
    private javax.swing.JButton jbLogin;
    private javax.swing.JLabel jlFeedback;
    private javax.swing.JLabel jlLOANerLogo;
    private javax.swing.JLabel jlName;
    private javax.swing.JLabel jlPassword;
    private javax.swing.JPasswordField jpfPassword;
    // End of variables declaration//GEN-END:variables

    /**
     * Fornece feedback ao utilizador.
     *
     * @param label
     * @param feedback
     */
    public void giveNormalFeedback(String feedback)
    {
        jlFeedback.setText(feedback);
        jlFeedback.setForeground(Color.black);
    }

    /**
     * Fornece feedback de alertas ao utilizador.
     *
     * @param label
     * @param feedback
     */
    public void giveAlertFeedback(String feedback)
    {
        jlFeedback.setText(feedback);
        jlFeedback.setForeground(Color.red);
    }

    /**
     * Define o hash da password do cliente para poder ser verificado.
     *
     * @param password
     */
    public void setPasswordHash(String password)
    {
        // Transforma a password num hash
        try
        {
            MessageDigest hash;
            hash = MessageDigest
                    .getInstance("SHA-512");
            hash.update(
                    password.getBytes()
            );

            // Guarda o hash da password
            passwordHash = Base64.getEncoder().encodeToString(hash.digest());
        }
        catch ( NoSuchAlgorithmException ex )
        {
            jlFeedback.setText(ex.getMessage());
        }
    }

    /**
     * Verifica se o cliente tem conta criada no banco.
     *
     */
    private boolean clientHasAccount()
    {
        // Booleano para verificar se foi encontrada a conta do cliente.
        boolean found = false;

        for ( Block b : blockChain.chain )
        {
            // Individualiza o conteudo do bloco
            AccountManager blockContent = b.content;

            // Caso o conteudo do bloco seja uma informação de conta e esta pertença ao cliente
            if ( blockContent instanceof AccountInformation && blockContent.comparePublicKeys(publicKey) )
            {
                // Se entrar aqui, então encontrou a conta do cliente
                found = true;

                // Transforma o blockContent na sua verdadeira instancia
                AccountInformation info = (AccountInformation) blockContent;

                try
                {
                    // Trata da autenticação
                    if ( info.authenticateLogin(passwordHash) )
                    {
                        return true;
                    }
                    else
                    {
                        giveAlertFeedback("Wrong password provided.");
                        return false;
                    }
                }
                catch ( NoSuchAlgorithmException ex )
                {
                    giveAlertFeedback(ex.getMessage());
                }

                break;
            }
        }

        // Caso percorra a BlockChain inteira e não encontre a conta referente ao cliente.
        return false;
    }

    /**
     * Abre uma janela onde é possível preencher os dados de um novo cliente.
     *
     */
    public void createNewClientAccount()
    {
        GUI_NewAccount newAccountWindow = new GUI_NewAccount(blockChain, this);
        newAccountWindow.setVisible(true);
    }

    /**
     * Disconecta o nodo da rede.
     *
     */
    public void disconnectNode()
    {
        try
        {
            myNode.disconnect();
        }
        catch ( Exception ex )
        {
            giveAlertFeedback(ex.getMessage());
        }
    }

    /**
     * Desabilita os botões de movimento de conta.
     *
     */
    public void disableButtons()
    {
        jbCreateAccount.setEnabled(false);
    }

    /**
     * Desabilita todos os botoes.
     *
     */
    public void disableGUI()
    {
        jbCreateAccount.setEnabled(false);
        jbLoadPublicKey.setEnabled(false);
        jbLogin.setEnabled(false);
        jbCreateAccount.setEnabled(false);
        jpfPassword.setEnabled(false);
    }
    
    /**
     * Desabilita todos os botoes.
     *
     */
    public void enableGUI()
    {
        jbCreateAccount.setEnabled(true);
        jbLoadPublicKey.setEnabled(true);
        jbLogin.setEnabled(true);
        jbCreateAccount.setEnabled(true);
        jpfPassword.setEnabled(true);
    }
    
    /**
     * Ativa os botões de movimento de conta.
     *
     */
    public void enableButtons()
    {
        jbCreateAccount.setEnabled(true);
    }

    /**
     * Limpa os dados de login do utilizador
     */
    public void clearClientData()
    {
        publicKey = null;
        passwordHash = "";
        jpfPassword.setText("");
    }

    private void syncAnimation(AtomicBoolean active)
    {

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    disableGUI();
                    
                    while ( active.get() )
                    {

                        giveNormalFeedback("Synchronizing data.");
                        sleep(300);

                        giveNormalFeedback("Synchronizing data..");
                        sleep(300);

                        giveNormalFeedback("Synchronizing data...");
                        sleep(300);
                    }
                    
                    giveNormalFeedback("Welcome");
                    enableGUI();
                }
                catch ( InterruptedException ex )
                {
                    Logger.getLogger(GUI_Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
}
