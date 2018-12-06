//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package Utilities;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 *
 * @author antoniomanso
 */
public class NetworkTime {

    public static final String DEFAULT_HOST = "time.nist.gov";

    public static long getTimeUDP(String host) throws Exception {
        //Time Protocol  - https://www.rfc-editor.org/rfc/rfc868.txt        
        DatagramSocket timeServer = new DatagramSocket();
        byte[] data = new byte[4]; // 4 bytes for unsigend integer
        //U: Send an empty datagram to port 37.
        DatagramPacket packet = new DatagramPacket(data, data.length,
                InetAddress.getByName(host), 37);
        //S: Receive the empty datagram.
        timeServer.send(packet);
        //S: Send a datagram containing the time as a 32 bit binary number.
        //U: Receive the time datagram.
        timeServer.receive(packet);
        //converts 32 bits unsigned int (4 bytes) to long 
        long time = ByteBuffer.wrap(data).getInt() & 0xffffffffL;
        //2208988800L = seconds between 1900-1970 
        return (time - 2208988800L) * 1000L; // time in Unix era
    }

    public static long getTimeTCP(String host) throws IOException {
        //U: Connect to port 37.
        Socket timeServer = new Socket(host, 37);
        DataInputStream input = new DataInputStream(timeServer.getInputStream());
        //S: Send the time as a 32 bit binary number.
        //U: Receive the time         
        long time = (input.readInt() & 0xffffffffL);//convert to usigned int 
        //U: Close the connection.        
        timeServer.close();
        //2208988800L = seconds between 1900-1970 
        return (time - 2208988800L) * 1000L; // miliseconds in Unix era
    }

    public static long getTime() {
        try {
            return getTimeTCP(DEFAULT_HOST);
        } catch (Exception e) {
            try {
                return getTimeUDP(DEFAULT_HOST);
            } catch (Exception ex) {
                return 0;
            }
        }
    }   
}
