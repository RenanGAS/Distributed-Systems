package src.tools;

/**
 * UDPServer: Servidor UDP
 * Descricao: Recebe um datagrama de um cliente, imprime o conteudo e retorna o mesmo
 * datagrama ao cliente
 */

import java.net.*;
import java.io.*;

public class UDPReceive extends Thread {
	DatagramSocket dgramSocket;
	
	public UDPReceive (DatagramSocket dgramSocket) {
		try {
			this.dgramSocket = dgramSocket;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
    public void run() {
        try{
            while(true){
                byte[] buffer = new byte[1000];
                
                DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
                this.dgramSocket.receive(dgramPacket);

                System.out.println(new String(dgramPacket.getData(), 0, dgramPacket.getLength()));
            } //while
        }catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            this.dgramSocket.close();
        } //finally
    } //main
}//class
