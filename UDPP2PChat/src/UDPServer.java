package src;

/**
 * TCPServer: Servidor para conexão TCP
 * Descricao: Responde requisições dos clientes
 */

import java.net.DatagramSocket;
import java.net.SocketException;

import src.tools.UDPReceive;
import src.tools.UDPSend;

public class UDPServer {

    public static void main(String args[]) {
        try {
        	int peerPort = Integer.parseInt(args[0]);
        	DatagramSocket dgramSocket = new DatagramSocket(peerPort);
            
            String nickName = "Server";
            
            UDPReceive receiveThread = new UDPReceive(dgramSocket, nickName, null, 0);
            
            receiveThread.start();
        } catch (SocketException e) {
            System.out.println(e.getMessage() + " - Server");
        }
    } //main
} //class
