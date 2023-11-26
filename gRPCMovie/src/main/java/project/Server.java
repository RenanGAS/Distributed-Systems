package project;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.grpc.ServerBuilder;

/**
 * Responde chamdas remotas do cliente realizando operações sobre o servidor MongoDB 
 */
public class Server {
	
	static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String args[]) throws IOException {
       io.grpc.Server server = ServerBuilder
            .forPort(6666)
            .addService(new CrudMovieImpl())
            .build();

       try {
            server.start();
            logger.info("Server started");
            server.awaitTermination();
            logger.info("Server shutdown");
        } catch (IOException | InterruptedException e) {
            logger.warn("IOException: " + e.getMessage());
        }
    } 
} 

