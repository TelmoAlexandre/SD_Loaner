/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import AccountManager.AccountManager;
import Information.AccountInformation;
import BankServices.AccountMovment;
import BankServices.LoanPayment;
import BlockChain.Block;
import BlockChain.BlockChain;
import Information.LoanInformation;
import SecureUtils.SecurityUtils;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.text.ParseException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 * Apresenta uma GUI para a criação de um novo emprestimo.
 *
 * @author Telmo
 */
public class GUI_NewLoan extends javax.swing.JFrame
{

    private GUI_Main main;
    private BlockChain blockChain;
    private Key publicKey;
    private String passwordHash;
    private String clientName;

    /**
     * Creates new form GUI_NewLoan
     */
    public GUI_NewLoan()
    {
        initComponents();

        // Centra a janela
        this.setLocationRelativeTo(null);

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

        jlFeedback = new javax.swing.JLabel();
        jbConfirmAndSign = new javax.swing.JButton();
        jbCancel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jpfPassword = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jbLoadPublicKey = new javax.swing.JButton();
        jsAmount = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jlFeedback.setText("Fill up your information:");

        jbConfirmAndSign.setText("Confirm and Sign");
        jbConfirmAndSign.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbConfirmAndSignActionPerformed(evt);
            }
        });

        jbCancel.setText("Cancel");
        jbCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbCancelActionPerformed(evt);
            }
        });

        jLabel4.setText("Password:");

        jLabel2.setText("Public Key:");

        jbLoadPublicKey.setText("Load");
        jbLoadPublicKey.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbLoadPublicKeyActionPerformed(evt);
            }
        });

        jsAmount.setModel(new javax.swing.SpinnerNumberModel(100, 1, 250000, 100));

        jLabel1.setText("Amount:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbLoadPublicKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jlFeedback))
                        .addGap(0, 72, Short.MAX_VALUE))
                    .addComponent(jpfPassword)
                    .addComponent(jSeparator1)
                    .addComponent(jbConfirmAndSign, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jsAmount))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlFeedback)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(jbLoadPublicKey)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbConfirmAndSign, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbConfirmAndSignActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbConfirmAndSignActionPerformed
    {//GEN-HEADEREND:event_jbConfirmAndSignActionPerformed

        // Verifica se o campo password foi preenchido
        if ( !(new String(jpfPassword.getPassword()).equals("")) )
        {
            // Por fim verifica se foi carregada uma publicKey
            if ( publicKey != null )
            {

                // Cria o hash da password e guarda o mesmo no atributo passwordHash
                setPasswordHash(new String(jpfPassword.getPassword()));

                try
                {
                    // Verifica se o cliente tem conta, e caso tenha chama um metodo para criar o emprestimo
                    // Caso tenha sido criado o emprestimo, fecha a janela
                    if ( clientHasAccount() )
                    {

                        if ( !clienteHasLoan() )
                        {
                            // Cria o emprestimo
                            createLoan();
                            this.setVisible(false);
                        }
                        else
                        {
                            giveAlertFeedback("You already have an active loan.");
                        }

                    }
                    else
                    {

                        giveAlertFeedback("Account not found.");
                    }
                }
                catch ( InterruptedException | NoSuchAlgorithmException | ParseException ex )
                {
                    giveAlertFeedback(ex.getMessage());
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


    }//GEN-LAST:event_jbConfirmAndSignActionPerformed

    private void jbCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbCancelActionPerformed
    {//GEN-HEADEREND:event_jbCancelActionPerformed
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jbCancelActionPerformed

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
                giveAlertFeedback(ex.getMessage());
            }
        }
    }//GEN-LAST:event_jbLoadPublicKeyActionPerformed

    /**
     * Adiciona o movimento do emprestimo à conta do cliente.
     *
     * @param content
     */
    public void addLoanToClientAccount(AccountManager content)
    {
        try
        {
            blockChain.add(content, main);

            dispose();
        }
        catch ( Exception ex )
        {
            giveAlertFeedback(ex.getMessage());
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
                        // Guarda o nome do cliente
                        clientName = info.getName();
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
        return false;
    }

    /**
     * Verifica se o cliente tem conta, caso tenha e a password esteja correta,
     * cria o emprestimo.
     *
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.lang.InterruptedException
     * @throws java.text.ParseException
     */
    private void createLoan() throws NoSuchAlgorithmException, InterruptedException, ParseException
    {

        // Verificar se os conteudos intruduzidos manualmente respeitam os limites do spinner
        jsAmount.commitEdit();
        // Transformar o conteudo do spinner num double
        String value = jsAmount.getValue() + "";
        double amount = Double.parseDouble(value);

        // Criado o emprestimo
        LoanInformation loanInfo = new LoanInformation(
                publicKey,
                clientName,
                amount
        );

        // É criado o movimento de conta
        AccountMovment mov = new AccountMovment(
                publicKey,
                amount,
                "Loan"
        );

        try
        {
            // Assina o movimento
            mov.sign(askForPrivateKey());

            // Adiciona o emprestimo e o movimento de conta à block chain
            blockChain.addLoanBlocks(loanInfo, mov, main, this);

            // Dá feedback ao cliente
            main.giveNormalFeedback("Loan created with success.");
        }
        catch ( Exception ex )
        {
            giveAlertFeedback(ex.getMessage());
        }
    }

    /**
     * Abre uma janela onde permite dar load a uma chave privada.
     *
     * @return
     */
    private PrivateKey askForPrivateKey()
    {
        try
        {
            JFileChooser file = new JFileChooser();
            file.setCurrentDirectory(new File("."));
            int i = file.showOpenDialog(null);
            if ( i == JFileChooser.APPROVE_OPTION )
            {
                byte[] privateKeyBytes = Files.readAllBytes(Paths.get(file.getSelectedFile().getAbsolutePath()));
                PrivateKey privateKey = (PrivateKey) SecurityUtils.getPrivateKey(privateKeyBytes);
                return privateKey;
            }
        }
        catch ( Exception ex )
        {
            giveAlertFeedback(ex.getMessage());
        }

        return null;
    }

    /**
     * Recebe a password, cria o hash e guarda o mesmo.
     *
     * @param password
     */
    private void setPasswordHash(String password)
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
            giveAlertFeedback(ex.getMessage());
        }
    }

    /**
     * Verifica se o cliente tem um emprestimo activo. Caso tenha, faz o
     * pagamento do mesmo.
     *
     * @return
     */
    private boolean clienteHasLoan()
    {

        boolean hasLoan = false;

        for ( Block b : blockChain.chain )
        {
            // Caso se trate de um emprestimo
            if ( b.content instanceof LoanInformation )
            {
                // Individualizar a instancia do LoanInformation
                LoanInformation loanInfo = (LoanInformation) b.content;

                // Se se tratar de um empretimo do cliente
                if ( loanInfo.comparePublicKeys(publicKey) )
                {

                    // Recolhe o que falta pagar do emprestimo
                    double whatsLeftToPay = LoanPayment.whatsLeftToPayInThisLoan(
                            b.hashCode, // O Hash do bloco de emprestimo porque existe referencia a ele em todos os pagamentos do emprestimo
                            blockChain,
                            loanInfo.getAmountWithInterest() // Total a pagar do emprestimo
                    );

                    // Caso ainda não tenha pago tudo, coloca como activo (TRUE)
                    // Seão coloca como FALSE
                    hasLoan = (whatsLeftToPay != 0.0);
                }
            }
        }

        return hasLoan;
    }

    /**
     * Carrega os objetos necessários para completar o emprestimo.
     *
     * @param main
     * @param accounts
     * @param loans
     */
    public void loadMainAndBlockChain(GUI_Main main, BlockChain blockChain)
    {
        this.main = main;
        this.blockChain = blockChain;
    }

    /**
     * Fornece feedback ao utilizador.
     *
     * @param feedback
     */
    private void giveNormalFeedback(String feedback)
    {
        jlFeedback.setText(feedback);
        jlFeedback.setForeground(Color.black);
    }

    /**
     * Fornece feedback de alertas ao utilizador.
     *
     * @param feedback
     */
    private void giveAlertFeedback(String feedback)
    {
        jlFeedback.setText(feedback);
        jlFeedback.setForeground(Color.red);
    }

    /**
     * @param args the command line arguments
     */
    private static void main(String args[])
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
            java.util.logging.Logger.getLogger(GUI_NewLoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch ( InstantiationException ex )
        {
            java.util.logging.Logger.getLogger(GUI_NewLoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch ( IllegalAccessException ex )
        {
            java.util.logging.Logger.getLogger(GUI_NewLoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch ( javax.swing.UnsupportedLookAndFeelException ex )
        {
            java.util.logging.Logger.getLogger(GUI_NewLoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new GUI_NewLoan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbCancel;
    private javax.swing.JButton jbConfirmAndSign;
    private javax.swing.JButton jbLoadPublicKey;
    private javax.swing.JLabel jlFeedback;
    private javax.swing.JPasswordField jpfPassword;
    private javax.swing.JSpinner jsAmount;
    // End of variables declaration//GEN-END:variables
}
