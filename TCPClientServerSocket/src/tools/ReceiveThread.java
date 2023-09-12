package src.tools;

/**
 * ReceiveThread: Thread responsável por receber comandos e enviar/receber informações
 * Descricao: Recebe comandos e enviar/receber informações
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiveThread extends Thread {
    DataInputStream input;

    DataOutputStream output;
    
    Socket socket;
    
    Logger logger = LoggerFactory.getLogger(ReceiveThread.class);

    public ReceiveThread(Socket socket) {
        try {
            this.socket = socket;
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
        	logger.warn("IOException: " + ioe.getMessage());
            //System.out.println("IOException: " + ioe.getMessage());
        } //catch
    } //construtor

    /* metodo executado ao iniciar a thread - start() */
    @Override
    public void run() {
        try {
            String buffer = "";
            FSController fsController = new FSController();
            while (true) {
                buffer = this.input.readUTF();   /* aguarda o envio de dados */

                System.out.println(buffer);
                
                if (this.socket.getLocalPort() == 6666) {
					String response;
					try {
						response = fsController.handleCommand(buffer);
						this.output.writeUTF(response);
					} catch (IOException ioe) {
						logger.warn("IOException: " + ioe.getMessage());
						this.output.writeUTF("ERROR: " + ioe.getMessage());
					}
				}
            }
        } catch (EOFException eofe) {
        	logger.warn("IOException: " + eofe.getMessage());
            //System.out.println("EOFException: " + eofe.getMessage());
        } catch (IOException ioe) {
        	logger.warn("IOException: " + ioe.getMessage());
            //System.out.println("IOException: " + ioe.getMessage());
        } finally {
            try {
                this.input.close();
                this.output.close();
                this.socket.close();
            } catch (IOException ioe) {
            	logger.warn("IOException: " + ioe.getMessage());
                //System.err.println("IOException: " + ioe.getMessage());
            }
        }
        logger.info("ReceiveThread finished");
        //System.out.println("ReceiveThread finished.");
    } //run
} //class