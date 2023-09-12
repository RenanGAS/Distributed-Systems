package src;

/**
 * TCPClient: Cliente para conexão TCP
 * Descricao: Faz requisições ao servidor. Ao enviar "EXIT", a conexão é finalizada.
 */

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.tools.ReceiveThread;
import src.tools.SendThread;

public class TCPClient {

	static Logger logger = LoggerFactory.getLogger(TCPClient.class);

	public static void main (String args[]) {
		Socket serverSocket = null; // socket do server

		try{
			/* Endereço e porta do servidor */
			int serverPort = 6666;   
			InetAddress serverAddr = InetAddress.getByName("127.0.0.1");

			/* conecta com o servidor */  
			serverSocket = new Socket(serverAddr, serverPort);  

			/* cria um thread para receber mensagens */
			ReceiveThread receiveThread = new ReceiveThread(serverSocket);

			/* cria um thread para enviar mensagens */
			SendThread sendThread = new SendThread(serverSocket);

			receiveThread.start();
			sendThread.start();
			receiveThread.join();
			sendThread.join();
		} catch (UnknownHostException ue){
			logger.warn("UnknownHostException:" + ue.getMessage());
			//System.out.println("UnknownHostException:" + ue.getMessage());
		} catch (EOFException eofe){
			logger.warn("EOFException:" + eofe.getMessage());
			//System.out.println("EOFException:" + eofe.getMessage());
		} catch (IOException ioe){
			logger.warn("IOException:" + ioe.getMessage());
			//System.out.println("IOException:" + ioe.getMessage());
		} catch (InterruptedException ie) {
			logger.warn("InterruptedException:" + ie.getMessage());
			//System.out.println("InterruptedException:" + ie.getMessage());
		} finally {
			try {
				serverSocket.close();
			} catch (IOException ioe) {
				logger.warn("IOException: " + ioe);
				//System.out.println("IOException: " + ioe);
			}
		}
	} //main
} //class
