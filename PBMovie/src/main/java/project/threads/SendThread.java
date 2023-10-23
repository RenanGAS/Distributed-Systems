package project.threads;

/**
 * SendThread: Thread para envio de requisições ao TCPServer 
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
import project.stream.Parser;
import project.javaOut.Movie;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import project.stream.Format;

public class SendThread extends Thread {
    DataInputStream input;

    DataOutputStream output;

    Socket socket;
    
    //Logger logger = LoggerFactory.getLogger(SendThread.class);

    public SendThread(Socket socket) {
        try {
            this.socket = socket;
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
        	//logger.warn("IOException: " + ioe.getMessage());
            System.out.println("IOException: " + ioe.getMessage());
        } //catch
        
    } //construtor
    
    void handleCodeOperation(String operation, Scanner scanner) throws IOException { 
        Format format = new Format();
        Parser parser = new Parser();

        switch (operation) {
            case "create":
                // create 
                Movie movie = formMovie(scanner);
                byte[] createRequest = format.formatCreate(movie);
                this.output.write(createRequest, 0, createRequest.length);  // envia a mensagem para o servidor
                break;
            case "read":
                System.out.print("Nome do filme: ");
                String movieName = scanner.nextLine();
                byte[] readRequest = format.formatRead(movieName);
                this.output.write(readRequest, 0, readRequest.length);
                break;
            case "update":
                // Mandar read, pegar Movie e fazer um form com os valores correntes do lado
                // os que forem preenchidos serao mandados na requisição, sem ser um objeto 
                // protobuff.
                break;
            case "delete":
                System.out.print("Nome do filme: ");
                String movieNameDel = scanner.nextLine();
                byte[] deleteRequest = format.formatDelete(movieNameDel);
                this.output.write(deleteRequest, 0, deleteRequest.length);
                break;
            case "listByActor":
                System.out.print("Nome do ator: ");
                String actorName = scanner.nextLine();
                byte[] listByActorRequest = format.formatListByActor(actorName);
                this.output.write(listByActorRequest, 0, listByActorRequest.length);
                byte[] listByActorResponse = new byte[1024]; 
                this.input.read(listByActorResponse);
            default:
                System.out.println("ERROR: Unsupported operation\n");
                break;
        }
    } //connectUser

    Movie formMovie(Scanner scanner) {
        Movie.Builder movie = Movie.newBuilder();

        System.out.println("\nTitle: ");
        String title = scanner.nextLine(); 
        movie.setTitle(title);

        System.out.println("\nYear: ");
        int year = Integer.valueOf(scanner.nextLine()); 
        movie.setYear(year);

        System.out.println("\nDate: ");
        String released = scanner.nextLine(); 
        movie.setReleased(released);

        System.out.println("\nURL Poster: ");
        String poster = scanner.nextLine(); 
        movie.setPoster(poster);

        System.out.println("\nPlot: ");
        String plot = scanner.nextLine(); 
        movie.setPlot(plot);           

        System.out.println("\nFull plot: ");
        String fullplot = scanner.nextLine(); 
        movie.setFullplot(fullplot);           

        System.out.println("Directors' names:\n");
        System.out.println("(Press key 'q' for quit)\n");
        
        int countDirectors = 1;
        while(true) {
            System.out.format("\nDirector %d: ", countDirectors);
            String directorName = scanner.nextLine(); 

            if ("q".equals(directorName)) {
                if (countDirectors == 1) {
                   System.out.println("ERROR: At least one director must be provided\n"); 
                   continue;
                } else {
                    System.out.println("Quitting...\n");
                    break;
                }
            }

            movie.addDirectors(directorName);
            countDirectors++;
        } 

        System.out.println("Actors' names:\n");
        System.out.println("(Press key 'q' for quit)\n");

        int countCast = 1;
        while(true) {
            System.out.format("\nActor %d: ", countCast);
            String actorName = scanner.nextLine(); 

            if ("q".equals(actorName)) {
                if (countCast == 1) {
                   System.out.println("ERROR: At least one actor must be provided\n"); 
                   continue;
                } else {
                    System.out.println("Quitting...\n");
                    break;
                }
            }

            movie.addCast(actorName);
            countCast++;
        }

        System.out.println("Countries' names:\n");
        System.out.println("(Press key 'q' for quit)\n");

        int countCountry = 1;
        while(true) {
            System.out.format("\nCountry %d: ", countCountry);
            String countryName = scanner.nextLine(); 

            if ("q".equals(countryName)) {
                if (countCountry == 1) {
                   System.out.println("ERROR: At least one country must be provided\n"); 
                   continue;
                } else {
                    System.out.println("Quitting...\n");
                    break;
                }
            }

            movie.addContries(countryName);
            countCountry++;
        }

        System.out.println("Genres' names:\n");
        System.out.println("(Press key 'q' for quit)\n");

        int countGenre = 1;
        while(true) {
            System.out.format("\nGenre %d: ", countGenre);
            String genreName = scanner.nextLine(); 

            if ("q".equals(genreName)) {
                if (countGenre == 1) {
                   System.out.println("ERROR: At least one genre must be provided\n"); 
                   continue;
                } else {
                    System.out.println("Quitting...\n");
                    break;
                }
            }

            movie.addGenres(genreName);
            countGenre++;
        }

        return movie.build();
    }

    /* metodo executado ao iniciar a thread - start() */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in); // ler mensagens via teclado

        try {
            String buffer = "";
            while (true) {
                buffer = scanner.nextLine(); // lê mensagem via teclado
                
                if (buffer.trim().equals("EXIT")) {
                	//logger.info("Client exiting");
                	break;
                }
                
                handleCodeOperation(buffer, scanner);
            }

            System.out.println("SendThread exitted\n");
        } catch (EOFException eofe) {
        	//logger.warn("EOFException: " + eofe.getMessage());
            System.out.println("EOFException: " + eofe.getMessage());
        } catch (IOException ioe) {
        	//logger.warn("IOException: " + ioe.getMessage());
            System.out.println("IOException: " + ioe.getMessage());
        } finally {
            try {
                this.input.close();
                this.output.close();
                this.socket.close();
            } catch (IOException ioe) {
            	//logger.warn("IOException: " + ioe.getMessage());
                System.err.println("IOException: " + ioe);
            }
        }
        //logger.info("SendThread finished.");
        System.out.println("SendThread finished.");
    } //run
} //class

