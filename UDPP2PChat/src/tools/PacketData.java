package src.tools;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class PacketData {
	public byte[] format(String type, String nickname, String content) {
		int sizeBuffer = 9 + nickname.length() + content.length();
		// Tipo + Tamanho apelido + Tamanho mensagem = 9
		
		ByteBuffer dataBuffer = ByteBuffer.allocate(sizeBuffer);
		
		dataBuffer.put(type.getBytes(StandardCharsets.UTF_8));
		dataBuffer.putInt(nickname.length());
		dataBuffer.put(nickname.getBytes(StandardCharsets.UTF_8));
		dataBuffer.putInt(content.length());
		dataBuffer.put(content.getBytes(StandardCharsets.UTF_8));
		
		//System.out.println("Inside format\n");
		//System.out.println(String.valueOf("Array lenght: " + dataBuffer.array().length) + "\n");
		
		return dataBuffer.array();
	}
	
	public byte[] format(String type, String fileName, int fileSize) {
		int sizeBuffer = 9 + fileName.length();
		// Tipo + Tamanho do nome do arquivo + Tamanho do arquivo = 9
		
		ByteBuffer dataBuffer = ByteBuffer.allocate(sizeBuffer);
		
		dataBuffer.put(type.getBytes(StandardCharsets.UTF_8));
		dataBuffer.putInt(fileSize);
		dataBuffer.putInt(fileName.length());
		dataBuffer.put(fileName.getBytes(StandardCharsets.UTF_8));
		
		return dataBuffer.array();
	}
	
	public byte[] format(String type, String checksum) {
		int sizeBuffer = 5 + checksum.length();
		// Tipo + Tamanho do checksum = 5
		
		ByteBuffer dataBuffer = ByteBuffer.allocate(sizeBuffer);
		
		dataBuffer.put(type.getBytes(StandardCharsets.UTF_8));
		dataBuffer.putInt(checksum.length());
		dataBuffer.put(checksum.getBytes(StandardCharsets.UTF_8));
		
		return dataBuffer.array();
	}
}