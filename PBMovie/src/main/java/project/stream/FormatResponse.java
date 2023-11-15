package project.stream;

import java.util.List;
import java.io.IOException;
import java.nio.ByteBuffer;
import project.javaOut.Movie;

/**
 * FormatResponse: Formata respostas para ReceiveThreadClient 
 */
public class FormatResponse {

     /**
     * Formata modelo de resposta padrão: código | bytes
     *
     * @param code Código de sucesso (20X) ou falha (40X) 
     * @param content Bytes da resposta 
     * @return Byte array com os bytes da resposta
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
     * Formata resposta de 'listByActor' e 'listByGenre': código | tamanhoLista | [(tamanhoMovie | Movie), ...]
     *
     * @param code Código da resposta da operação (205 ou 206)
     * @param listMovies Lista de filmes
     * @return Byte array com os bytes da resposta
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
