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
    double value;
    String type;
    byte[] signature;

    public AccountMovments(String publicKey, double value, String type, byte[] signature)
    {
        super(publicKey);
        this.value = value;
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
        // 
        byte[] data = (type + value).getBytes();

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

}
