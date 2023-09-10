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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    String handleConnectUser(String buffer) throws IOException {
    	Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(buffer);

		if (matcher.find()) {
			String[] sequence = pattern.split(buffer);

			if (sequence.length == 3) {
				List<String> listTokens = Arrays.asList(sequence);
				
				String password = listTokens.get(2);
				
				try {
					MessageDigest md = MessageDigest.getInstance("SHA-512");
					md.update("opisreal".getBytes(StandardCharsets.UTF_8));
					
					byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
					
					StringBuilder sb = new StringBuilder();
					
					for(int i=0; i< bytes.length ;i++){
					    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
					}
					
					password = sb.toString();
				} catch (NoSuchAlgorithmException nsae) {
					throw new IOException("ERROR: " + nsae.getMessage());
				}
				
				return listTokens.get(0) + " " + listTokens.get(1) + " " + password;
			}
		}
		
		return buffer;
    } //connectUser

    /* metodo executado ao iniciar a thread - start() */
    @Override
    public void run() {
        Scanner reader = new Scanner(System.in); // ler mensagens via teclado

        try {
            String buffer = "";
            while (true) {
                buffer = reader.nextLine(); // lê mensagem via teclado
                
                if (buffer.trim().equals("EXIT")) break;
                
                if (buffer.indexOf("CONNECT") != -1) {
                	buffer = handleConnectUser(buffer);
                }
                
                this.output.writeUTF(buffer);      	// envia a mensagem para o servidor
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