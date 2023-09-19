package src;

/**
 * TCPServer: Servidor para conexão TCP
 * Descricao: Responde requisições dos clientes
 */

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

import src.tools.UDPReceive;
import src.tools.UDPSend;

public class UDPPeer {

    public static void main(String args[]) {
        try {
        	int peerPort = Integer.parseInt(args[1]);
            DatagramSocket dgramSocket = new DatagramSocket(peerPort);
            
            Scanner reader = new Scanner(System.in); // ler mensagens via teclado
            
            System.out.println("IP: ");
        	String dstIP = reader.nextLine();
        	
        	System.out.println("Porta: ");
        	int dstPort = Integer.parseInt(reader.nextLine());
            
            InetAddress dstAddr = InetAddress.getByName(dstIP);
            
            String nickName;
            
            if ("Server".equals(args[0])) {
            	dgramSocket.close();
            	throw new IOException("You can't be a Server");
            } else {
            	nickName = args[0];
            }
            
            UDPSend sendThread = new UDPSend(dgramSocket, nickName, dstAddr, dstPort);
            UDPReceive receiveThread = new UDPReceive(dgramSocket, nickName, dstAddr, dstPort);
            
            receiveThread.start();
            sendThread.start();
        } catch (SocketException e) {
            System.out.println(e.getMessage() + " - Peer");
        } catch (IOException e) {
            System.out.println(e.getMessage() + " - Peer");
        } //catch
    } //main
} //class
