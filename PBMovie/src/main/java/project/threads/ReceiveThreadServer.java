package project.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.mongodb.MongoException;
import project.stream.FormatResponse;
import project.stream.ParserRequest;
import project.javaOut.Movie;
import project.mongodb.CrudMovie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ReceiveThreadServer: Thread para receber e responder requisições de SendThread 
 */
public class ReceiveThreadServer extends Thread {
    DataInputStream input;

    DataOutputStream output;
    
    Socket socket;

    CrudMovie crud;

    FormatResponse formatResp;

    ParserRequest parserReq;
    
    Logger logger = LoggerFactory.getLogger(ReceiveThreadServer.class);

    public ReceiveThreadServer(Socket socket) {
        this.crud = new CrudMovie();
        this.formatResp = new FormatResponse();
        this.parserReq = new ParserRequest();
        try {
            this.socket = socket;
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
        	logger.warn("IOException: " + ioe.getMessage());
            //System.out.println("IOException: " + ioe.getMessage());
        } 
    } 

    /**
     * Lida com as requisições com ParserRequest, CrudMovie (DTO para comunicação com o MongoDB) e FormatResponse 
     *
     * @param code Código da requisição
     * @param request Bytes da requisição 
     * @return Envio da resposta da requisição para ReceiveThreadClient
     */
    void handleCodeOperation(int code, byte[] request) throws IOException { 
        switch (code) {
            case 1:
                // create 
                logger.info("Create request received");

                Movie movieCreate = this.parserReq.create(request);

                try {
                    String responseCreate = this.crud.createMovie(movieCreate);
                    byte[] responseCreateFormatted = this.formatResp.standard(201, responseCreate.getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseCreateFormatted, 0, responseCreateFormatted.length);
                    logger.info("Successfully creation of a Movie");
                } catch (MongoException e) {
                    byte[] responseCreateFormatted = this.formatResp.standard(401, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseCreateFormatted, 0, responseCreateFormatted.length);
                    logger.warn("Failed creation of a Movie");
                }

                break;
            case 2:
                // read
                logger.info("Read request received");

                String movieNameRead = this.parserReq.getQueryParam(request);

                try {
                    Movie movieRead = this.crud.retrieveMovie(movieNameRead);
                    byte[] responseReadFormatted = this.formatResp.standard(202, movieRead.toByteArray());
                    this.output.write(responseReadFormatted, 0, responseReadFormatted.length);
                    logger.info("Successfully info retrieve of a Movie");
                } catch (MongoException e) {
                    byte[] responseReadFormatted = this.formatResp.standard(402, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseReadFormatted, 0, responseReadFormatted.length);
                    logger.warn("Failed info retrieve of a Movie");
                }

                break;
            case 3:
                // update 
                logger.info("Update request received");

                Movie movie = this.parserReq.create(request);

                try {
                    Movie movieResponseUpdate = this.crud.editMovie(movie);
                    byte[] responseUpdateFormatted = this.formatResp.standard(203, movieResponseUpdate.toByteArray());
                    this.output.write(responseUpdateFormatted, 0, responseUpdateFormatted.length);
                    logger.info("Successfully update of a Movie");
                } catch (MongoException e) {
                    byte[] responseUpdateFormatted = this.formatResp.standard(403, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseUpdateFormatted, 0, responseUpdateFormatted.length);
                    logger.warn("Failed update of a Movie");
                }

                break;
            case 4:
                // delete
                logger.info("Delete request received");

                String movieNameDelete = this.parserReq.getQueryParam(request);

                try {
                    String responseDelete = this.crud.deleteMovie(movieNameDelete);
                    byte[] responseDeleteFormatted = this.formatResp.standard(204, responseDelete.getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseDeleteFormatted, 0, responseDeleteFormatted.length);
                    logger.info("Successfully deletion of a Movie");
                } catch (MongoException e) {
                    byte[] responseDeleteFormatted = this.formatResp.standard(404, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseDeleteFormatted, 0, responseDeleteFormatted.length);
                    logger.warn("Failed deletion of a Movie");
                }

                break;
            case 5:
                // listByActor
                logger.info("ListByActor request received");

                String actorName = this.parserReq.getQueryParam(request);

                try {
                    List<Movie> responseListByActor = this.crud.listByActor(actorName);
                    byte[] responseListByActorFormatted = this.formatResp.listByAttribute(205, responseListByActor);
                    this.output.write(responseListByActorFormatted, 0, responseListByActorFormatted.length);
                    logger.info("Successfully retrieve of list of Movies by actor");
                } catch (MongoException e) {
                    byte[] responseListByActorFormatted = this.formatResp.standard(405, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseListByActorFormatted, 0, responseListByActorFormatted.length);
                    logger.warn("Failed retrieve of list of Movies by actor");
                }

                break;
            case 6:
                // listByGenre
                logger.info("ListByGenre request received");

                String genreName = this.parserReq.getQueryParam(request);

                try {
                    List<Movie> responseListByGenre = this.crud.listByGenre(genreName);
                    byte[] responseListByGenreFormatted = this.formatResp.listByAttribute(206, responseListByGenre);
                    this.output.write(responseListByGenreFormatted, 0, responseListByGenreFormatted.length);
                    logger.info("Successfully retrieve of list of Movies by genre");
                } catch (MongoException e) {
                    byte[] responseListByGenreFormatted = this.formatResp.standard(406, e.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(responseListByGenreFormatted, 0, responseListByGenreFormatted.length);
                    logger.warn("Failed retrieve of list of Movies by genre");
                }

                break;
            default:
                logger.warn("Unsupported operation");
                throw new IOException("Unsupported operation");
        }
    } 

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];

            while (true) {
                this.input.read(buffer); 

                try {
                    int code = this.parserReq.getCode(buffer);

                    handleCodeOperation(code, buffer);
                } catch (IOException ioe) {
                    logger.warn("IOException: " + ioe.getMessage());
                    byte[] errorResponse = this.formatResp.standard(500, ioe.getMessage().getBytes(StandardCharsets.UTF_8));
                    this.output.write(errorResponse, 0, errorResponse.length);
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
                //System.err.println("IOException: " + ioe.getMessage() + "\n");
            }
        }
        logger.info("ReceiveThread finished");
        //System.out.println("ReceiveThreadServer finished.");
    } 
} 

