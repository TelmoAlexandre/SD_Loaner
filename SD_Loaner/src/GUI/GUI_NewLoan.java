/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import AccountManager.AccountInformation;
import AccountManager.AccountManager;
import BankServices.Loan;
import BlockChain.Block;
import BlockChain.BlockChain;
import Utilities.SecurityUtils;
import java.awt.Color;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
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
     * Creates new form NewJFrame
     */
    public GUI_NewLoan()
    {
        initComponents();

        // Centra a janela
        this.setLocationRelativeTo(null);
    }

    public GUI_NewLoan(GUI_Main main)
    {
        initComponents();

        // Centra a janela
        this.setLocationRelativeTo(null);

        this.main = main;

        // Recolhe a informação necessária
        this.blockChain = main.blockChain;
        this.publicKey = main.publicKey;
        this.passwordHash = main.passwordHash;
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
        jSeparator1 = new javax.swing.JSeparator();
        jsAmount = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

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

        jsAmount.setModel(new javax.swing.SpinnerNumberModel(100, 1, 250000, 100));

        jLabel1.setText("Amount:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jlFeedback))
                        .addGap(0, 83, Short.MAX_VALUE))
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
        try
        {
            // Verificar se os conteudos intruduzidos manualmente respeitam os limites do spinner
            jsAmount.commitEdit();
            // Transformar o conteudo do spinner num double
            String value = jsAmount.getValue() + "";
            double amount = Double.parseDouble(value);
            
            startLoanProcess(askForPrivateKey(), amount);
        }
        catch ( ParseException ex )
        {
            System.out.println("Error commiting edit in jSpinner - GUI_NewLoan - Confirm and sign button");
            System.out.println(ex.getMessage());
            //Logger.getLogger(GUI_NewLoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbConfirmAndSignActionPerformed

    private void jbCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbCancelActionPerformed
    {//GEN-HEADEREND:event_jbCancelActionPerformed
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jbCancelActionPerformed

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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbCancel;
    private javax.swing.JButton jbConfirmAndSign;
    private javax.swing.JLabel jlFeedback;
    private javax.swing.JSpinner jsAmount;
    // End of variables declaration//GEN-END:variables

    public boolean startLoanProcess(PrivateKey pvK, double amount)
    {
        try
        {
            // Verifica se o cliente tem conta, e caso tenha chama um metodo para criar o emprestimo
            // Caso tenha sido criado o emprestimo, fecha a janela
            if ( clientHasAccount() )
            {
                // Cria o emprestimo
                return createLoan(pvK, amount);
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
        
        return false;
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
    private boolean createLoan(PrivateKey pvK, double amount) throws NoSuchAlgorithmException, InterruptedException, ParseException
    {
        // Criado o emprestimo
        Loan loanInfo = new Loan(
                publicKey,
                clientName,
                amount
        );
        try
        {
            // Caso o cliente não tenha um emprestimo activo
            if ( loanInfo.validate(blockChain) )
            {
                try
                {
                    // Assina o movimento
                    loanInfo.sign(pvK);

                    // Adiciona o emprestimo e o movimento de conta à block chain
                    blockChain.add(loanInfo);

                    // Dá feedback ao cliente
                    main.giveNormalFeedback(null, "Loan created with success.");
                    this.setVisible(false);
                    this.dispose();
                }
                catch ( Exception ex )
                {
                    giveAlertFeedback("Private Key not valid.");
                    return false;
                }
            }
            else
            {
                giveAlertFeedback("You already have an active loan.");
                return false;
            }
        }
        catch ( Exception ex )
        {
            giveAlertFeedback("We're experiencing technical difficulties");
            return false;
        }
        return true;
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
     * Fornece feedback de alertas ao utilizador.
     *
     * @param feedback
     */
    private void giveAlertFeedback(String feedback)
    {
        jlFeedback.setText(feedback);
        jlFeedback.setForeground(Color.red);
    }

}
