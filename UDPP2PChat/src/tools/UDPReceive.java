package src.tools;

/**
 * UDPServer: Servidor UDP
 * Descricao: Recebe um datagrama de um cliente, imprime o conteudo e retorna o mesmo
 * datagrama ao cliente
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Desktop;
import java.io.*;
import com.vdurmont.emoji.EmojiParser;

public class UDPReceive extends Thread {
	DatagramSocket dgramSocket;
	String nickName;
	InetAddress dstIp; 
	int dstPort;
	String allowUrls;
	
	public UDPReceive (DatagramSocket dgramSocket, String nickName, InetAddress dstIp, int dstPort, String allowUrls) {
		this.nickName = nickName;
		this.dstPort = dstPort;
		this.allowUrls = allowUrls;

		try {
			this.dgramSocket = dgramSocket;
			this.dstIp = dstIp;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " - Receive");
		}
	}
	
	void handleMsgPeer(DatagramPacket dgramPacket) throws IOException {
		try {
			PacketParser packetParser = new PacketParser();
			
			packetParser.parsePacket(dgramPacket);
			String packetType = packetParser.getPacketType();
			String nicknameSender = packetParser.getPacketNickname();
			String packetContent = packetParser.getPacketContent();
			
			switch (packetType) {
				case "1":
					//System.out.println("Normal message received");
					System.out.format("%s: %s\n", nicknameSender, packetContent);
					break;
				case "2":
					printEmojis(nicknameSender,packetContent);
					// Emojis válidos: happy | sad | swag | ok | amem
					break;
				case "3":
					System.out.format("%s: %s\n", nicknameSender, packetContent);
					
					if ("y".equals(this.allowUrls)) {
						openUrl(packetContent);
					}
					break;
				default:
					System.out.println("Unexpected message type: " + packetType);
			}
		} catch (URISyntaxException e) {
			throw new IOException(e.getMessage());
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	} //handleMsgPeer
	
	void printEmojis(String nicknameSender, String message) throws IOException{
		List<String> allMatches = new ArrayList<String>();
		Pattern pattern = Pattern.compile("(happy|sad|swag|ok|amem)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(message);
		
		while (matcher.find()) {
			allMatches.add(matcher.group());
		}
		
		if (allMatches.isEmpty()) {
			throw new IOException("No valid emojis was found");
		}
		
		System.out.print(nicknameSender + ": ");
		
		for(String emoji : allMatches) {
			switch (emoji) {
				case "happy":
					System.out.print(EmojiParser.parseToUnicode(":grinning:"));
					break;
				case "sad":	
					System.out.print(EmojiParser.parseToUnicode(":slightly_frowning:"));
					break;
				case "swag":
					System.out.print(EmojiParser.parseToUnicode(":sunglasses:"));
					break;
				case "ok":
					System.out.print(EmojiParser.parseToUnicode(":thumbsup:"));
					break;
				case "amem":
					System.out.print(EmojiParser.parseToUnicode(":pray:"));
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + emoji);
			}
		}
		
		System.out.println("");
	} //printEmojis
	
	void openUrl(String msg) throws URISyntaxException, IOException {
		List<String> allMatches = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\b(https?):\\/\\/[-a-zA-Z0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\\/%=~_|]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(msg);

		while (matcher.find()) {
			allMatches.add(matcher.group());
		}
		
		if (allMatches.isEmpty()) {
			throw new IOException("No valid URL was found");
		}
		
		for(String url : allMatches) {
			Desktop desktop = java.awt.Desktop.getDesktop();
			URI oURL = new URI(url);
			desktop.browse(oURL);
		}
	} // openUrl
	
	void handleMsgServer(DatagramPacket dgramPacket) throws IOException {		
		try {
			PacketParser packetParser = new PacketParser();
			
			String packetType = packetParser.getPacketTypeAlone(dgramPacket);
			
			InetAddress responsePingIp = dgramPacket.getAddress();
			int responsePingPort = dgramPacket.getPort();
			
			switch (packetType) {
				case "4":
					packetParser.parsePacket(dgramPacket);
					String packetContent = packetParser.getPacketContent();
					
					PacketData packetData = new PacketData();
					byte[] data = packetData.format("1", this.nickName, packetContent);
		 	        
		 	        DatagramPacket response = new DatagramPacket(data, data.length, responsePingIp, responsePingPort);
		 	        
		 			this.dgramSocket.send(response);
		 			break;
				case "5":
					PacketParser packetSendParser = new PacketParser();
					packetSendParser.parseSendPacket(dgramPacket);
					
					String fileName = packetSendParser.getPacketFileName();
					int fileSize = Integer.parseInt(packetSendParser.getPacketFileSize());
					
					ReceiveFile receiveFile = new ReceiveFile(this.dgramSocket, fileName, fileSize, responsePingIp, responsePingPort);
					receiveFile.startReceiveTask();
					break;
				default:
					System.out.println("Unexpected message type: " + packetType);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	} //handleMsgServer
	
	@Override
    public void run() {
        try{
            while(true){
                byte[] buffer = new byte[1024];
                
                DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
                
				this.dgramSocket.receive(dgramPacket);
				
				try {
					if ("Server".equals(this.nickName)) {
						//System.out.println("handleMsgServer\n");
						handleMsgServer(dgramPacket);
						// Aceita mensagens do tipo 4 (Ping) e 5 (SEND)
					} else {
						//System.out.println("handleMsgPeer\n");
						handleMsgPeer(dgramPacket);
						// Aceita mensagens do tipo 1 (Mensagem simples), 2 (Emoji) e 3 (URL)
					}
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
 				
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
