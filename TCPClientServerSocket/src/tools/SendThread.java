package src.tools;

/**
 * SendThread: Thread responsável pelo envio de mensagens
 * Descrição: Envia informações
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SendThread extends Thread {
    DataInputStream input;

    DataOutputStream output;

    Socket socket;

    public SendThread(Socket socket) {
        try {
            this.socket = socket;
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        } //catch
    } //construtor

    /* metodo executado ao iniciar a thread - start() */
    @Override
    public void run() {
        Scanner reader = new Scanner(System.in); // ler mensagens via teclado

        try {
            String buffer = "";
            while (true) {
                buffer = reader.nextLine(); // lê mensagem via teclado
                
                this.output.writeUTF(buffer);      	// envia a mensagem para o servidor

                if (buffer.equals("PARAR")) break;
            }
        } catch (EOFException eofe) {
            System.out.println("EOFException: " + eofe.getMessage());
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        } finally {
            try {
                this.input.close();
                this.output.close();
                this.socket.close();
            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe);
            }
        }
        System.out.println("SendThread finished.");
    } //run
} //class