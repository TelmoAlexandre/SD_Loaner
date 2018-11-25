/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankServices;

import Information.AccountInformation;
import BlockChain.Block;
import BlockChain.BlockChain;
import SecureUtils.SecurityUtils;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 *
 * @author Telmo
 */
public class AccountMovment extends Service
{
    private final double amount;
    private final String type;
    private byte[] signature;

    public AccountMovment(Key publicKey, double value, String type)
    {
        super(publicKey);
        this.amount = value;
        this.type = type;
    }

    /**
     * Assina um movimento de conta.
     *
     * @param pvKey - Chave privada do cliente.
     * @throws Exception
     */
    public void sign(PrivateKey pvKey) throws Exception
    {
        byte[] data
                = (Base64.getEncoder().encodeToString(publicKey.getEncoded())
                        + amount).getBytes();

        signature = SecurityUtils.signRSA(data, pvKey);
    }

    /**
     * Valida a integridade do movimento de conta.
     *
     * @param bc
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public boolean validate(BlockChain bc, Key pbK) throws Exception
    {
        if ( type.equals("Withdrawal") || type.equals("Loan Payment") )
        {
            return (amount <= getMyMoney(bc, pbK) && verifySignature());
        }
        else
        {
            return verifySignature();
        }
    }

    /**
     * Percorre a blockchain para contar o dinheiro em movimentos de conta da public key passada por paramatero.
     * 
     * @param bc BlockChain a ser percorrida.
     * @param pbK Chave publica do cliente.
     * @return O montante atual que se encontra na conta.
     */
    public static double getMyMoney(BlockChain bc, Key pbK)
    {
        double money = 0.0;

        for ( Block b : bc.chain )
        {
            // Garante que o conteudo do bloco é um movimento de conta e que se trata de um bloco da public key em questão
            if (b.content instanceof AccountMovment && b.content.comparePublicKeys(pbK))
            {
                AccountMovment mov = (AccountMovment) b.content;
                money += mov.getMovmentValue();
            }
                

        }

        AccountInformation info = (AccountInformation) bc.chain.get(0).content;
        return money;
    }

    /**
     * Retorna o valor do movimento. Se for deposito ou deposito proveniente de
     * um empréstimo, retorna positivo, se for levantamento ou pagamento de
     * empréstimo retorna negativo.
     *
     * @return
     */
    public double getMovmentValue()
    {
        return (type.equals("Withdrawal") || type.equals("Loan Payment")) ? amount * (-1.0) : amount;
    }

    /**
     * Verifica a integridade da assinatura do movimento.
     *
     * @return
     * @throws Exception
     */
    public boolean verifySignature() throws Exception
    {
        byte[] data
                = (Base64.getEncoder().encodeToString(publicKey.getEncoded())
                        + amount).getBytes();

        return SecurityUtils.verifyRSA(
                data,
                signature,
                (PublicKey) SecurityUtils.getPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()))
        );
    }

    /**
     * Retorna os atributos do movimento como String.
     *
     * @return type + amount.
     */
    @Override
    public String toString()
    {
        return " Type: " + type + "\n Amount: " + amount;
    }
}
