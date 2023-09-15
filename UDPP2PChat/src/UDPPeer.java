package src;

/**
 * TCPServer: Servidor para conexão TCP
 * Descricao: Responde requisições dos clientes
 */

import java.io.IOException;
import java.net.DatagramSocket;

import src.tools.UDPReceive;
import src.tools.UDPSend;

public class UDPPeer {

    public static void main(String args[]) {
        try {
            DatagramSocket dgramSocket = new DatagramSocket(6666);

            UDPSend sendThread = new UDPSend(dgramSocket);
            UDPReceive receiveThread = new UDPReceive(dgramSocket);

            sendThread.start();
            receiveThread.start();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } //catch
    } //main
} //class
