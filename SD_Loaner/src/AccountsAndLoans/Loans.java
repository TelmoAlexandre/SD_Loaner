/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountsAndLoans;

import BlockChain.Block;
import BlockChain.BlockChain;
import Information.LoanInformation;
import BankServices.AccountMovment;
import GUI.GUI_Main;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Contem e gere as BlockChains dos emprestimos.
 *
 * @author Telmo
 */
public class Loans
{

    public ArrayList<BlockChain> loans;

    public Loans()
    {
        loans = new ArrayList<>();
    }

    /**
     * Cria um novo emprestimo.
     * <p>
     * Cria uma nova BlockChain já com o primeiro bloco de informação do
     * emprestimo.
     *
     * @param publicKey Chave do cliente
     * @param clientName Nome do cliente
     * @param amountRequest Motante do emprestimo
     * @param main
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     */
    public void createLoan(Key publicKey, String clientName, double amountRequest, GUI_Main main) throws NoSuchAlgorithmException, InterruptedException
    {
        // Cria o bloco de informação do emprestimo
        LoanInformation loanInfo = new LoanInformation(
                publicKey,
                clientName,
                amountRequest
        );

        // Cria a blockChain do emprestimo, criando o primeiro bloco que irá conter informações do emprestimo.
        // Todas os pagamentos do emprestimo seram guardadas nesta blockChain.
        BlockChain blockChain = new BlockChain(loanInfo, main);

        // Adiciona a nova BlockChain do emprestimo à chain do emprestimo
        loans.add(blockChain);
    }

    /**
     * Recebe uma chave pública e verifica se esse cliente tem um empréstimo activo.
     * 
     * @param key
     * @return 
     */
    public boolean hasActiveLoan(Key key)
    {

        for (BlockChain bc : loans) 
        {
            if(bc.chain.get(0).message.comparePublicKeys(key))
            {
                return isThisLoanActive(bc);
            }
        }

        return true;
    }
    
    /**
     * Recebe um empréstimo e verfifica se se encontra activo.
     * 
     * @param bc
     * @return 
     */
    public boolean isThisLoanActive(BlockChain bc)
    {
        return (AccountMovment.getMyMoney(bc) == 0.0);
    }
    
    /**
     * Recebe a BlockChain do empréstimo e o montante a pagar e valida-os.
     * 
     * @param bc
     * @param amount
     * @return 
     */
    public boolean loanValidate(BlockChain bc, double amount)
    {
        return (amount <= AccountMovment.getMyMoney(bc));
    }
    
    /**
     * Imprime a blockChain do emprestimo.
     *
     * @param publicKey Chave do cliente
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String toString(Key publicKey) throws NoSuchAlgorithmException
    {
        // Booleano para verificar se foi encontrada o emprestimo
        boolean found = false;

        for (BlockChain bc : loans)
        {
            // Individualiza o primeiro bloco da chain. Este bloco apenas contem informação do emprestimop
            LoanInformation info = (LoanInformation) bc.chain.get(0).message;

            if (info.comparePublicKeys(publicKey) && hasActiveLoan(publicKey))
            {
                // Se entrar aqui, então encontrou a conta do cliente
                found = true;

                // Retorna a blockChain do emprestimo
                return bc.toString();
            }
        }

        if (!found)
        {
            throw new RuntimeException("Loan not found.");
        }

        return "Loan not found.";
    }

    /**
     * Corre a Block Chain e calcula o montante pagamentos já efetuados.
     * <p>
     * Atualiza o bloco de informação do empréstimo para dispor o que falta
     * pagar.
     *
     * @param bc
     * @return
     */
    public static double getWhatsLeftToPay(BlockChain bc)
    {
        LoanInformation loanInfo = (LoanInformation) bc.chain.get(0).message;
        double money = loanInfo.getAmountWithInterest();
        boolean isFirst = true;

        for (Block b : bc.chain)
        {
            // Salta o primeiro bloco da chain pois este é apenas informação do cliente.
            if (isFirst)
            {
                isFirst = !isFirst;
            } else
            {
                AccountMovment mov = (AccountMovment) b.message;

                money += mov.getMovmentValue();
            }

        }

        LoanInformation info = (LoanInformation) bc.chain.get(0).message;
        return money;
    }

    /**
     * Retorna o bloco de informação de todos os emprestimos.
     *
     * @return
     */
    @Override
    public String toString()
    {
        StringBuilder txt = new StringBuilder();

        for (BlockChain bc : loans)
        {
            txt.append(bc.chain.get(0).toString()).append("\n\n");
        }

        return txt.toString();
    }
}
