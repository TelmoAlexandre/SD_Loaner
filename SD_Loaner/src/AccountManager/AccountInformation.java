/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * Esta classe contem informações sobre o cliente.
 *
 * @author Telmo
 */
public class AccountInformation extends AccountManager
{
    private final String name;
    private String passwordHash;

    public AccountInformation(String name, String publickKey, String password) throws NoSuchAlgorithmException
    {
        // publicKey
        super(publickKey);
        
        this.name = name;
        
        setPassword(password);
    }

    /**
     * Recebe a string da password, transforma-a numa Hash (SHA-512) e guarda a
     * mesma num atributo da classe.
     *
     * @param password
     * @throws java.security.NoSuchAlgorithmException
     */
    private void setPassword(String password) throws NoSuchAlgorithmException
    {
        MessageDigest hash = MessageDigest
                .getInstance("SHA-512");

        hash.update(password.getBytes());
        passwordHash = Base64.getEncoder().encodeToString(hash.digest());
    }

    /**
     * Compara o hash da password de login com o hash guardado quando a password foi definida.
     * <p>O booleano resultanto deste método informa se a autenticação do cliente é bem sucedida.
     * 
     * @param password
     * @return booleano que informa se a autenticação do cliente é bem sucedida.
     * @throws NoSuchAlgorithmException 
     */
    public boolean authenticateLogin(String password) throws NoSuchAlgorithmException
    {
        // Criar um hash (SHA-512) da password introduzida no login
        MessageDigest hash = MessageDigest
                .getInstance("SHA-512");
        hash.update(password.getBytes());

        // Transforma a passoword login recebida por paramêtro num hash.
        // Transforma o hash num Base64 e compara com o atributo passwordHash que guarda o hash da password quando esta foi definida.
        // Devolve o resultado do booleano da condição.
        return (Base64.getEncoder().encodeToString(hash.digest()).equals(passwordHash));
    }

    /**
     * Retorna a chave pública do cliente. 
     * 
     * @return publickKey
     */
    public String getPublickKey()
    {
        return publickKey;
    }

    /**
     * Retorna o nome do cliente.
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
