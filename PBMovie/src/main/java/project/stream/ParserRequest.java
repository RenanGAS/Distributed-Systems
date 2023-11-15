package project.stream;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import com.google.protobuf.InvalidProtocolBufferException;
import project.javaOut.Movie;

/**
 * ParserRequest: Faz o parse de uma requisição de acordo com seu tipo
 */
public class ParserRequest {
    
    /**
     * Parse do código da requisição
     *
     * @param request Bytes da requisição
     * @return Valor do código
     */
    public int getCode(byte[] request) {
        ByteBuffer requestBuffer = ByteBuffer.allocate(request.length);
        requestBuffer.put(request);

        int code = requestBuffer.getInt(0);
        
        return code;
    }

    /**
     * Parse da requisição 'create' 
     *
     * @param request Bytes da requisição 
     * @return Objeto Movie 
     */
    public Movie create(byte[] request) throws InvalidProtocolBufferException {
        ByteBuffer requestBuffer = ByteBuffer.allocate(request.length);
        requestBuffer.put(request);

        int lenghtMovie = requestBuffer.getInt(4);
        byte[] movieBytes = new byte[lenghtMovie];
        requestBuffer.get(8, movieBytes);
        Movie movie = Movie.parseFrom(movieBytes);

        return movie;
    }

    /**
     * Parse das requisições 'read', 'delete', 'listByActor' e 'listByCategory' 
     *
     * @param request Bytes da requisição 
     * @return Parâmetro de busca 
     */
    public String getQueryParam(byte[] request) throws InvalidProtocolBufferException {
        ByteBuffer requestBuffer = ByteBuffer.allocate(request.length);
        requestBuffer.put(request);

        int lenghtParam = requestBuffer.getInt(4);
        byte[] paramBytes = new byte[lenghtParam];
        requestBuffer.get(8, paramBytes);
        String param = new String(paramBytes, StandardCharsets.UTF_8);

        return param;
    }
}

