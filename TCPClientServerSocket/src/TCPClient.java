package aula_tcp;

/**
 * TCPClient: Cliente para conexão TCP
 * Descricao: Envia uma informação ao servidor e recebe respostas. Ao enviar "PARAR", a conexão é finalizada.
 */

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import src.tools.ReceiveThread;
import src.tools.SendThread;

public class TCPClient {
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
	    } catch (UnknownHostException ue){
		System.out.println("UnknownHostException:" + ue.getMessage());
            } catch (EOFException eofe){
		System.out.println("EOFException:" + eofe.getMessage());
            } catch (IOException ioe){
		System.out.println("IOException:" + ioe.getMessage());
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException ioe) {
                    System.out.println("IOException: " + ioe);;
                }
            }
     } //main
} //class
