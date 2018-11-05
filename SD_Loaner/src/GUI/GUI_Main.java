/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import AccountManager.AccountInformation;
import AccountServices.AccountMovment;
import BlockChain.Accounts;
import BlockChain.BlockChain;
import SecureUtils.SecurityUtils;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Telmo
 */
public class GUI_Main extends javax.swing.JFrame
{
    Accounts accounts;
    public static double amount;
    public static Key publicKey;
    public static String passwordHash;
    
    // Manager das GUIs
    GUI_Manager manager;

    /**
     * Creates new form GUI
     */
    public GUI_Main()
    {
        initComponents();

        manager = new GUI_Manager(this);
        
        // Centra a janela
        this.setLocationRelativeTo(null);

        try
        {
            accounts = new Accounts();
            jtaLedger.setText(accounts.toString());
        }
        catch ( Exception ex )
        {
            jlFeedback.setText(ex.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jtaLedger = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jbCheckClientAccounts = new javax.swing.JButton();
        jbCheckLoans = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jbSetLoanInterest = new javax.swing.JButton();
        jbAddFunds = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jbDeposit = new javax.swing.JButton();
        jbWithdrawal = new javax.swing.JButton();
        jbCheckMoney = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jbRequestLoan = new javax.swing.JButton();
        jbLoanPayment = new javax.swing.JButton();
        jbCheckRemainingPayments = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jbGenerateRSAKeys = new javax.swing.JButton();
        jbCreateNewAccount = new javax.swing.JButton();
        jbExit = new javax.swing.JButton();
        jlFeedback = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtaLedger.setColumns(20);
        jtaLedger.setRows(5);
        jScrollPane1.setViewportView(jtaLedger);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Bank"));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Consult"));

        jbCheckClientAccounts.setText("Client Accounts");
        jbCheckClientAccounts.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbCheckClientAccountsActionPerformed(evt);
            }
        });

        jbCheckLoans.setText("Loans");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbCheckLoans, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbCheckClientAccounts, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbCheckClientAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbCheckLoans, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Services"));

        jbSetLoanInterest.setText("Set Loan Interest");

        jbAddFunds.setText("Add Funds");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbSetLoanInterest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbAddFunds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbSetLoanInterest, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbAddFunds, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Client"));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Account Management"));

        jbDeposit.setText("Deposit");
        jbDeposit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbDepositActionPerformed(evt);
            }
        });

        jbWithdrawal.setText("Withdrawal");
        jbWithdrawal.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbWithdrawalActionPerformed(evt);
            }
        });

        jbCheckMoney.setText("Check money");
        jbCheckMoney.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbCheckMoneyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbDeposit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbWithdrawal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbCheckMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbDeposit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbWithdrawal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbCheckMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Services"));

        jbRequestLoan.setText("Request a Loan");

        jbLoanPayment.setText("Loan Payment");

        jbCheckRemainingPayments.setText("Check remaining payments");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbRequestLoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbLoanPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbCheckRemainingPayments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbRequestLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbLoanPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbCheckRemainingPayments, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("New Account"));

        jbGenerateRSAKeys.setText("Generate RSA Keys");
        jbGenerateRSAKeys.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbGenerateRSAKeysActionPerformed(evt);
            }
        });

        jbCreateNewAccount.setText("Create a new account");
        jbCreateNewAccount.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbCreateNewAccountActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbGenerateRSAKeys, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbCreateNewAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbGenerateRSAKeys, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbCreateNewAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbExit.setText("Exit");
        jbExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbExitActionPerformed(evt);
            }
        });

        jlFeedback.setText("Choose your option");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlFeedback, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jbExit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlFeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbExitActionPerformed
    {//GEN-HEADEREND:event_jbExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbExitActionPerformed

    private void jbGenerateRSAKeysActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbGenerateRSAKeysActionPerformed
    {//GEN-HEADEREND:event_jbGenerateRSAKeysActionPerformed
        KeyPair keys;

        try
        {
            // Gera um par de chaves
            keys = SecurityUtils.generateKeyPair(1024);

            // Mostra uma janela ao utilizador a informar sobre os seguintes procedimentos.
            JOptionPane.showMessageDialog(
                    null, 
                    "Two keys were generated for you.\n\nFirst save the public key.\nThen save the private key."
            );

            // Configurações JFileChooser
            JFileChooser file = new JFileChooser();
            // Pasta onde guardar. Neste caso é a pasta de origem do projeto
            file.setCurrentDirectory(new File("."));
            // Nome recomendado do ficheiro
            file.setSelectedFile(new File("public_key"));
            // Nome da janela de permite escolher a localização onde o ficheiro será guardado
            file.setDialogTitle("Save the public key");

            // Pede uma localização para guardar a chave pública. Guarda a opção escolhida pelo utilizador na variável i
            int i = file.showSaveDialog(null);

            if ( i == JFileChooser.APPROVE_OPTION )
            {

                // Caso seja clicado no butao save da janela, guardar a chave publica na localização escolhida
                SecurityUtils.saveKey(keys.getPublic(), file.getSelectedFile().getAbsolutePath());

                // Se a chave publica for guardada, então pede para guardar a privada
                // Nome recomendado do ficheiro
                file.setSelectedFile(new File("private_key"));
                // Nome da janela de permite escolher a localização onde o ficheiro será guardado
                file.setDialogTitle("Save the private key");

                i = file.showSaveDialog(null);

                if ( i == JFileChooser.APPROVE_OPTION )
                {
                    // Caso seja clicado no butao save da janela, guardar a chave privada na localização escolhida
                    SecurityUtils.saveKey(
                            keys.getPrivate(), 
                            file.getSelectedFile().getAbsolutePath()
                    );
                }
            }

        }
        catch ( Exception e )
        {
            giveAlertFeedback(
                    e.getMessage()
            );
        }

    }//GEN-LAST:event_jbGenerateRSAKeysActionPerformed

    private void jbCreateNewAccountActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbCreateNewAccountActionPerformed
    {//GEN-HEADEREND:event_jbCreateNewAccountActionPerformed
        GUI_NewAccount newAccount = new GUI_NewAccount();
        newAccount.setVisible(true);
    }//GEN-LAST:event_jbCreateNewAccountActionPerformed

    private void jbDepositActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbDepositActionPerformed
    {//GEN-HEADEREND:event_jbDepositActionPerformed

        GUI_DepositWithdrawal deposit = new GUI_DepositWithdrawal();
        deposit.setVisible(true);
        deposit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Aguarda que o jframe seja fechado
        deposit.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
                // Chama o método que irá criar o movimento de conta
                try
                {
                    performAccountMovment(
                            "Deposit",
                            askForPrivateKey()
                    );
                }
                catch ( NoSuchAlgorithmException ex )
                {
                    giveAlertFeedback(
                            ex.getMessage()
                    );
                }
            }

        });

    }//GEN-LAST:event_jbDepositActionPerformed

    private void jbWithdrawalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbWithdrawalActionPerformed
    {//GEN-HEADEREND:event_jbWithdrawalActionPerformed
        GUI_DepositWithdrawal withdrawal = new GUI_DepositWithdrawal();
        withdrawal.setVisible(true);
        withdrawal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Aguarda que o jframe seja fechado
        withdrawal.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {

                // Chama o método que irá criar o movimento de conta
                try
                {
                    performAccountMovment(
                            "Withdrawal",
                            askForPrivateKey()
                    );
                }
                catch ( NoSuchAlgorithmException ex )
                {
                    giveAlertFeedback(
                            ex.getMessage()
                    );
                }
            }

        });
    }//GEN-LAST:event_jbWithdrawalActionPerformed

    private void jbCheckMoneyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbCheckMoneyActionPerformed
    {//GEN-HEADEREND:event_jbCheckMoneyActionPerformed

        GUI_CheckMyMoney cmm = new GUI_CheckMyMoney();
        cmm.setVisible(true);
        cmm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cmm.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {

                // Booleano para verificar se foi encontrada a conta do cliente.
                boolean found = false;

                for ( BlockChain bc : accounts.accounts )
                {
                    // Individualiza o primeiro bloco da chain. Este bloco apenas contem informação
                    AccountInformation info = (AccountInformation) bc.chain.get(0).message;

                    if ( info.comparePublicKeys(publicKey) )
                    {
                        // Verifica o dineiro do cliente.
                        // O método estático getMyMoney atualiza o primeiro bloco da chain, bloco esse que contém
                        // as informações do cliente, com o dinheiro total da conta do cliente.
                        AccountMovment.getMyMoney(bc);
                        jtaLedger.setText(accounts.toString());
                        found = !found;
                        break;
                    }
                }

                if ( !found )
                {
                    giveAlertFeedback("Account not found.");
                }

            }
        });


    }//GEN-LAST:event_jbCheckMoneyActionPerformed

    private void jbCheckClientAccountsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbCheckClientAccountsActionPerformed
    {//GEN-HEADEREND:event_jbCheckClientAccountsActionPerformed
        jtaLedger.setText("");
    }//GEN-LAST:event_jbCheckClientAccountsActionPerformed

    public void performAccountMovment(String type, PrivateKey pvK) throws NoSuchAlgorithmException
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
                found = !found;

                // Verifica a password do cliente
                if ( info.authenticateLogin(passwordHash) )
                {
                    // Chegando aqui, existe certeza que se trata do cliente em questão

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
                        bc.add(mov);
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
                    giveAlertFeedback("Wrong passoword.");
                }

                break;
            }
        }

        if ( !found )
        {
            giveAlertFeedback("Account not found.");
        }

        jtaLedger.setText(accounts.toString());
    }

    public PrivateKey askForPrivateKey()
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

    public void giveNormalFeedback(String feedback)
    {
        jlFeedback.setText(feedback);
        jlFeedback.setForeground(Color.black);
    }

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
            java.util.logging.Logger.getLogger(GUI_Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        catch ( InstantiationException ex )
        {
            java.util.logging.Logger.getLogger(GUI_Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        catch ( IllegalAccessException ex )
        {
            java.util.logging.Logger.getLogger(GUI_Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        catch ( javax.swing.UnsupportedLookAndFeelException ex )
        {
            java.util.logging.Logger.getLogger(GUI_Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new GUI_Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAddFunds;
    private javax.swing.JButton jbCheckClientAccounts;
    private javax.swing.JButton jbCheckLoans;
    private javax.swing.JButton jbCheckMoney;
    private javax.swing.JButton jbCheckRemainingPayments;
    private javax.swing.JButton jbCreateNewAccount;
    private javax.swing.JButton jbDeposit;
    private javax.swing.JButton jbExit;
    private javax.swing.JButton jbGenerateRSAKeys;
    private javax.swing.JButton jbLoanPayment;
    private javax.swing.JButton jbRequestLoan;
    private javax.swing.JButton jbSetLoanInterest;
    private javax.swing.JButton jbWithdrawal;
    private javax.swing.JLabel jlFeedback;
    private javax.swing.JTextArea jtaLedger;
    // End of variables declaration//GEN-END:variables
}
