package src;

/**
 * TCPServer: Servidor para conexão TCP
 * Descricao: Responde requisições dos clientes
 */

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import src.tools.UDPReceive;
import src.tools.UDPSend;

public class UDPPeer {

    public static void main(String args[]) {
        try {
        	int peerPort = Integer.parseInt(args[0]);
            DatagramSocket dgramSocket = new DatagramSocket(peerPort);
            
            Scanner reader = new Scanner(System.in);

            System.out.println("Nickname (1-64 characters): ");
            String nickName = reader.nextLine();
            
            if (nickName.length() == 0 || nickName.length() > 64) {
            	dgramSocket.close();
            	throw new IOException("NickName's range is 1-64 characters long");
            }
            
            if ("Server".equals(nickName)) {
            	dgramSocket.close();
            	throw new IOException("You can't be a Server");
            }
            
            System.out.println("Do you allow this application to automatically open received URLs? (y/n)");
            String allowUrls = reader.nextLine();
            
            if (allowUrls.isBlank()) {
            	allowUrls = "n";
            }
            
            System.out.println("IP: ");
        	String dstIP = reader.nextLine();
        	InetAddress dstAddr = InetAddress.getByName(dstIP);
        	
        	System.out.println("Porta: ");
        	int dstPort = Integer.parseInt(reader.nextLine());
            
            UDPSend sendThread = new UDPSend(dgramSocket, nickName, dstAddr, dstPort);
            UDPReceive receiveThread = new UDPReceive(dgramSocket, nickName, dstAddr, dstPort, allowUrls);
            
            receiveThread.start();
            sendThread.start();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage() + " - Peer");
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage() + " - Peer");
        } catch (SocketException e) {
            System.out.println(e.getMessage() + " - Peer");
        } catch (IOException e) {
            System.out.println(e.getMessage() + " - Peer");
        } //catch
    } //main
} //class
