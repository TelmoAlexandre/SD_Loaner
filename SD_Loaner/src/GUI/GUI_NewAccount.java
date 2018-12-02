/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import AccountManager.AccountInformation;
import BlockChain.BlockChain;
import SecureUtils.SecurityUtils;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import javax.swing.JFileChooser;

/**
 *
 * @author Telmo
 */
public class GUI_NewAccount extends javax.swing.JFrame
{
    private GUI_Main main;
    private BlockChain blockChain;
    private Key publicKey;

    /**
     * Creates new form GUI_NewAccount
     */
    public GUI_NewAccount()
    {
        initComponents();

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
        jtfName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jbLoadPublicKey = new javax.swing.JButton();
        jlFeedback = new javax.swing.JLabel();
        jbSave = new javax.swing.JButton();
        jbCancel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jpfPassword = new javax.swing.JPasswordField();
        jpfPasswordConfirm = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Name:");

        jLabel2.setText("Public Key:");

        jbLoadPublicKey.setText("Load");
        jbLoadPublicKey.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbLoadPublicKeyActionPerformed(evt);
            }
        });

        jlFeedback.setText("Fill up your information:");

        jbSave.setText("Save");
        jbSave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbSaveActionPerformed(evt);
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

        jLabel5.setText("Confirm Password:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlFeedback)
                        .addContainerGap(209, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtfName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbCancel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbSave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbLoadPublicKey, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpfPasswordConfirm, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpfPassword, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlFeedback)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfPasswordConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(jbLoadPublicKey)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbCancelActionPerformed
    {//GEN-HEADEREND:event_jbCancelActionPerformed
        main.windowWasCancelled = true;
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jbCancelActionPerformed

    private void jbSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbSaveActionPerformed
    {//GEN-HEADEREND:event_jbSaveActionPerformed

        // Verifica se o campo nome está preenchido
        String name = jtfName.getText();
        if ( !name.equals("") )
        {
            // Verifica se as password estão iguais
            // Quero evitar guardar as password em variáveis por questões de segurança
            if ( new String(jpfPassword.getPassword()).equals(new String(jpfPasswordConfirm.getPassword())) // Garante que as passwords introduzidas são iguais
                    && (!(new String(jpfPassword.getPassword()).equals("")) || !(new String(jpfPasswordConfirm.getPassword()).equals(""))) ) // Garante que estão preenchidas
            {
                // Por fim verifica se foi carregada uma publicKey
                if ( publicKey != null )
                {

                    try
                    {
                        // Cria as informações da conta
                        AccountInformation info = new AccountInformation(
                                name,
                                publicKey,
                                new String(jpfPassword.getPassword())
                        );

                        // Cria e adiciona o bloco à blockChain
                        blockChain.add(info);
                        main.giveNormalFeedback(null, "Account created with success.");

                        // Esconde a janela e dá dispose()
                        main.windowWasCancelled = false;
                        this.setVisible(false);
                        dispose();
                    }
                    catch ( Exception ex )
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
                giveAlertFeedback("Passwords field is empty\n or passwords don't match.");
            }
        }
        else
        {
            giveAlertFeedback("Name field needs to be filled.");
        }


    }//GEN-LAST:event_jbSaveActionPerformed

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
     * Carrega os objectos necessário à criação de uma nova conta.
     *
     * @param main
     * @param accounts
     */
    public void loadMain(GUI_Main main)
    {
        this.main = main;
        this.blockChain = main.blockChain;
    }

    /**
     * Fornece feedback ao utilizador.
     *
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
     * @param feedback
     */
    public void giveAlertFeedback(String feedback)
    {
        jlFeedback.setText(feedback);
        jlFeedback.setForeground(Color.red);
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
            java.util.logging.Logger.getLogger(GUI_NewAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch ( InstantiationException ex )
        {
            java.util.logging.Logger.getLogger(GUI_NewAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch ( IllegalAccessException ex )
        {
            java.util.logging.Logger.getLogger(GUI_NewAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch ( javax.swing.UnsupportedLookAndFeelException ex )
        {
            java.util.logging.Logger.getLogger(GUI_NewAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new GUI_NewAccount().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbCancel;
    private javax.swing.JButton jbLoadPublicKey;
    private javax.swing.JButton jbSave;
    private javax.swing.JLabel jlFeedback;
    private javax.swing.JPasswordField jpfPassword;
    private javax.swing.JPasswordField jpfPasswordConfirm;
    private javax.swing.JTextField jtfName;
    // End of variables declaration//GEN-END:variables
}
