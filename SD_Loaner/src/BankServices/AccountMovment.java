/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankServices;

import BlockChain.BlockChain;
import java.security.Key;

/**
 *
 * @author Telmo
 */
public class AccountMovment extends Service
{
    private final String type;

    public AccountMovment(Key publicKey, double amount, String type)
    {
        super(publicKey, amount);
        this.type = type;
    }

    /**
     * Valida a integridade do movimento de conta.
     *
     * @param bc
     * @param pbK
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public boolean validate(BlockChain bc, Key pbK) throws Exception
    {
        if ( type.equals("Withdrawal"))
        {
            return (amount <= getMyMoney(bc, pbK) && verifySignature());
        }
        else
        {
            return verifySignature();
        }
    }

    /**
     * Retorna o valor do movimento. Se for deposito ou deposito proveniente de
     * um empréstimo, retorna positivo, se for levantamento retorna negativo.
     *
     * @return
     */
    @Override
    public double getTrueAmount()
    {
        return (type.equals("Withdrawal")) ? amount * (-1.0) : amount;
    }

    /**
     * Retorna os atributos do movimento como String.
     *
     * @return type + amount.
     */
    @Override
    public String toString()
    {
        return " Type: " + type + "\n Amount: " + amount + "€";
    }
}
