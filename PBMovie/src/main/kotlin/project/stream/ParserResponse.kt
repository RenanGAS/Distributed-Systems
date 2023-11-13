package project.stream;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import com.google.protobuf.InvalidProtocolBufferException;
import project.javaOut.Movie;
import org.bson.json.JsonObject;

/**
 * ParserResponse: Faz o parse de uma requisição de acordo com seu tipo, para o acesso ao seu conteúdo.
 */
public class ParserResponse {

    /**
     * Parse do código da resposta
     *
     * @param request bytes da resposta
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

        //System.out.format("message: %s\n", message);

        if (code % 100 == 2) {
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
     * Parse da resposta 'listByActor'
     *
     * @param response Bytes da resposta
     * @return Lista de filmes
     */
    fun listByActor(response: ByteArray): List<Movie> {
        var responseBuffer: ByteBuffer = ByteBuffer.allocate(response.size);
        responseBuffer.put(response);

        responseBuffer.flip();

        var listLenght: Int = responseBuffer.getInt(4) - 1;

        var listMovies: MutableList<Movie> = mutableListOf() // Ver se tem que definir o tamanho da lista

        responseBuffer.position(8); // ver se isso funciona pro getInt() abaixo

        for (i in 0..listLenght) {
            var movieLenght: Int = responseBuffer.getInt(); // ver se move ponteiro
            var movieBytes: ByteArray = ByteArray(movieLenght);
            responseBuffer.get(movieBytes, 0, movieLenght);
            var movie: Movie = Movie.parseFrom(movieBytes);
            listMovies.add(movie);
        }

        return listMovies.toList();
    }
}

