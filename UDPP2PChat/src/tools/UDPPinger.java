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
import java.nio.charset.StandardCharsets;

public class UDPPinger {

    public DatagramPacket sendPing(String nicknameSender, String messageContent, InetAddress dstIp, int dstPort) throws SocketTimeoutException{
    	DatagramSocket dgramSocket = null;
        try {
        	dgramSocket = new DatagramSocket(1234);
            dgramSocket.setSoTimeout(3000);
            
            PacketData packetData = new PacketData();
            byte[] data = packetData.format("4", nicknameSender, messageContent);
 	        
 	        DatagramPacket request = new DatagramPacket(data, data.length, dstIp, dstPort);
 	        
 	        byte[] bufferResponse = new byte[1024];
           
 	        DatagramPacket response = new DatagramPacket(bufferResponse, bufferResponse.length);
           
 	        dgramSocket.send(request);
            
 	        dgramSocket.receive(response);
 	        
 	        return response;
        } catch (SocketTimeoutException e) {
            throw new SocketTimeoutException(e.getMessage());
        } catch (SocketException e) {
            System.out.println(e.getMessage() + " - Pinger");
        } catch (IOException e) {
            System.out.println(e.getMessage() + " - Pinger");
        } finally {
        	dgramSocket.close();
        }
        
		return null;
    } //main
} //class
