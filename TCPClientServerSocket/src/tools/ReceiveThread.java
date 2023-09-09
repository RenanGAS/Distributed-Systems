package src.tools;

/**
 * ReceiveThread: Thread responsável pelo recebimento de mensagens
 * Descricao: Recebe informações
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ReceiveThread extends Thread {
    DataInputStream input;

    DataOutputStream output;
    
    Socket socket;

    public ReceiveThread(Socket socket) {
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
        try {
            String buffer = "";
            while (true) {
                buffer = this.input.readUTF();   /* aguarda o envio de dados */

                System.out.println(buffer);
                
                if (this.socket.getLocalPort() == 6666) {
					String response;
					try {
						response = FSController.handleCommand(buffer);
						this.output.writeUTF(response);
					} catch (IOException ioe) {
						this.output.writeUTF("IOException: " + ioe.getMessage());
					}
				} else {
					if (buffer.equals("PARAR")) break;
				}
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
        System.out.println("ReceiveThread finished.");
    } //run
} //class