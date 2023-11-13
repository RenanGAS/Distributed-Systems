package project.stream;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.google.protobuf.InvalidProtocolBufferException;

import project.javaOut.Movie;
import org.bson.json.JsonObject;

/**
 * Parser: Faz o parse de uma requisição de acordo com seu tipo, para o acesso ao seu conteúdo.
 */
public class ParserRequest {
    
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
    public Movie create(byte[] request) throws InvalidProtocolBufferException {
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
     * Parse das requisições 'read', 'delete', 'listByActor' e 'listByCategory' 
     *
     * @param request bytes da requisição 
     * @return Parametro a ser utilizado 
     */
    public String getQueryParam(byte[] request) throws InvalidProtocolBufferException {
        ByteBuffer requestBuffer = ByteBuffer.allocate(request.length);
        requestBuffer.put(request);

        int lenghtParam = requestBuffer.getInt(4);
        byte[] paramBytes = new byte[lenghtParam];
        requestBuffer.get(8, paramBytes);
        String param = new String(paramBytes, StandardCharsets.UTF_8);

        System.out.format("param: %s\n", param);

        return param;
    }

    /**
     * Parse da requisição 'update'
     *
     * @param request bytes da requisição
     * @return Objeto Json dos campos a serem editados do filme
     */
    public JsonObject update(byte[] request) throws InvalidProtocolBufferException {
        ByteBuffer requestBuffer = ByteBuffer.allocate(request.length);
        requestBuffer.put(request);

        int lenghtJsonString = requestBuffer.getInt(4);

        byte[] movieJsonStringBytes = new byte[lenghtJsonString];
        requestBuffer.get(8, movieJsonStringBytes);

        String movieJsonString = new String(movieJsonStringBytes, StandardCharsets.UTF_8);

        JsonObject movieJson = new JsonObject(movieJsonString);
        return movieJson;
    }
}

