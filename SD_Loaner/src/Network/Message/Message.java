/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Message;

import java.io.Serializable;

/**
 *
 * @author Telmo
 */
public class Message implements Serializable
{
    
    public static final String TOMINE = "TOMINE";
    public static final String MINEDBLOCK = "MINEDBLOCK";
    
    String type; // CONNECT/DISCONNECT
    Object content; // Conteudo da MessageUDP

    public Message(String type, Object content)
    {
        this.type = type;
        this.content = content;
    }
    
    /**
     * Retorna o tipo de comando presente na mensagem.
     *
     * @return Comando da mensagem
     */
    public String getType()
    {
        return type;
    }

    /**
     * Define o tipo de comando da mensagem.
     *
     * @param comand Comando a ser definido.
     */
    public void setType(String comand)
    {
        this.type = comand;
    }

    /**
     * Retorna o conteudo da mensagem.
     *
     * @return Conteudo da mensagem.
     */
    public Object getContent()
    {
        return content;
    }

    /**
     * Define o conteudo da mensagem.
     *
     * @param content Conteudo da mensagem.
     */
    public void setContent(Object content)
    {
        this.content = content;
    }
    
    /**
     * String com os conteudos da MessageUDP.
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        return type + " " + content.toString();
    }
}
