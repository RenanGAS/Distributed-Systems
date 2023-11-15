package project.stream;

import java.util.List;

import org.bson.json.JsonObject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import project.javaOut.Movie;

/**
 * FormatResponse: Formata respostas para o Cliente. 
 */
public class FormatResponse {

     /**
     * Formata resposta padrão: código + bytes
     *
     * @param code Código de sucesso (20X) ou falha (40X) 
     * @param content Bytes da resposta 
     * @return Byte array composto do código da reposta e do tamanho e conteúdo da mensagem
     */
    public byte[] standard(int code, byte[] content) throws IOException {
        int contentSize = content.length;

        int capacity = 8 + contentSize; 

        ByteBuffer data = ByteBuffer.allocate(capacity);

        data.putInt(code);
        data.putInt(contentSize);
        data.put(content);

        return data.array();
    }

    /**
     * Formata resposta de 'listByAttribute'
     *
     * @param listMovies Lista de filmes
     * @return Resposta do formato codigo + numMovies + [(lenghtMovie + Move) + ...]
     */
    public byte[] listByAttribute(int code, List<Movie> listMovies) {
        int listSize = listMovies.size();

        int capacity = 8; 

        int lenghtAllMovies = 0;

        for(Movie movie : listMovies) {
            lenghtAllMovies += 4;
            lenghtAllMovies += movie.toByteArray().length;
        }

        capacity += lenghtAllMovies;
        
        ByteBuffer data = ByteBuffer.allocate(capacity);

        data.putInt(code);
        data.putInt(listSize);

        for(Movie movie : listMovies) {
            byte[] movieBytes = movie.toByteArray();
            data.putInt(movieBytes.length);
            data.put(movieBytes);
        }

        return data.array();
    }
}
