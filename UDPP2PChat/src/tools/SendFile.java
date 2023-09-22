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
	
	public SendFile(Path filePath, DatagramSocket dgramSocket, InetAddress serverIp, int serverPort) throws IOException {
		if (filePath != null && dgramSocket != null && serverIp != null && serverPort != 0) {
			this.dgramSocket = dgramSocket;
			this.filePath = filePath;
			this.fileName = filePath.getFileName().toString();
			
			File file = new File(this.filePath.toString());
			this.fileSize = (int)file.length();
			
			this.serverIp = serverIp;
		    this.serverPort = serverPort;
		} else {
			throw new IOException("Invalid file configurations");
		}
	}
	
	public void startSendTask() throws IOException {
		System.out.println("SendFile - startSendTask");
		
		if (!isServerAlive()) {
			throw new IOException("The server is down");
		}
		
		System.out.println("SendFile - System is alive");
		
		ByteBuffer byteBuffer = readFile(this.fileSize);
		
		try {
			stablishConnection();
			System.out.println("Superou stablishConnection");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Exception");
			throw new IOException("fileName is too big");
		} catch (IOException e) {
			System.out.println("Exception");
			throw new IOException(e.getMessage());
		}
		
		System.out.println("Connection stablished");
		
		System.out.println("SendFile - Loop - Sending packets");
		
		byteBuffer = byteBuffer.position(0);
		
		while (byteBuffer.hasRemaining()) {
			System.out.println("Buffer offset: " + Integer.toString(byteBuffer.position()));
			System.out.println("Buffer remaining: " + Integer.toString(byteBuffer.remaining()));
			
			byte[] buffer = new byte[1024];
			
			if (byteBuffer.remaining() >= 1024) {
				byteBuffer = byteBuffer.get(buffer, 0, 1024);
			} else {
				byteBuffer = byteBuffer.get(buffer, 0, byteBuffer.remaining());
			}
	        
	        DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length, this.serverIp, this.serverPort);
			
		    this.dgramSocket.send(dgramPacket);
		}
		System.out.println("All packets sent");
		
		byte[] commandBuffer = new byte[10];
		System.arraycopy("FINAL".getBytes(StandardCharsets.UTF_8), 0, commandBuffer, 0, 5);
		
		String checksumString = generateChecksum(byteBuffer.array());
		
		byte[] bufferRequest = new byte[1024];
		System.arraycopy(commandBuffer, 0, bufferRequest, 0, 10);
		System.arraycopy(checksumString.getBytes(StandardCharsets.UTF_8), 0, bufferRequest, 10, 40);
		
		DatagramPacket finalPacket = new DatagramPacket(bufferRequest, bufferRequest.length, this.serverIp, this.serverPort);
		
		this.dgramSocket.send(finalPacket);
		System.out.println("Final packet sent");
	}
	
	boolean isServerAlive() {
		try {
			UDPPinger pinger = new UDPPinger(this.serverIp, this.serverPort);
			pinger.sendPing();
		} catch (SocketTimeoutException e) {
			return false;
		}
		
		return true;
	}
	
	void stablishConnection() throws IndexOutOfBoundsException, IOException {
		System.out.println("SendFile - stablishConnection");

		byte[] commandBuffer = new byte[10];
		int commandLenght = commandBuffer.length;

		byte[] send = "SEND".getBytes(StandardCharsets.UTF_8);
		System.arraycopy(send, 0, commandBuffer, 0, 4);

		//System.out.format("commandBuffer: %s", new String(commandBuffer, StandardCharsets.UTF_8));
		
		byte[] fileNameBuffer = new byte[100];
		int fileNameLenght = fileNameBuffer.length;
		System.arraycopy(this.fileName.getBytes(StandardCharsets.UTF_8), 0, fileNameBuffer, 0, this.fileName.length());

		//System.out.format("fileNameBuffer: %s", new String(fileNameBuffer, StandardCharsets.UTF_8));
		
		byte[] fileSizeBuffer = ByteBuffer.allocate(4).putInt(this.fileSize).array();

		//ByteBuffer wrapped = ByteBuffer.wrap(fileSizeBuffer);
		//int fileSizeValue = wrapped.getInt();

		//System.out.format("fileSizeValue: %d", fileSizeValue);
		
		byte[] bufferRequest = new byte[1024];
		System.arraycopy(commandBuffer, 0, bufferRequest, 0, commandLenght);
		System.arraycopy(fileNameBuffer, 0, bufferRequest, commandLenght, fileNameLenght);
		System.arraycopy(fileSizeBuffer, 0, bufferRequest, fileNameLenght + commandLenght, Integer.BYTES);

		//System.out.format("bufferRequest: %s", new String(bufferRequest, StandardCharsets.UTF_8));
	        
	    DatagramPacket request = new DatagramPacket(bufferRequest, bufferRequest.length, this.serverIp, this.serverPort);
	    
	    byte[] bufferResponse = new byte[1000];
        
        DatagramPacket response = new DatagramPacket(bufferResponse, bufferResponse.length);
		
        System.out.println("Sending first packet");
        
	    this.dgramSocket.send(request);
	    
	    System.out.println("Depois de mandar");
	    
	    //this.dgramSocket.receive(response);
			
	    System.out.println("First packet received");
	}
	
	ByteBuffer readFile(int bufferLenght) throws IOException {
		System.out.println("SendFile - readFile");
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(bufferLenght);
		
		FileInputStream fin = null;
		
		try {
			fin = new FileInputStream(this.filePath.toAbsolutePath().toFile());
			byteBuffer = byteBuffer.put(fin.readAllBytes()) ;
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			fin.close();
		}
		
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
            
            System.out.println("checksum: " + hashtext);
            
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
	}
}