package src.tools;

/**
 * ReceiveThread: Thread responsável pelo recebimento de mensagens
 * Descricao: Recebe informações
 */

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ReceiveThread extends Thread {

    DataInputStream input;
    Socket socket;

    public ReceiveThread(Socket socket) {
        try {
            this.socket = socket;
            this.input = new DataInputStream(socket.getInputStream());
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        } //catch
    } //construtor

    /* metodo executado ao iniciar a thread - start() */
    @Override
    public void run() {
        try {
            String buffer = "";
            while (true) {
                buffer = this.input.readUTF();   /* aguarda o envio de dados */

                System.out.println(buffer);

                if (buffer.equals("PARAR")) break;
            }
        } catch (EOFException eofe) {
            System.out.println("EOFException: " + eofe.getMessage());
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        } finally {
            try {
                this.input.close();
                this.socket.close();
            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe);
            }
        }
        System.out.println("ReceiveThread finished.");
    } //run
} //class