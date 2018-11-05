/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccountServices;

import BlockChain.BlockChain;
import java.security.Key;

/**
 *
 * @author Telmo
 */
public class Loan extends Service
{
    public Loan(Key publicKey)
    {
        super(publicKey);
    }
    
    @Override
    public boolean validate(BlockChain bc) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
