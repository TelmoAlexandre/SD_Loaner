//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2018   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package Network;

import java.awt.Color;
import java.util.EventListener;
import java.util.List;

/**
 * Created on 15/nov/2018, 19:38:20
 *
 * @author zulu
 */
public interface NodeEventListener extends EventListener {

    public void onConnectLink(Object obj);

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::         N O T I F Y     L I S T E N E R S         ::::::::::::::::: 
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static void notifyConnect(List<NodeEventListener> listeners, Object param) {
        for (NodeEventListener listener : listeners) {
            listener.onConnectLink(param);
        }
    }


}
