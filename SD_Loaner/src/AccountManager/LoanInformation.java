/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountManager;

import java.security.Key;

/**
 *
 * @author Telmo
 */
public class LoanInformation extends AccountManager
{
    private final double amountRequest, interest;
    private double leftToPay;
    private boolean isActive = true;
    private final String clientName;

    /**
     *
     * @param publicKey Chave pública do cliente
     * @param clientName Nome do cliente
     * @param amountRequest Montante do emprestimo
     */
    public LoanInformation(Key publicKey, String clientName, double amountRequest)
    {
        super(publicKey);
        this.amountRequest = amountRequest;
        this.interest = 20.0;
        this.clientName = clientName;

        // Calcula o montante a pagar já com os interesses incluidos
        leftToPay = (amountRequest * (100 + 20.0)) / 100;
    }

    /**
     * Valida se o pagamento é possivel.
     * 
     * @param amountToPay
     * @return 
     */
    public boolean validate(double amountToPay)
    {
        return (amountToPay <= leftToPay);
    }

    /**
     * Permite definir o montante restante do emprestimo.
     * <p>Permite saber que o emprestimo ainda se encontra ativo.
     *
     * @param lefToPay 
     */
    public void setLeftToPay(double lefToPay)
    {
        this.leftToPay = lefToPay;
    }

    /**
     * Retorna o montante que ainda falta pagar do emprestimo.
     *
     * @return
     */
    public double getLeftToPay()
    {
        return leftToPay;
    }

    public boolean isTheLoanActive()
    {
        return isActive;
    }
    
    /**
     * Permite alterar o estado do emprestimo.
     * 
     * @param bool 
     */
    public void setIsActive(boolean bool)
    {
        this.isActive = bool;
    }
    @Override
    public String toString()
    {
        return "Client: " + clientName + "\nLoan amount: " + amountRequest + "€\nLeft to pay: " + leftToPay + "€";
    }

}
