/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import AccountManager.AccountInformation;
import AccountManager.LoanInformation;
import AccountServices.AccountMovment;
import BlockChain.Accounts;
import BlockChain.BlockChain;
import BlockChain.Loans;
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
import java.util.Base64;
import javax.swing.JFileChooser;

/**
 *
 * @author Telmo
 */
public class GUI_AccountMovment extends javax.swing.JFrame
{
    private Key publicKey;
    private GUI_Main main;
    private Accounts accounts;
    private Loans loans;
    private String passwordHash, movType;

    /**
     * Creates new form GUI_DepositWithdrawal
     */
    public GUI_AccountMovment()
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jbLoadPublicKey = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jbConfirm = new javax.swing.JButton();
        jbCancel = new javax.swing.JButton();
        jlFeedback = new javax.swing.JLabel();
        jpfPassword = new javax.swing.JPasswordField();
        jsAmount = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Amount:");

        jLabel2.setText("Public Key:");

        jbLoadPublicKey.setText("Load");
        jbLoadPublicKey.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbLoadPublicKeyActionPerformed(evt);
            }
        });

        jLabel3.setText("Password:");

        jbConfirm.setText("Confirm and Sign");
        jbConfirm.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbConfirmActionPerformed(evt);
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

        jlFeedback.setText("Fill up the fields:");

        jsAmount.setModel(new javax.swing.SpinnerNumberModel(100, 1, 250000, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jbLoadPublicKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlFeedback)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator1)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jbConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jbCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jLabel1)
                                .addComponent(jLabel3))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addComponent(jsAmount)
                    .addComponent(jpfPassword))
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
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jbLoadPublicKey))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbConfirm)
                    .addComponent(jbCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbCancelActionPerformed
    {//GEN-HEADEREND:event_jbCancelActionPerformed
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jbCancelActionPerformed

    private void jbConfirmActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbConfirmActionPerformed
    {//GEN-HEADEREND:event_jbConfirmActionPerformed
        // Verificar se o cliente tem um emprestimo activo ou se o tipo de movimento é diferente de pagamento de emprestimo
        if ( !movType.equals("Loan Payment") || clienteHasLoan() )
        {

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
                        performAccountMovment(
                                movType, // Informa o tipo de movimento ( 'Deposit' ou 'Withdawal' )
                                askForPrivateKey() // Abre uma janela para ser escolhido o ficheiro com a chave privada
                        );

                        // Fornece feedback ao utilizador
                        main.giveNormalFeedback(movType + " has been successful");

                        // Caso consiga criar um movimento, sair da janela
                        this.setVisible(false);
                        dispose();
                    }
                    catch ( NoSuchAlgorithmException ex )
                    {
                        // Caso ocorra algum erro, informa o utilizador via a label de feedback
                        giveAlertFeedback(
                                ex.getMessage()
                        );
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
        }

    }//GEN-LAST:event_jbConfirmActionPerformed

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

    /**
     * Atualiza o pagamento do emprestimo na blockchain de emprestimos.
     *
     * @return
     */
    public BlockChain findLoanBlockChain()
    {
        for ( BlockChain bc : loans.loans )
        {
            // Recuperar o bloco com informações do emprestimo
            LoanInformation loanInfo = (LoanInformation) bc.chain.get(0).message;

            // Encontra o emprestimo do cliente
            if ( loanInfo.comparePublicKeys(publicKey) )
            {
                // Retorna a blockchain que contém o emprestimo
                return bc;
            }
        }
        return null;
    }

    /**
     * Cria a movimentação pretendida pelo cliente
     * <p>
     * Faz a verificação da autenticidade do cliente via a sua password.
     *
     * @param type
     * @param pvK
     * @throws NoSuchAlgorithmException
     */
    private void performAccountMovment(String type, PrivateKey pvK) throws NoSuchAlgorithmException
    {

        boolean found = false;

        for ( BlockChain bc : accounts.accounts )
        {

            // Individualiza o primeiro bloco da chain. Este bloco apenas contem informação
            AccountInformation info = (AccountInformation) bc.chain.get(0).message;

            // Verifica se se trata da block chain do cliente em questão
            if ( info.comparePublicKeys(publicKey) )
            {
                // Assinala que encontrou a conta do cliente
                found = true;

                // Verifica a password do cliente
                if ( info.authenticateLogin(passwordHash) )
                {
                    // Chegando aqui, existe certeza que se trata do cliente em questão

                    // Transformar o conteudo do spinner num double
                    String value = jsAmount.getValue() + "";
                    double amount = Double.parseDouble(value);

                    // É criado o movimento de conta
                    AccountMovment mov = new AccountMovment(
                            publicKey,
                            amount,
                            type);

                    try
                    {
                        // Assina o movimento
                        mov.sign(pvK);

                        // Adiciona o movimento de conta à block chain do cliente
                        bc.add(mov, main);

                        if ( movType.equals("Loan Payment") )
                        {
                            BlockChain loanBC = findLoanBlockChain();

                            loanBC.add(mov, main);

                            if ( Loans.getMyMoney(loanBC) == 0.0 )
                            {
                                info.setActiveLoan(false);
                            }
                        }
                    }
                    catch ( Exception ex )
                    {
                        giveAlertFeedback(ex.getMessage());
                    }

                    // Verifica o dineiro do cliente.
                    // O método estático getMyMoney atualiza o primeiro bloco da chain, bloco esse que contém
                    // as informações do cliente, com o dinheiro total da conta do cliente.
                    AccountMovment.getMyMoney(bc);

                    // Dá feedback ao cliente
                    giveNormalFeedback(type + " completed with success.");

                }
                else
                {
                    giveAlertFeedback("Wrong passoword provided.");
                }

                break;
            }
        }

        if ( !found )
        {
            giveAlertFeedback("Account not found.");
        }
    }

    /**
     * Carrega os objetos necessários para completar o movimento de conta.
     *
     * @param main
     * @param accounts
     * @param loans
     */
    public void loadMainAndChains(GUI_Main main, Accounts accounts, Loans loans, String movType)
    {
        this.main = main;
        this.accounts = accounts;
        this.loans = loans;
        this.movType = movType;
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

    private boolean clienteHasLoan()
    {
        for ( BlockChain bc : loans.loans )
        {
            // Individualiza o primeiro bloco da chain. Este bloco apenas contem informação
            LoanInformation info = (LoanInformation) bc.chain.get(0).message;

            // Verifica se se trata da block chain do cliente em questão
            if ( info.comparePublicKeys(publicKey) )
            {
                // Transformar o conteudo do spinner num double
                String value = jsAmount.getValue() + "";
                double amount = Double.parseDouble(value);

                if ( info.validate(amount) )
                {
                    return true;
                }
                else
                {
                    giveAlertFeedback("Loan payment is bigger than what's left to pay.");
                    return false;
                }

            }
        }
        giveAlertFeedback("You have no active loan.");
        return false;
    }

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
            java.util.logging.Logger.getLogger(GUI_AccountMovment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch ( InstantiationException ex )
        {
            java.util.logging.Logger.getLogger(GUI_AccountMovment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch ( IllegalAccessException ex )
        {
            java.util.logging.Logger.getLogger(GUI_AccountMovment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch ( javax.swing.UnsupportedLookAndFeelException ex )
        {
            java.util.logging.Logger.getLogger(GUI_AccountMovment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new GUI_AccountMovment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbCancel;
    private javax.swing.JButton jbConfirm;
    private javax.swing.JButton jbLoadPublicKey;
    private javax.swing.JLabel jlFeedback;
    private javax.swing.JPasswordField jpfPassword;
    private javax.swing.JSpinner jsAmount;
    // End of variables declaration//GEN-END:variables
}
