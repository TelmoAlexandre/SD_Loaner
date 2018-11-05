/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountManager;

import java.security.Key;
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
    private double money;

    public AccountInformation(String name, Key publickKey, String password) throws NoSuchAlgorithmException
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
     * Compara o hash da password de login com o hash guardado quando a password
     * foi definida.
     * <p>
     * O booleano resultanto deste método informa se a autenticação do cliente é
     * bem sucedida.
     *
     * @param passwordHash
     * @return booleano que informa se a autenticação do cliente é bem sucedida.
     * @throws NoSuchAlgorithmException
     */
    public boolean authenticateLogin(String passwordHash) throws NoSuchAlgorithmException
    {
        return (this.passwordHash.equals(passwordHash));
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

    /**
     * Define o total de dinheiro do cliente.
     *
     * @param money
     */
    public void setMoney(double money)
    {
        this.money = money;
    }

    @Override
    public String toString()
    {
        return "Client: " + name + "\nTotal money: " + money;

    }
}
