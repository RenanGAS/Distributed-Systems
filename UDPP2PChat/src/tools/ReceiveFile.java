package src.tools;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
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
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ReceiveFile {
	DatagramSocket dgramSocket;
	InetAddress clientIp;
	int clientPort;
	String fileName;
	int fileSize;
	
	public ReceiveFile(DatagramSocket dgramSocket, String fileName, int fileSize, InetAddress clientIp, int clientPort) {
		this.dgramSocket = dgramSocket;
		this.clientIp = clientIp;
		System.out.format("ReceiveFile - clientIp: %s", this.clientIp.toString());
		
		this.clientPort = clientPort;
		System.out.format("ReceiveFile - clientPort: %d", this.clientPort);
		
		this.fileName = fileName;

		System.out.format("ReceiveFile - fileName: %s", this.fileName);

		this.fileSize = fileSize;
		
		System.out.format("ReceiveFile - fileSize: %d", this.fileSize);
	}
	
	public void startReceiveTask() throws IOException {
		System.out.println("Enter startReceiveTask");
		
//		byte[] bufferRequest = "Server ready".getBytes(StandardCharsets.UTF_8);
//	        
//        DatagramPacket request = new DatagramPacket(bufferRequest, bufferRequest.length, this.clientIp, this.clientPort);
//        
//        System.out.println("Packet 'server ready' sent");
//		this.dgramSocket.send(request);
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(this.fileSize);
		
		byte[] checksum = new byte[40];
		
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
				byteBuffer.put(dgramPacket.getData(), 0, dgramLength);
			} else {
				byteBuffer.put(dgramPacket.getData(), 0, bufferRemaining);
			}
		}
		
		System.out.println("ReceiveFile - Loop finished - File sent");
		
		byte[] buffer = new byte[1024];
        
        DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
        
        System.out.println("Waiting checksum packet");
		this.dgramSocket.receive(dgramPacket);
		System.out.println("Checksum packet arrived");
		
		PacketParser packetChecksumParser = new PacketParser();
		packetChecksumParser.parseCheckSumPacket(dgramPacket);
		
		String checksumClient = "";
		
		if ("6".equals(packetChecksumParser.getPacketType())) {
			checksumClient = packetChecksumParser.getPacketChecksum();
			System.out.println("Receiving checksum packet");
		}
		
		String checksumServer = generateChecksum(byteBuffer.array());
		System.out.println("Current checksum calculated");
		
		System.out.println(checksumServer + " == " + checksumClient);
		
		if (checksumServer.equals(checksumClient)) {
			System.out.println("Successful Upload");
		} else {
			System.out.println("Fail Upload");
		}
		
		Path filePath = null;
		
		try {
			filePath = Paths.get("Uploads");
			filePath = filePath.resolve(this.fileName);
			
			System.out.println("filePath: " + filePath.toString());
			
			FileOutputStream fout = null;
				
			try {
				fout = new FileOutputStream(filePath.toAbsolutePath().toFile());
				fout.write(byteBuffer.array());
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally {
				fout.flush();
				fout.close();
			}
		} catch (InvalidPathException e) {
			System.out.println(e.getMessage());
		}
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