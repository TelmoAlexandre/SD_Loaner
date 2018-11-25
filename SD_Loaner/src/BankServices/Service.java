/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankServices;

import AccountManager.AccountManager;
import BlockChain.Block;
import BlockChain.BlockChain;
import Information.AccountInformation;
import SecureUtils.SecurityUtils;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * Classe abstrata que contem métodos de movimento de conta.
 *
 * @author Telmo
 */
public abstract class Service extends AccountManager
{
    protected byte[] signature;
    protected final double amount;

    public Service(Key publicKey, double amount)
    {
        super(publicKey);
        this.amount = amount;
    }

    public abstract boolean validate(BlockChain bc, Key pbK) throws Exception;
    public abstract double getTrueAmount();

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
     * Percorre a blockchain para contar o dinheiro em movimentos de conta da
     * public key passada por paramatero.
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
            if ( (b.content instanceof AccountMovment || b.content instanceof LoanPayment)
                    && b.content.comparePublicKeys(pbK) )
            {
                // Faz o cast para Service
                Service serv = (Service) b.content;
                
                money += serv.getTrueAmount();
            }

        }
        
        return money;
    }
}
