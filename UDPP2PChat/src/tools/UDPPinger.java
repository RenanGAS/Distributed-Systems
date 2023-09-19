package src.tools;

/**
 * TCPServer: Servidor para conexão TCP
 * Descricao: Responde requisições dos clientes
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class UDPPinger {
	InetAddress dstIp;
	int dstPort;
	
	public UDPPinger(InetAddress dstIp, int dstPort) {
		this.dstIp = dstIp;
		this.dstPort = dstPort;
	}

    public void sendPing() throws SocketTimeoutException{
    	DatagramSocket dgramSocket = null;
        try {
        	dgramSocket = new DatagramSocket(1234);
            dgramSocket.setSoTimeout(3000);
            
            byte[] bufferRequest = "pI4g".getBytes();
 	        
 	        DatagramPacket request = new DatagramPacket(bufferRequest, bufferRequest.length, this.dstIp, this.dstPort);
 	        
 	        byte[] bufferResponse = new byte[1000];
           
 	        DatagramPacket response = new DatagramPacket(bufferResponse, bufferResponse.length);
           
 	        dgramSocket.send(request);
            
 	        dgramSocket.receive(response);
        } catch (SocketTimeoutException e) {
        	dgramSocket.close();
            throw new SocketTimeoutException("Time out");
        } catch (SocketException e) {
            System.out.println(e.getMessage() + " - Pinger");
        } catch (IOException e) {
            System.out.println(e.getMessage() + " - Pinger");
        } //catc

        dgramSocket.close();
    } //main
} //class
