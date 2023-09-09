package src;

/**
 * TCPServer: Servidor para conexão TCP
 * Descricao: Envia informações aos seus clientes e recebe respostas
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import src.tools.ReceiveThread;

public class TCPServer {

    public static void main(String args[]) {
        try {
            int serverPort = 6666; // porta do servidor

            /* cria um socket e mapeia a porta para aguardar conexao */
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while (true) {
                System.out.println("Aguardando conexao...");

                /* aguarda conexoes */
                Socket clientSocket = serverSocket.accept();

                System.out.println("Conexao estabelecida!");

                /* cria um thread para receber mensagens */
                ReceiveThread receiveThread = new ReceiveThread(clientSocket);

                receiveThread.start();
            } //while

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } //catch
    } //main
} //class
