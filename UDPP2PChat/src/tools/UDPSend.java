package src.tools;

/**
 * UDPClient: Cliente UDP Descricao: Envia uma msg em um datagrama e recebe a
 * mesma msg do servidor
 */
import java.net.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import javax.swing.JOptionPane;

public class UDPSend extends Thread {
	DatagramSocket dgramSocket;
	String nickName;
	InetAddress dstIp; 
	int dstPort;
	
	public UDPSend (DatagramSocket dgramSocket, String nickName, InetAddress dstIp, int dstPort) {
		this.nickName = nickName;
		this.dstPort = dstPort;
		try {
			this.dgramSocket = dgramSocket;
			this.dstIp = dstIp;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " - Send");
		}
	}
	
	void handleMsg(String msg) {
		List<String> parsedMsg = new ArrayList<>();
		
		parsedMsg = parseMsg(msg);

		String firstToken = parsedMsg.get(0);

        try {
        	if ("ECHO".equals(firstToken)) {
				try {
					UDPPinger pinger = new UDPPinger(this.dstIp, this.dstPort);
					pinger.sendPing();
					System.out.println(parsedMsg.get(1));
				} catch (SocketTimeoutException e) {
					System.out.println(e.getMessage());
				}
			} else if ("SEND".equals(firstToken)) {
				try {
					Path filePath = Paths.get("./resources/" + parsedMsg.get(1));
					SendFile sendFile = new SendFile(filePath, dgramSocket, this.dstIp, this.dstPort);
					sendFile.startSendTask();
					System.out.println("Saiu do SendFile");
				} catch (InvalidPathException e) {
					System.out.println(e.getMessage());
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			} else {
	 			msg = msg.concat(" - " + this.nickName);
	 			
	 			byte[] bufferRequest = msg.getBytes();
	 	        
	 	        DatagramPacket request = new DatagramPacket(bufferRequest, bufferRequest.length, this.dstIp, this.dstPort);
	 	        
	 			this.dgramSocket.send(request);
	 		}
        } catch (IOException e) {
			System.out.println(e.getMessage()+ " - Send");
		}			
	} //handleEcho
	
	List<String> parseMsg(String input) {
		Pattern pattern = Pattern.compile("($|\\s+)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			String[] sequence = pattern.split(input);

			if (sequence.length > 0) {
				List<String> listTokens = Arrays.asList(sequence);
				return listTokens;
			}
		}
		
		return null;
	} //parseMsg

	@Override
    public void run() {
        Scanner reader = new Scanner(System.in); // ler mensagens via teclado
		
		while (true) {
		    String msg = reader.nextLine();
		    
		    if ("EXIT".equals(msg)) {
		    	break;
		    }
		    
		    handleMsg(msg);
		}

		System.out.println("DatagramSocket closed - Send");
		dgramSocket.close();
    } //main		      	
} //class
