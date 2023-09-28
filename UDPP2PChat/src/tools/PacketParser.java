package src.tools;

import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PacketParser {
	List<String> parsedPackage = null;
	
	// Ex. de Pacote:
		// Tipo:_|TamApl:_|_|_|_|Apelido:_(1-64)|TamMsg:_|_|_|_|Msg:_(0-255)
	
	public void parsePacket(DatagramPacket dgramPacket) throws IOException {
		List<String> parsedPacketStructure = new ArrayList<>();
		
		String packetType = "";
		String packetNickname = "";
		String packetContent = "";
		
		try {
			ByteBuffer packetBuffer = ByteBuffer.allocate(dgramPacket.getData().length);
			
			//System.out.format("dgramPacket lenght: %d\n", dgramPacket.getData().length);
			
			packetBuffer.put(dgramPacket.getData());
			
			packetBuffer.flip();
			
			byte[] typeBuffer = new byte[1];
			packetBuffer.get(typeBuffer, 0, 1);
			packetType = new String(typeBuffer, StandardCharsets.UTF_8);
			
			//System.out.format("packet type: %s\n", packetType);
			
			int nicknameSize = packetBuffer.getInt(1);
			
			//System.out.format("nickname size: %d\n", nicknameSize);
			
			packetBuffer.position(5);
			
			byte[] nicknameBuffer = new byte[nicknameSize];
			packetBuffer.get(nicknameBuffer, 0, nicknameSize);
			packetNickname = new String(nicknameBuffer, StandardCharsets.UTF_8);
			
			//System.out.format("nickname: %s\n", packetNickname);
			
			int packetContentSize = packetBuffer.getInt();
			
			//System.out.format("content size: %d\n", packetContentSize);
			
			byte[] contentBuffer = new byte[packetContentSize];
			packetBuffer.get(contentBuffer, 0, packetContentSize);
			packetContent = new String(contentBuffer, StandardCharsets.UTF_8);
			
			//System.out.format("content: %s\n", packetContent);
		} catch (IllegalArgumentException  e) {
			throw new IOException(e.getMessage());
		} catch (BufferOverflowException  e) {
			throw new IOException(e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			throw new IOException(e.getMessage());
		}
		
		//System.out.format("packetType: %s, packetNickname: %s, packetContent: %s\n", 
				//packetType, packetNickname, packetContent);
		
		parsedPacketStructure.add(packetType);
		parsedPacketStructure.add(packetNickname);
		parsedPacketStructure.add(packetContent);
		
		this.parsedPackage = parsedPacketStructure;
	} //parsePacket
	
	public void parseSendPacket(DatagramPacket dgramPacket) throws IOException {
		List<String> parsedSendPacketStructure = new ArrayList<>();
		
		String packetType = "";
		int packetFileSize = 0;
		String packetFileName = "";
		
		try {
			ByteBuffer packetBuffer = ByteBuffer.allocate(dgramPacket.getData().length);
			packetBuffer.put(dgramPacket.getData());
			
			packetBuffer.flip();
			
			byte[] typeBuffer = new byte[1];
			packetBuffer.get(typeBuffer, 0, 1);
			packetType = new String(typeBuffer, StandardCharsets.UTF_8);
			
			packetFileSize = packetBuffer.getInt(1);
			int packetFileNameSize = packetBuffer.getInt(5);
			
			packetBuffer.position(9);
			
			byte[] fileNameBuffer = new byte[packetFileNameSize];
			packetBuffer.get(fileNameBuffer, 0, packetFileNameSize);
			packetFileName = new String(fileNameBuffer, StandardCharsets.UTF_8);
		} catch (IllegalArgumentException  e) {
			throw new IOException(e.getMessage());
		} catch (BufferOverflowException  e) {
			throw new IOException(e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			throw new IOException(e.getMessage());
		}
		
		//System.out.format("packetType: %s, packetFileSize: %s, packetFileName: %s\n", 
				//packetType, String.valueOf(packetFileSize), packetFileName);
		
		parsedSendPacketStructure.add(packetType);
		parsedSendPacketStructure.add(String.valueOf(packetFileSize));
		parsedSendPacketStructure.add(packetFileName);
		
		this.parsedPackage = parsedSendPacketStructure;
	}
	
	public void parseCheckSumPacket(DatagramPacket dgramPacket) throws IOException {
		List<String> parsedChecksumPacketStructure = new ArrayList<>();
		
		String packetType = "";
		int packetChecksumSize = 0;
		String packetChecksum = "";
		
		try {
			ByteBuffer packetBuffer = ByteBuffer.allocate(dgramPacket.getData().length);
			packetBuffer.put(dgramPacket.getData());
			
			packetBuffer.flip();
			
			byte[] typeBuffer = new byte[1];
			packetBuffer.get(typeBuffer, 0, 1);
			packetType = new String(typeBuffer, StandardCharsets.UTF_8);
			
			packetChecksumSize = packetBuffer.getInt(1);
			
			packetBuffer.position(5);
			
			byte[] checksumBuffer = new byte[packetChecksumSize];
			packetBuffer.get(checksumBuffer, 0, packetChecksumSize);
			packetChecksum = new String(checksumBuffer, StandardCharsets.UTF_8);
		} catch (IllegalArgumentException  e) {
			throw new IOException(e.getMessage());
		} catch (BufferOverflowException  e) {
			throw new IOException(e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			throw new IOException(e.getMessage());
		}
		
		//System.out.format("packetType: %s, checksum: %s\n", packetType, packetChecksum);
		
		parsedChecksumPacketStructure.add(packetType);
		parsedChecksumPacketStructure.add(packetChecksum);
		
		this.parsedPackage = parsedChecksumPacketStructure;
	}
	
	public String getPacketType() throws IOException {
		if (this.parsedPackage == null) {
			throw new IOException("The packet needs to be parsed first");
		}
		
		return this.parsedPackage.get(0);
	} //getPacketType
	
	public String getPacketNickname() throws IOException {
		if (this.parsedPackage == null) {
			throw new IOException("The packet needs to be parsed first");
		}
		
		return this.parsedPackage.get(1);
	} //getPacketNickname
	
	public String getPacketContent() throws IOException {
		if (this.parsedPackage == null) {
			throw new IOException("The packet needs to be parsed first");
		}

		return this.parsedPackage.get(2);
	} //getPacketContent
	
	public String getPacketFileSize() throws IOException {
		if (this.parsedPackage == null) {
			throw new IOException("The packet needs to be parsed first");
		}
		
		return this.parsedPackage.get(1);
	} //getPacketFileSize
	
	public String getPacketFileName() throws IOException {
		if (this.parsedPackage == null) {
			throw new IOException("The packet needs to be parsed first");
		}
		
		return this.parsedPackage.get(2);
	} //getPacketType
	
	public String getPacketChecksum() throws IOException {
		if (this.parsedPackage == null) {
			throw new IOException("The packet needs to be parsed first");
		}
		
		return this.parsedPackage.get(1);
	} //getPacketType
	
	public String getPacketTypeAlone(DatagramPacket dgramPacket) {
		ByteBuffer packetBuffer = ByteBuffer.allocate(dgramPacket.getData().length);
		packetBuffer.put(dgramPacket.getData());
		
		packetBuffer.flip();
		
		byte[] typeBuffer = new byte[1];
		packetBuffer.get(typeBuffer, 0, 1);
		return new String(typeBuffer, StandardCharsets.UTF_8);
	}
}