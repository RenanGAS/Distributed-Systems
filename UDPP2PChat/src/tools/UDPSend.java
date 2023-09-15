package src.tools;

/**
 * UDPClient: Cliente UDP Descricao: Envia uma msg em um datagrama e recebe a
 * mesma msg do servidor
 */
import java.net.*;
import java.util.Scanner;
import java.io.*;
import javax.swing.JOptionPane;

public class UDPSend extends Thread {
	DatagramSocket dgramSocket;
	
	public UDPSend (DatagramSocket dgramSocket) {
		try {
			this.dgramSocket = dgramSocket;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
    public void run() {
		
		Scanner reader = new Scanner(System.in); // ler mensagens via teclado

        try {
        	System.out.println("IP: ");
        	String dstIP = reader.nextLine();
        	
        	System.out.println("Porta: ");
        	int dstPort = Integer.parseInt(reader.nextLine());
        	
            dgramSocket = new DatagramSocket();
            
            InetAddress serverAddr = InetAddress.getByName(dstIP);
            int serverPort = dstPort; 

            while (true) {
                String msg = reader.nextLine();
                
                if ("EXIT".equals(msg)) {
                	break;
                }

                byte[] m = msg.getBytes(); 
                DatagramPacket request = new DatagramPacket(m, m.length, serverAddr, serverPort);
                dgramSocket.send(request);
            }

            /* libera o socket */
            dgramSocket.close();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } //catch
    } //main		      	
} //class
