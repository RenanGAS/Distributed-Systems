package src.tools;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ReceiveFile {
	DatagramSocket dgramSocket;
	InetAddress clientIp;
	int clientPort;
	String fileName;
	int fileSize;
	
	public ReceiveFile(DatagramSocket dgramSocket, DatagramPacket dgramPacket) {
		this.dgramSocket = dgramSocket;
		this.clientIp = dgramPacket.getAddress();
		this.clientPort = dgramPacket.getPort();
		System.arraycopy(dgramPacket.getData(), 0, this.fileName, 10, 100); // erro
		System.arraycopy(this.fileSize, 0, dgramPacket.getData(), 100, 4); // erro
	}
	
	public void startReceiveTask() throws IOException {
		byte[] bufferRequest = "Server ready".getBytes();
	        
        DatagramPacket request = new DatagramPacket(bufferRequest, bufferRequest.length, this.clientIp, this.clientPort);
        
		this.dgramSocket.send(request);
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(this.fileSize);
		byte[] checksum = new byte[20];
		
		while (byteBuffer.hasRemaining()) {
			byte[] buffer = new byte[1024];
            
            DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
            
			this.dgramSocket.receive(dgramPacket);
			
			byteBuffer = byteBuffer.put(dgramPacket.getData(), 0, dgramPacket.getData().length);
		}
		
		byte[] buffer = new byte[1024];
        
        DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
        
		this.dgramSocket.receive(dgramPacket);
		
		byte[] commandBuffer = new byte[10];
		System.arraycopy(dgramPacket.getData(), 0, commandBuffer, 0, 10);
		
		if ("FINAL".equals(commandBuffer.toString())) {
			System.arraycopy(dgramPacket.getData(), 10, checksum, 0, 20);
		}
		
		byte[] checksumServer = generateChecksum(byteBuffer.array());
		
		if (checksumServer.equals(checksum)) {
			System.out.println("Successful Upload");
		} else {
			System.out.println("Fail Upload");
		}
		
		Path filePath = Paths.get("./Uploads/" + this.fileName);
		
		FileChannel fChannel = FileChannel.open(filePath);
		fChannel.write(byteBuffer);
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