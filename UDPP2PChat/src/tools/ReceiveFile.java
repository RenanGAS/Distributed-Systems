package src.tools;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
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
		System.out.format("ReceiveFile - clientIp: %s", this.clientIp.toString());
		
		this.clientPort = dgramPacket.getPort();
		System.out.format("ReceiveFile - clientPort: %d", this.clientPort);
		
		byte[] fileNameBuffer = new byte[100];
		System.arraycopy(dgramPacket.getData(), 10, fileNameBuffer, 0, 100);
		this.fileName = new String(fileNameBuffer, StandardCharsets.UTF_8);
		this.fileName = this.fileName.replace("\0", "");

		System.out.format("ReceiveFile - fileName: %s", this.fileName);

		byte[] fileSizeBuffer = new byte[4];
		System.arraycopy(dgramPacket.getData(), 110, fileSizeBuffer, 0, 4);
		ByteBuffer wrapped = ByteBuffer.wrap(fileSizeBuffer);
		this.fileSize = wrapped.getInt();
		
		System.out.format("ReceiveFile - fileSize: %d", this.fileSize);
	}
	
	public void startReceiveTask() throws IOException {
		System.out.println("Enter startReceiveTask");
		
		byte[] bufferRequest = "Server ready".getBytes(StandardCharsets.UTF_8);
	        
        DatagramPacket request = new DatagramPacket(bufferRequest, bufferRequest.length, this.clientIp, this.clientPort);
        
        System.out.println("Packet 'server ready' sent");
		this.dgramSocket.send(request);
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(this.fileSize);
		
		byte[] checksum = new byte[20];
		
		System.out.println("ReceiveFile - Loop: Sending file content");
		while (byteBuffer.hasRemaining()) {
			System.out.println("Buffer offset: " + Integer.toString(byteBuffer.position()));
			System.out.println("Buffer remaining: " + Integer.toString(byteBuffer.remaining()));
			
			byte[] buffer = new byte[1024];
            
            DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
            
			this.dgramSocket.receive(dgramPacket);
			
			System.out.println("dgram length: " + Integer.toString(dgramPacket.getData().length));
			
			int dgramLength = dgramPacket.getData().length;
			int bufferRemaining = byteBuffer.remaining();
			
			if (bufferRemaining >= dgramLength) {
				byteBuffer = byteBuffer.put(dgramPacket.getData(), 0, dgramLength);
			} else {
				byteBuffer = byteBuffer.put(dgramPacket.getData(), 0, bufferRemaining);
			}
		}
		
		System.out.println("ReceiveFile - Loop finished - File sent");
		
		byte[] buffer = new byte[1024];
        
        DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
        
        System.out.println("Waiting checksum packet");
		this.dgramSocket.receive(dgramPacket);
		System.out.println("Checksum packet arrived");
		
		byte[] commandBuffer = new byte[10];
		System.arraycopy(dgramPacket.getData(), 0, commandBuffer, 0, 10);
		
		String commandString = new String(commandBuffer, StandardCharsets.UTF_8);
		
		System.out.println("commandString: " + commandString);
		
		if ("FINAL".equals(commandString)) {
			System.arraycopy(dgramPacket.getData(), 10, checksum, 0, 20);
			System.out.println("Receiving checksum packet");
		}
		
		byte[] checksumServer = generateChecksum(byteBuffer.array());
		System.out.println("Current checksum calculated");
		
		if (checksumServer.equals(checksum)) {
			System.out.println("Successful Upload");
		} else {
			System.out.println("Fail Upload");
		}
		
		Path filePath = null;
		
		try {
			filePath = Paths.get("Uploads");
			filePath = filePath.resolve(this.fileName);
			
			System.out.println("filePath: " + filePath.toString());
			
			try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
				byte[] conteudo = byteBuffer.array();
				String conteudoString = new String(conteudo, StandardCharsets.UTF_8);
				
			    writer.write(conteudoString.toCharArray(), 0, conteudoString.length());
			} catch (IOException ioe) {
				throw new IOException(ioe.getMessage());
			}
		} catch (InvalidPathException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
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