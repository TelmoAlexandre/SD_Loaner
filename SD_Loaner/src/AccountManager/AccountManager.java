/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountManager;

import java.security.Key;
import java.util.Base64;

/**
 *
 * @author Telmo
 */
public abstract class AccountManager
{

    /**
     * Contem a chave publica do cliente.
     */
    protected Key publicKey;
    
    protected AccountManager (Key publicKey){
        this.publicKey = publicKey;
    }
    
    public Key getPublicKey(){
        return publicKey;
    }
    
    /**
     * Compara uma public key recebida por parâmetro com a do cliente.
     * 
     * @param pbK
     * @return 
     */
    public boolean comparePublicKeys(Key pbK)
    {
        return (Base64.getEncoder().encodeToString(pbK.getEncoded()).equals(
                Base64.getEncoder().encodeToString(publicKey.getEncoded())));
    }
}
