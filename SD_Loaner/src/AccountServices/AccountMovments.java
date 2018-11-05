/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountServices;

import SecureUtils.SecurityUtils;
import java.security.PrivateKey;

/**
 *
 * @author Telmo
 */
public class AccountMovments extends Service
{
    double amount;
    String type;
    byte[] signature;

    public AccountMovments(String publicKey, double value, String type)
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
        byte[] data = (publickKey + amount).getBytes();

        signature = SecurityUtils.signRSA(data, pvKey);
    }
    
    /**
     * Valida a integridade do movimento de conta. 
     * 
     */
    @Override
    public void validate()
    {
        
    }

    
    @Override
    public String toString()
    {
        return " Type: " + type + "\n Amount: " + amount;
    }
}
