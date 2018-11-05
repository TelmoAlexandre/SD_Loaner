/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountServices;

import AccountManager.AccountManager;
import BlockChain.Block;
import BlockChain.BlockChain;
import java.security.Key;

/**
 *
 * @author Telmo
 */
public abstract class Service extends AccountManager
{
    
    public Service (Key publicKey)
    {
        super(publicKey);
    }
    
    public abstract boolean validate(BlockChain bc) throws Exception;
    
    /**
     * Corre a Block Chain toda e retorna o dinheiro resultante.
     * 
     * @param bc Block Chain a ser procurada.
     * @return O dinheiro do cliente.
     */
    public static double getMyMoney(BlockChain bc){
        
        // Utilizado para saltar o primeiro block da chain, pois este só contem informação do cliente.
        boolean isFirst = true;
        
        // Corre todos os AccountsMovements da blockChain do cliente com exepção do primeiro bloco,
        // porque este contem apenas a informação do cliente.
        for(Block b : bc.chain)
        {
            // Saltar o primeiro block porque este só tem informações sobre o cliente. Não contém transacções.
            if(isFirst){
                isFirst = !isFirst;
                continue;
            }
        }
        
        return 2.0;
    }
}
