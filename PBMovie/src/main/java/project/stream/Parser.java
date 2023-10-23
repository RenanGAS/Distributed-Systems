package project.stream;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

import project.javaOut.Movie;
import org.bson.json.JsonObject;
import org.bson.json.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Parser: Faz o parse de uma requisição de acordo com seu tipo, para o acesso ao seu conteúdo.
 */
public class Parser {
    
    /**
     * Parse do código da requisição
     *
     * @param request bytes da requisição
     * @return String do código
     */
    public int getCode(byte[] request) {
       // System.out.println("func: getCode\n");

        ByteBuffer requestBuffer = ByteBuffer.allocate(request.length);
        requestBuffer.put(request);

        int code = requestBuffer.getInt(0);
        
        //System.out.format("code : %d\n", code);

        return code;
    }

    /**
     * Parse da requisição 'create' 
     *
     * @param request bytes da requisição 
     * @return Objeto movie para ser inserido no servidor MongoDB
     */
    public Movie parseCreate(byte[] request) throws InvalidProtocolBufferException {
        System.out.println("func: parse\n");

        ByteBuffer requestBuffer = ByteBuffer.allocate(request.length);
        requestBuffer.put(request);

        int lenghtMovie = requestBuffer.getInt(4);
        byte[] movieBytes = new byte[lenghtMovie];
        requestBuffer.get(8, movieBytes);
        Movie movie = Movie.parseFrom(movieBytes);

        System.out.format("movie: %s\n", movie.toString());

        return movie;
    }

    /**
     * Parse da requisição 'read' 
     *
     * @param request bytes da requisição 
     * @return Nome do filme 
     */
    public String parseReadDeleteList(byte[] request) throws InvalidProtocolBufferException {
        System.out.println("func: parseRead\n");

        ByteBuffer requestBuffer = ByteBuffer.allocate(request.length);
        requestBuffer.put(request);

        int lenghtMovieName = requestBuffer.getInt(4);
        byte[] movieNameBytes = new byte[lenghtMovieName];
        requestBuffer.get(8, movieNameBytes);
        String movieName = new String(movieNameBytes, StandardCharsets.UTF_8);

        System.out.format("movie name: %s\n", movieName);

        return movieName;
    }

    /**
     * Parse da resposta 'create' 
     *
     * @param response bytes da response 
     * @return Mensagem de sucesso ou falha
     */
    public String parseResponseCreateDelete(int code, byte[] response) {
        //System.out.println("func: parseResponseCreate\n");

        ByteBuffer responseBuffer = ByteBuffer.allocate(response.length);
        responseBuffer.put(response);

        int messageLenght = responseBuffer.getInt(4);
        byte[] messageBytes = new byte[messageLenght];
        responseBuffer.get(8, messageBytes);
        String message = new String(messageBytes, StandardCharsets.UTF_8);

        //System.out.format("message: %s\n", message);

        if (code == 201 || code == 204) {
            return "SUCCESS: " + message; 
        }

        return "ERROR: " + message;
    }

    /**
     * Parse da resposta 'read' 
     *
     * @param response bytes da response 
     * @return Objeto Movie ou mensagem de falha 
     */
    public Movie parseResponseRead(byte[] response) throws InvalidProtocolBufferException {
        //System.out.println("func: parseResponseCreate\n");

        ByteBuffer responseBuffer = ByteBuffer.allocate(response.length);
        responseBuffer.put(response);

        int contentLenght = responseBuffer.getInt(4);
        byte[] contentBytes = new byte[contentLenght];
        responseBuffer.get(8, contentBytes);

        Movie movie = Movie.parseFrom(contentBytes);

        return movie; 
    }

    /**
     * Parse da resposta 'read' quando falha 
     *
     * @param response bytes da response 
     * @return Objeto Movie ou mensagem de falha 
     */
    public String parseResponseReadFail(byte[] response) {
        //System.out.println("func: parseResponseCreate\n");

        ByteBuffer responseBuffer = ByteBuffer.allocate(response.length);
        responseBuffer.put(response);

        int contentLenght = responseBuffer.getInt(4);
        byte[] contentBytes = new byte[contentLenght];
        responseBuffer.get(8, contentBytes);

        String failMessage = new String(contentBytes, StandardCharsets.UTF_8);

        return "ERROR: " + failMessage;
    }
}
