package project;

import java.util.Scanner;
import java.io.IOException;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.DataOutputStream;
import project.javaOut.output.Movie;

/**
 * Armazena dados de um filme
 *
 */
public class App 
{
    static Movie createMovie(Scanner scanner) throws IOException {
        Movie.Builder movie = Movie.newBuilder();
        System.out.print("Enter movie ID: ");
        movie.setId(Integer.valueOf(scanner.nextLine()));

        System.out.print("Enter name: ");
        movie.setName(scanner.nextLine());

        System.out.print("Enter a rating: ");
        int rating = Integer.valueOf(scanner.nextLine());

        movie.setRating(rating);

        return movie.build();
    }

    public static void main( String[] args ) {
        try {
            
            Socket serverSocket = null; // socket do server

            int serverPort = 6666;

            InetAddress serverAddr = InetAddress.getByName("127.0.0.1");

            serverSocket = new Socket(serverAddr, serverPort);

            DataOutputStream output = new DataOutputStream(serverSocket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            Movie movie = createMovie(scanner);

            output.writeInt(movie.toString().length()); 
            movie.writeTo(output);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
