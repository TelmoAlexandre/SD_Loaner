/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountManager;

/**
 *
 * @author Telmo
 */
public abstract class AccountManager
{

    /**
     * Contem a chave publica do cliente.
     */
    protected String publickKey;
    
    protected AccountManager (String publicKey){
        this.publickKey = publicKey;
    }
    
    protected String getPublicKey(){
        return publickKey;
    }
}
