/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountServices;

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
public class AccountMovments extends Service
{
    double amount;
    String type;
    byte[] signature;

    public AccountMovments(Key publicKey, double value, String type)
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
        byte[] data = 
                (Base64.getEncoder().encodeToString(publicKey.getEncoded()) + 
                amount
                ).getBytes();

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
    public boolean validate(BlockChain bc) throws Exception
    {
        if(type.equals("Deposite")){
            return verifySignature();
        }
        else
        {
            return verifySignature();
        }
    }

    public boolean verifySignature() throws Exception{
        byte[] data = 
                (Base64.getEncoder().encodeToString(publicKey.getEncoded()) + 
                amount
                ).getBytes();
        
        return SecurityUtils.verifyRSA(
                data, 
                signature,
                (PublicKey)SecurityUtils.getPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()))
                );
    }
    
    @Override
    public String toString()
    {
        return " Type: " + type + "\n Amount: " + amount;
    }
}
