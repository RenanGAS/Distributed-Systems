package project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import project.threads.ReceiveThreadServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TCPServer: Servidor para responder requisições do TCPClient realizando operações sobre o servidor MongoDB 
 */
public class TCPServer {
	
	static Logger logger = LoggerFactory.getLogger(TCPServer.class);

    public static void main(String args[]) throws IOException {
        int serverPort = 6666; 
        ServerSocket serverSocket = new ServerSocket(serverPort);

        try {
            while (true) {
            	logger.info("Waiting connections...");
                //System.out.println("Waiting connections...");

                Socket clientSocket = serverSocket.accept();
                
                logger.info("Established connection!");
                //System.out.println("Established connection!");

                ReceiveThreadServer receiveThread = new ReceiveThreadServer(clientSocket);

                receiveThread.start();
            } 
        } catch (IOException e) {
        	logger.warn("IOException: " + e.getMessage());
            //System.out.println("\nIOException: " + e.getMessage() + "\n");
        } finally {
            serverSocket.close();
        }
    } 
} 

