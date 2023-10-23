package project.threads;

/**
 * ReceiveThread: Thread para responder requisições (TCPServer) ou receber repostas (TCPClient)
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.bson.json.JsonObject;

import com.mongodb.MongoException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import project.stream.Format;
import project.stream.Parser;
import project.javaOut.Movie;
import project.mongodb.CrudMovie;

public class ReceiveThread extends Thread {
    DataInputStream input;

    DataOutputStream output;
    
    Socket socket;
    
    //Logger logger = LoggerFactory.getLogger(ReceiveThread.class);

    public ReceiveThread(Socket socket) {
        try {
            this.socket = socket;
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
        	//logger.warn("IOException: " + ioe.getMessage());
            System.out.println("IOException: " + ioe.getMessage());
        } //catch
    } //construtor

    /**
     * Lida com requisições para o Servidor
     *
     * @param code Código da requisição
     * @param request Requisição em bytes
     * @param parser Parser para as requisições
     * @return Workflow para cada requisição por parte do Servidor
     */
    void handleCodeOperation(int code, byte[] request, Parser parser) throws IOException { 
        CrudMovie crud = new CrudMovie();
        Format format = new Format();

        switch (code) {
            case 1:
                // create 
                Movie movieCreate = parser.parseCreate(request);

                try {
                    String responseCreate = crud.createMovie(movieCreate);
                    byte[] responseCreateFormatted = format.formatResponseCreateDelete(201, responseCreate);
                    this.output.write(responseCreateFormatted, 0, responseCreateFormatted.length);
                } catch (MongoException e) {
                    byte[] responseCreateFormatted = format.formatResponseCreateDelete(401, e.getMessage());
                    this.output.write(responseCreateFormatted, 0, responseCreateFormatted.length);
                }

                break;
            case 2:
                // read
                String movieNameRead = parser.parseReadDeleteList(request);

                try {
                    Movie movieRead = crud.retrieveMovie(movieNameRead);
                    byte[] responseReadFormatted = format.formatResponseRead(202, movieRead.toByteArray());
                    this.output.write(responseReadFormatted, 0, responseReadFormatted.length);
                } catch (MongoException e) {
                    byte[] responseReadFormatted = format.formatResponseRead(402, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseReadFormatted, 0, responseReadFormatted.length);
                }

                break;
            case 3:
                // update 
                break;
            case 4:
                String movieNameDelete = parser.parseReadDeleteList(request);

                try {
                    String responseDelete = crud.deleteMovie(movieNameDelete);
                    byte[] responseDeleteFormatted = format.formatResponseCreateDelete(204, responseDelete);
                    this.output.write(responseDeleteFormatted, 0, responseDeleteFormatted.length);
                } catch (MongoException e) {
                    byte[] responseDeleteFormatted = format.formatResponseCreateDelete(404, e.getMessage());
                    this.output.write(responseDeleteFormatted, 0, responseDeleteFormatted.length);
                }

                break;
            case 5:
                String actorName = parser.parseReadDeleteList(request);

                try {
                    List<Movie> responseListByActor = crud.listByActor(actorName);
                    byte[] responseListByActorFormatted = format.formatResponseListByActor(responseListByActor);
                    this.output.write(responseListByActorFormatted, 0, responseListByActorFormatted.length);
                } catch (MongoException e) {
                    byte[] responseListByActorFormatted = format.formatResponseListByActorFail(e.getMessage());
                    this.output.write(responseListByActorFormatted, 0, responseListByActorFormatted.length);
                }

                break;
            default:
                System.out.println("ERROR: Unsupported operation\n");
                break;
        }
    } 

    /**
     * Lida com respostas para o Cliente
     *
     * @param code Código da resposta
     * @param response Resposta em bytes
     * @param parser Parser para as respostas
     * @return Workflow para cada resposta por parte do Cliente
     */
    void handleCodeResponse(int code, byte[] response, Parser parser) throws IOException { 
        switch (code) {
            case 201:
                String successResponseCreate = parser.parseResponseCreateDelete(code, response); 
                System.out.println(successResponseCreate);
 
                break;
            case 401:
                String failResponseCreate = parser.parseResponseCreateDelete(code, response); 
                System.out.println(failResponseCreate);

                break;
            case 202:
                Movie movieRead = parser.parseResponseRead(response); 
                System.out.println(movieRead.toString()); 
                break;
            case 402:
                String failResponseRead = parser.parseResponseReadFail(response); 
                System.out.println(failResponseRead);

                break;
            case 204:
                String successResponseDelete = parser.parseResponseCreateDelete(code, response); 
                System.out.println(successResponseDelete); 
                break;
            case 404:
                String failResponseDelete = parser.parseResponseCreateDelete(code, response); 
                System.out.println(failResponseDelete);

                break;
            default:
                System.out.println("ERROR: Unexpected response code\n");
                break;
        }
    }

    /* metodo executado ao iniciar a thread - start() */
    @Override
    public void run() {
        try {
            // Este tamanho de 1024 pode não ser o suficiente
            byte[] buffer = new byte[1024];

            while (true) {
                this.input.read(buffer); 

                if (this.socket.getLocalPort() == 6666) {
					try {
                        Parser parser = new Parser();
                        int code = parser.getCode(buffer);

                        handleCodeOperation(code, buffer, parser);
					} catch (IOException ioe) {
						//logger.warn("IOException: " + ioe.getMessage());
						//this.output.writeUTF("ERROR: " + ioe.getMessage());
                        System.out.println(ioe.getMessage());
					}
				} else {
                    try {
                        Parser parser = new Parser();
                        int code = parser.getCode(buffer);

                        handleCodeResponse(code, buffer, parser);
					} catch (IOException ioe) {
						//logger.warn("IOException: " + ioe.getMessage());
						//this.output.writeUTF("ERROR: " + ioe.getMessage());
                        System.out.println(ioe.getMessage());
					}
                }
            }
        } catch (EOFException eofe) {
        	//logger.warn("IOException: " + eofe.getMessage());
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
                System.err.println("IOException: " + ioe.getMessage());
            }
        }
        //logger.info("ReceiveThread finished");
        System.out.println("ReceiveThread finished.");
    } //run
} //class

