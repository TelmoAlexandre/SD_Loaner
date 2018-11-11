/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import AccountManager.AccountInformation;
import AccountManager.LoanInformation;
import AccountServices.AccountMovment;
import GUI.GUI_Main;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
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
     * Cria um novo emprestimo
     *
     * @param publicKey Chave do cliente
     * @param clientName Nome do cliente
     * @param amountRequest Motante do emprestimo
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

        for ( BlockChain bc : loans )
        {
            // Individualiza o primeiro bloco da chain. Este bloco apenas contem informação do emprestimop
            LoanInformation info = (LoanInformation) bc.chain.get(0).message;

            if ( info.comparePublicKeys(publicKey) && info.isTheLoanActive())
            {
                // Se entrar aqui, então encontrou a conta do cliente
                found = true;

                // Retorna a blockChain do emprestimo
                return bc.toString();
            }
        }

        if ( !found )
        {
            throw new RuntimeException("Loan not found.");
        }

        return "Loan not found.";
    }

    /**
     * Corre a Block Chain e calcula o montante de dinheiro do cliente.
     * <p>
     * Atualiza o bloco de informação do cliente para conter o total de
     * dinheiro.
     *
     * @param bc
     * @return
     */
    public static double getMyMoney(BlockChain bc)
    {
        LoanInformation loanInfo = (LoanInformation) bc.chain.get(0).message;
        double money = loanInfo.getLeftToPay();
        boolean isFirst = true;

        for ( Block b : bc.chain )
        {
            // Salta o primeiro bloco da chain pois este é apenas informação do cliente.
            if ( isFirst )
            {
                isFirst = !isFirst;
            }
            else
            {
                AccountMovment mov = (AccountMovment) b.message;

                money += mov.getMovmentValue();
            }

        }

        LoanInformation info = (LoanInformation) bc.chain.get(0).message;
        // Caso o emprestimo estja pago, ativa os booleanos
        if ( money == 0.0 )
        {
            info.setIsActive(false);
        }
        info.setLeftToPay(money);
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

        for ( BlockChain bc : loans )
        {
            txt.append(bc.chain.get(0).toString()).append("\n\n");
        }

        return txt.toString();
    }
}
