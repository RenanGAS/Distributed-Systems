package project.threads;

/**
 * ReceiveThreadServer: Thread para responder requisições do Cliente 
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
import project.stream.FormatResponse;
import project.stream.ParserRequest;
import project.javaOut.Movie;
import project.mongodb.CrudMovie;

public class ReceiveThreadServer extends Thread {
    DataInputStream input;

    DataOutputStream output;
    
    Socket socket;

    CrudMovie crud;

    FormatResponse formatResp;

    ParserRequest parserReq;
    
    //Logger logger = LoggerFactory.getLogger(ReceiveThread.class);

    public ReceiveThreadServer(Socket socket) {
        this.crud = new CrudMovie();
        this.formatResp = new FormatResponse();
        this.parserReq = new ParserRequest();
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
     * Lida com requisições do Cliente 
     *
     * @param code Código da requisição
     * @param request Requisição em bytes
     * @return Workflow para cada requisição por parte do Servidor
     */
    void handleCodeOperation(int code, byte[] request) throws IOException { 
        switch (code) {
            case 1:
                // create 
                Movie movieCreate = this.parserReq.create(request);

                try {
                    String responseCreate = this.crud.createMovie(movieCreate);
                    byte[] responseCreateFormatted = this.formatResp.standard(201, responseCreate.getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseCreateFormatted, 0, responseCreateFormatted.length);
                } catch (MongoException e) {
                    byte[] responseCreateFormatted = this.formatResp.standard(401, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseCreateFormatted, 0, responseCreateFormatted.length);
                }

                break;
            case 2:
                // read
                String movieNameRead = this.parserReq.getQueryParam(request);

                try {
                    Movie movieRead = this.crud.retrieveMovie(movieNameRead);
                    byte[] responseReadFormatted = this.formatResp.standard(202, movieRead.toByteArray());
                    this.output.write(responseReadFormatted, 0, responseReadFormatted.length);
                } catch (MongoException e) {
                    byte[] responseReadFormatted = this.formatResp.standard(402, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseReadFormatted, 0, responseReadFormatted.length);
                }

                break;
            case 3:
                // update 
                Movie movie = this.parserReq.create(request);

                try {
                    Movie movieResponseUpdate = this.crud.editMovie(movie);
                    byte[] responseUpdateFormatted = this.formatResp.standard(203, movieResponseUpdate.toByteArray());
                    this.output.write(responseUpdateFormatted, 0, responseUpdateFormatted.length);
                } catch (MongoException e) {
                    byte[] responseUpdateFormatted = this.formatResp.standard(403, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseUpdateFormatted, 0, responseUpdateFormatted.length);
                }

                break;
            case 4:
                // delete
                String movieNameDelete = this.parserReq.getQueryParam(request);

                try {
                    String responseDelete = this.crud.deleteMovie(movieNameDelete);
                    byte[] responseDeleteFormatted = this.formatResp.standard(204, responseDelete.getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseDeleteFormatted, 0, responseDeleteFormatted.length);
                } catch (MongoException e) {
                    byte[] responseDeleteFormatted = this.formatResp.standard(404, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseDeleteFormatted, 0, responseDeleteFormatted.length);
                }

                break;
            case 5:
                // listByActor
                String actorName = this.parserReq.getQueryParam(request);

                try {
                    List<Movie> responseListByActor = this.crud.listByActor(actorName);
                    byte[] responseListByActorFormatted = this.formatResp.listByAttribute(205, responseListByActor);
                    this.output.write(responseListByActorFormatted, 0, responseListByActorFormatted.length);
                } catch (MongoException e) {
                    byte[] responseListByActorFormatted = this.formatResp.standard(405, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseListByActorFormatted, 0, responseListByActorFormatted.length);
                }

                break;
            case 6:
                // listByGenre
                String genreName = this.parserReq.getQueryParam(request);

                try {
                    List<Movie> responseListByGenre = this.crud.listByGenre(genreName);
                    byte[] responseListByGenreFormatted = this.formatResp.listByAttribute(206, responseListByGenre);
                    this.output.write(responseListByGenreFormatted, 0, responseListByGenreFormatted.length);
                } catch (MongoException e) {
                    byte[] responseListByGenreFormatted = this.formatResp.standard(406, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseListByGenreFormatted, 0, responseListByGenreFormatted.length);
                }

                break;

            default:
                System.out.println("ERROR: Unsupported operation\n");
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

                try {
                    int code = this.parserReq.getCode(buffer);

                    handleCodeOperation(code, buffer);
                } catch (IOException ioe) {
                    //logger.warn("IOException: " + ioe.getMessage());
                    //this.output.writeUTF("ERROR: " + ioe.getMessage());
                    System.out.println(ioe.getMessage());
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
        System.out.println("ReceiveThreadServer finished.");
    } //run
} //class

