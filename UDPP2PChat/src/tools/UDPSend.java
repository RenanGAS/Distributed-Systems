package src.tools;

/**
 * UDPSend
 */

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.io.*;
import javax.swing.JOptionPane;


public class UDPSend extends Thread {
	DatagramSocket dgramSocket;
	String nickName;
	InetAddress dstIp; 
	int dstPort;
	
	public UDPSend(DatagramSocket dgramSocket, String nickName, InetAddress dstIp, int dstPort) {
		this.nickName = nickName;
		this.dstPort = dstPort;
		try {
			this.dgramSocket = dgramSocket;
			this.dstIp = dstIp;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " - Send");
		}
	} //UDPSend
	
	void handleMsg(String messageType, String messageContent) throws IOException {
		try {
			if (messageContent.length() > 255) {
				throw new IOException("Message content is too long");
			}
			
			switch (messageType) {
				case "1":
					//System.out.println("Normal message type\n");
					sendPacket(messageType, messageContent);
					break;
				case "2":
					//System.out.println("Emoji message type\n");
					sendPacket(messageType, messageContent);
					break;
				case "3":
					//System.out.println("URL message type\n");
					sendPacket(messageType, messageContent);
					break;
				case "4":
					//System.out.println("Ping message type\n");
					UDPPinger pinger = new UDPPinger();
					DatagramPacket response = pinger.sendPing(this.nickName, messageContent, this.dstIp, this.dstPort);
					
					PacketParser packetParser = new PacketParser();
					packetParser.parsePacket(response);
					
					System.out.format("%s: %s\n", packetParser.getPacketNickname(), packetParser.getPacketContent());
					break;
				case "5":
					//System.out.println("Send message type\n");
					Path filePath = Paths.get("./resources/" + messageContent);
					SendFile sendFile = new SendFile(this.nickName, filePath, dgramSocket, this.dstIp, this.dstPort);
					sendFile.startSendTask();
					break;
				default:
					System.out.println("Unexpected message type: " + messageType);
			}
		} catch (InvalidPathException e) {
			System.out.println(e.getMessage());
		} catch (SocketTimeoutException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	} //handleMsg
	
	void sendPacket(String messageType, String parsedContent) throws IOException {
		PacketData packetData = new PacketData();
		byte[] data = packetData.format(messageType, this.nickName, parsedContent);
	        
        DatagramPacket request = new DatagramPacket(data, data.length, this.dstIp, this.dstPort);
        
		this.dgramSocket.send(request);
	} //sendPacket

	@Override
    public void run() {
        Scanner reader = new Scanner(System.in);
        MessageParser msgParser = new MessageParser();
		
		while (true) {
		    String msg = reader.nextLine();
		    
		    if ("EXIT".equals(msg)) {
		    	break;
		    }
		    
		    try {
		    	msgParser.parseMsg(msg);

				String messageType = msgParser.getMessageType();
				String messageContent = msgParser.getMessageContent();
				
				handleMsg(messageType, messageContent);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("DatagramSocket closed - Send");
		dgramSocket.close();
    } //main		      	
} //class
