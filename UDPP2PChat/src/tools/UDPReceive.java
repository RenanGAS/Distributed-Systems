package src.tools;

/**
 * UDPServer: Servidor UDP
 * Descricao: Recebe um datagrama de um cliente, imprime o conteudo e retorna o mesmo
 * datagrama ao cliente
 */

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Desktop;
import java.io.*;

public class UDPReceive extends Thread {
	DatagramSocket dgramSocket;
	String nickName;
	InetAddress dstIp; 
	int dstPort;
	
	public UDPReceive (DatagramSocket dgramSocket, String nickName, InetAddress dstIp, int dstPort) {
		this.nickName = nickName;
		this.dstPort = dstPort;
		try {
			this.dgramSocket = dgramSocket;
			this.dstIp = dstIp;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " - Receive");
		}
	}
	
	void handleMsg(DatagramPacket dgramPacket) {
		String msg = new String(dgramPacket.getData(), 0, dgramPacket.getLength());
		InetAddress responsePingIp = dgramPacket.getAddress();
		int responsePingPort = dgramPacket.getPort();
		
		try {
			if (msg.indexOf("pI4g") != -1) {
				byte[] bufferResponse = msg.getBytes();
	 	        
	 	        DatagramPacket response = new DatagramPacket(bufferResponse, bufferResponse.length, responsePingIp, responsePingPort);
	 	        
	 			this.dgramSocket.send(response);
			} else if (msg.indexOf("SEND") != -1) {
				if (!"Server".equals(this.nickName)) {
					byte[] bufferResponse = "I'm not a UDP Server".getBytes();
		 	        
		 	        DatagramPacket response = new DatagramPacket(bufferResponse, bufferResponse.length, responsePingIp, responsePingPort);
		 	        
		 			this.dgramSocket.send(response);
				} else {
					ReceiveFile receiveFile = new ReceiveFile(this.dgramSocket, dgramPacket);
					receiveFile.startReceiveTask();
				}
			} else if (containsUrl(msg)) {
				if (!"Server".equals(this.nickName)) {
					openUrl(msg);
				}
			} else {
				if (!"Server".equals(this.nickName)) {
					System.out.println(msg);
				}
			}
		} catch (URISyntaxException e) {
			System.out.println(e.getMessage()+ " - Receive");
		} catch (IOException e) {
			System.out.println(e.getMessage()+ " - Receive");
		}
	} //handleMsg
	
	boolean containsUrl(String msg) {
		return msg.indexOf("http") != -1;
	} //containsUrl
	
	void openUrl(String msg) throws URISyntaxException, IOException {
		List<String> allMatches = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\b(https?):\\/\\/[-a-zA-Z0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\\/%=~_|]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(msg);

		while (matcher.find()) {
			allMatches.add(matcher.group());
		}
		
		for(String url : allMatches) {
			Desktop desktop = java.awt.Desktop.getDesktop();
			URI oURL = new URI(url);
			desktop.browse(oURL);
		}
	} // openUrl
	
	@Override
    public void run() {
        try{
            while(true){
                byte[] buffer = new byte[1024];
                
                DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
                
				this.dgramSocket.receive(dgramPacket);
				
				handleMsg(dgramPacket);
            } //while
        }catch (SocketException e){
            System.out.println(e.getMessage()+ " - Receive");
        }catch (IOException e) {
            System.out.println(e.getMessage()+ " - Receive");
        } finally {
        	System.out.println("DatagramSocket closed - Receive");
            this.dgramSocket.close();
        } //finally
    } //main
}//class
