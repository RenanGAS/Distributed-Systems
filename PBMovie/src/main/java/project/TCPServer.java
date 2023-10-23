package project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import project.threads.ReceiveThread;

/**
 * TCPServer: Servidor para responder requisições do TCPClient fazendo consultas ao servidor MongoDB 
 */
public class TCPServer {
	
	//static Logger logger = LoggerFactory.getLogger(TCPServer.class);

    public static void main(String args[]) {
        try {
            int serverPort = 6666; // porta do servidor

            /* cria um socket e mapeia a porta para aguardar conexao */
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while (true) {
            	//logger.info("Waiting connections...");
                System.out.println("Aguardando conexao...");

                /* aguarda conexoes */
                Socket clientSocket = serverSocket.accept();
                
                //logger.info("Established connection!");
                System.out.println("Conexao estabelecida!");

                /* cria um thread para receber mensagens */
                ReceiveThread receiveThread = new ReceiveThread(clientSocket);

                receiveThread.start();
            } //while

        } catch (IOException e) {
        	//logger.warn("IOException: " + e.getMessage());
            System.out.println("IOException: " + e.getMessage());
        } //catch
    } //main
} //class

