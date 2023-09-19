package src.tools;

import java.io.File;
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
		if (!isServerAlive()) {
			throw new IOException("The server is down");
		}
		
		ByteBuffer byteBuffer = readFile(this.fileSize);
		
		try {
			stablishConnection();
		} catch (IndexOutOfBoundsException e) {
			throw new IOException("fileName is too big");
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}
		
		while (byteBuffer.hasRemaining()) {
			byte[] buffer = new byte[1024];
			
			try {
				byteBuffer = byteBuffer.get(buffer, 0, 1024);
			} catch (BufferUnderflowException e) {
				byteBuffer = byteBuffer.get(buffer, 0, byteBuffer.remaining());
			}
	        
	        DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length, this.serverIp, this.serverPort);
			
		    this.dgramSocket.send(dgramPacket);
		}
		
		byte[] commandBuffer = new byte[10];
		System.arraycopy("FINAL", 0, commandBuffer, 0, 10);
		
		byte[] checksumBuffer = generateChecksum(byteBuffer.array());
		
		byte[] bufferRequest = new byte[1024];
		System.arraycopy(commandBuffer, 0, bufferRequest, 0, 10);
		System.arraycopy(checksumBuffer, 0, bufferRequest, 10, 20);
		
		DatagramPacket finalPacket = new DatagramPacket(bufferRequest, bufferRequest.length, this.serverIp, this.serverPort);
		this.dgramSocket.send(finalPacket);
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
		byte[] commandBuffer = new byte[10];
		int commandLenght = commandBuffer.length;
		System.arraycopy("SEND", 0, commandBuffer, 0, commandLenght);
		
		byte[] fileNameBuffer = new byte[100];
		int fileNameLenght = fileNameBuffer.length;
		System.arraycopy(this.fileName.getBytes(), 0, fileNameBuffer, 0, fileNameLenght);
		
		byte[] fileSizeBuffer = ByteBuffer.allocate(4).putInt(this.fileSize).array();
		
		byte[] bufferRequest = new byte[1024];
		System.arraycopy(commandBuffer, 0, bufferRequest, 0, commandLenght);
		System.arraycopy(fileNameBuffer, 0, bufferRequest, commandLenght, fileNameLenght);
		System.arraycopy(fileSizeBuffer, 0, bufferRequest, fileNameLenght, Integer.BYTES);
	        
	    DatagramPacket request = new DatagramPacket(bufferRequest, bufferRequest.length, this.serverIp, this.serverPort);
	    
	    byte[] bufferResponse = new byte[1000];
        
        DatagramPacket response = new DatagramPacket(bufferResponse, bufferResponse.length);
		
	    this.dgramSocket.send(request);
	    
	    this.dgramSocket.receive(response);
	}
	
	ByteBuffer readFile(int bufferLenght) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(bufferLenght);
		FileChannel fChannel = FileChannel.open(this.filePath);
		fChannel.read(byteBuffer);
		
		return byteBuffer;
	}
	
	byte[] generateChecksum(byte[] fileContent) throws IOException {
		try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            
            byte[] messageDigest = md.digest(fileContent);
            
            return messageDigest;
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
	}
}