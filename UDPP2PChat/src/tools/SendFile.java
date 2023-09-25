package src.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class SendFile {
	Path filePath;
	DatagramSocket dgramSocket;
	String fileName;
	int fileSize; // bytes
	InetAddress serverIp;
	int serverPort;
	String nicknameSender;
	
	public SendFile(String nicknameSender, Path filePath, DatagramSocket dgramSocket, InetAddress serverIp, int serverPort) throws IOException {
		if (filePath != null && dgramSocket != null && serverIp != null && serverPort != 0) {
			this.dgramSocket = dgramSocket;
			this.filePath = filePath;
			this.fileName = filePath.getFileName().toString();
			this.nicknameSender = nicknameSender;
			
			File file = new File(this.filePath.toString());
			this.fileSize = (int)file.length();
			
			this.serverIp = serverIp;
		    this.serverPort = serverPort;
		} else {
			throw new IOException("Invalid file configurations");
		}
	}
	
	public void startSendTask() throws IOException {
		//System.out.println("SendFile - startSendTask");
		
		if (!isServerAlive()) {
			throw new IOException("The server is down");
		}
		
		//System.out.println("SendFile - System is alive");
		
		ByteBuffer byteBuffer = readFile(this.fileSize);
		
		try {
			stablishConnection();
			//System.out.println("Superou stablishConnection");
		} catch (IndexOutOfBoundsException e) {
			//System.out.println(e.getMessage());
			throw new IOException("fileName is too big");
		} catch (IOException e) {
			//System.out.println(e.getMessage());
			throw new IOException(e.getMessage());
		}
		
		//System.out.println("Connection stablished");
		
		//System.out.println("SendFile - Loop - Sending packets");
		
		byteBuffer = byteBuffer.position(0);
		
		while (byteBuffer.hasRemaining()) {
			//System.out.println("Buffer offset: " + Integer.toString(byteBuffer.position()));
			//System.out.println("Buffer remaining: " + Integer.toString(byteBuffer.remaining()));
			
			byte[] buffer = new byte[1024];
			
			if (byteBuffer.remaining() >= 1024) {
				byteBuffer = byteBuffer.get(buffer, 0, 1024);
			} else {
				byteBuffer = byteBuffer.get(buffer, 0, byteBuffer.remaining());
			}
	        
	        DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length, this.serverIp, this.serverPort);
			
		    this.dgramSocket.send(dgramPacket);
		}
		//System.out.println("All packets sent");
		
		String checksumString = generateChecksum(byteBuffer.array());
		
		PacketData packetData = new PacketData();
		byte[] data = packetData.format("6", checksumString);
		
		DatagramPacket finalPacket = new DatagramPacket(data, data.length, this.serverIp, this.serverPort);
		
		this.dgramSocket.send(finalPacket);
		//System.out.println("Final packet sent");
	}
	
	boolean isServerAlive() {
		try {
			UDPPinger pinger = new UDPPinger();
			DatagramPacket response = pinger.sendPing(this.nicknameSender, "The server is alive", this.serverIp, this.serverPort);
			
			PacketParser packetParser = new PacketParser();
			
			packetParser.parsePacket(response);
			//System.out.format("%s: %s", packetParser.getPacketNickname(), packetParser.getPacketContent());
		} catch (SocketTimeoutException e) {
			return false;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	void stablishConnection() throws IndexOutOfBoundsException, IOException {
		//System.out.println("SendFile - stablishConnection");
		
		PacketData packetData = new PacketData();
		
		byte[] data = packetData.format("5", this.fileName, this.fileSize);

		//System.out.format("data: %s", new String(data, StandardCharsets.UTF_8));
	        
	    DatagramPacket request = new DatagramPacket(data, data.length, this.serverIp, this.serverPort);
	    
	    byte[] bufferResponse = new byte[1024];
        
        DatagramPacket response = new DatagramPacket(bufferResponse, bufferResponse.length);
		
        //System.out.println("Sending first packet");
        
	    this.dgramSocket.send(request);
	    
	    //System.out.println("Depois de mandar");
	    
	    //this.dgramSocket.receive(response);
			
	    //System.out.println("First packet received");
	}
	
	ByteBuffer readFile(int bufferLenght) throws IOException {
		//System.out.println("SendFile - readFile");
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(bufferLenght);
		
		FileInputStream fin = null;
		
		//try {
		fin = new FileInputStream(this.filePath.toAbsolutePath().toFile());
		byteBuffer = byteBuffer.put(fin.readAllBytes()) ;
		//} catch (FileNotFoundException e) {
			//System.out.println(e.getMessage());
		//} catch (IOException e) {
			//System.out.println(e.getMessage());
		//} finally {
			//try {
		fin.close();
			//} catch (IOException e) {
				//System.out.println(e.getMessage());
			//}
		//}
		
		return byteBuffer;
	}
	
	String generateChecksum(byte[] fileContent) throws IOException {
		try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            
            byte[] messageDigest = md.digest(fileContent);
            
            BigInteger no = new BigInteger(1, messageDigest);
            
            String hashtext = no.toString(16);
            
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            
            //System.out.println("checksum: " + hashtext);
            
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
	}
}