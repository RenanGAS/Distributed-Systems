package project.stream;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import com.google.protobuf.InvalidProtocolBufferException;
import project.javaOut.Movie;
import org.bson.json.JsonObject;

/**
 * ParserResponse: Faz o parse das respostas do servidor de acordo com seu tipo
 */
public class ParserResponse {

    /**
     * Parse do código da resposta
     *
     * @param request Bytes da resposta
     * @return Valor inteiro do código
     */
    fun getCode(request: ByteArray): Int {
        var requestBuffer: ByteBuffer = ByteBuffer.allocate(request.size);
        requestBuffer.put(request);

        var code: Int  = requestBuffer.getInt(0);
        
        return code;
    }
    
    /**
     * Parse de respostas com mensagens de sucesso ou erro 
     *
     * @param response Bytes da mensagem 
     * @return Mensagem de sucesso ou falha
     */
    fun message(code: Int, response: ByteArray): String {
        var responseBuffer: ByteBuffer = ByteBuffer.allocate(response.size);
        responseBuffer.put(response);

        var messageLenght: Int = responseBuffer.getInt(4);
        var messageBytes: ByteArray = ByteArray(messageLenght);
        responseBuffer.get(8, messageBytes);
        var message: String = String(messageBytes, StandardCharsets.UTF_8);

        if (code / 100 == 2) {
            return "SUCCESS: " + message; 
        }

        return "ERROR: " + message;
    }

    /**
     * Parse da resposta da operação 'read' 
     *
     * @param response Bytes da resposta 
     * @return Objeto Movie 
     */
    fun read(response: ByteArray): Movie {
        var responseBuffer: ByteBuffer = ByteBuffer.allocate(response.size);
        responseBuffer.put(response);

        var contentLenght: Int = responseBuffer.getInt(4);
        var contentBytes: ByteArray = ByteArray(contentLenght);
        responseBuffer.get(8, contentBytes);

        var movie: Movie = Movie.parseFrom(contentBytes);

        return movie; 
    }

    /**
     * Parse da resposta da operação 'listByActor' e 'listByGenre'
     *
     * @param response Bytes da resposta
     * @return Lista de filmes
     */
    fun listByAttribute(response: ByteArray): List<Movie> {
        var responseBuffer: ByteBuffer = ByteBuffer.allocate(response.size);
        responseBuffer.put(response);

        responseBuffer.flip();

        var listLenght: Int = responseBuffer.getInt(4) - 1;

        var listMovies: MutableList<Movie> = mutableListOf() 

        responseBuffer.position(8); 

        for (i in 0..listLenght) {
            var movieLenght: Int = responseBuffer.getInt();
            var movieBytes: ByteArray = ByteArray(movieLenght);
            responseBuffer.get(movieBytes, 0, movieLenght);
            var movie: Movie = Movie.parseFrom(movieBytes);
            listMovies.add(movie);
        }

        return listMovies.toList();
    }
}

