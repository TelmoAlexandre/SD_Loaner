/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * Esta classe contem informações sobre o cliente.
 *
 * @author Telmo
 */
public class AccountInformation extends AccountManager
{
    private final String name;
    private byte[] passwordHash;

    public AccountInformation(String name, String publickKey)
    {
        this.name = name;

        // publickKey é herdada do AccountManager
        this.publickKey = publickKey;
    }

    /**
     * Recebe a string da password, transforma-a numa Hash (SHA-512) e guarda a
     * mesma num atributo da classe.
     *
     * @param password
     * @throws java.security.NoSuchAlgorithmException
     */
    public void setPassword(String password) throws NoSuchAlgorithmException
    {
        MessageDigest hash = MessageDigest
                .getInstance("SHA-512");

        hash.update(password.getBytes());
        passwordHash = hash.digest();
    }

    /**
     * Retorna a chave pública do cliente.
     *      * 
     * @return 
     */
    public String getPublickKey()
    {
        return publickKey;
    }

    /**
     * Retorna o nome do cliente.
     * 
     * @return 
     */
    public String getName()
    {
        return name;
    }
}
