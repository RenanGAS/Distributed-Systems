package project.stream;

import java.util.List;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import project.javaOut.Movie;

/**
 * Format: Formata uma requisição conforme seu tipo para ser enviada ao servidor. 
 */
public class Format {

    /**
     * Formata requisição 'create'
     *
     * @param movie Objeto Movie
     * @return Byte array composto do código da requisição e do tamanho e objeto proto
     */
    public byte[] formatCreate(Movie movie) throws IOException {
        byte[] movieBytes = movie.toByteArray();

        int movieSize = movieBytes.length;

        int capacity = 8 + movieSize; 

        ByteBuffer data = ByteBuffer.allocate(capacity);

        // Código para operação 'create': 1
        data.putInt(1);
        data.putInt(movieSize);
        data.put(movieBytes);

        return data.array();
    }

    /**
     * Formata requisição 'read'
     *
     * @param movieName Nome do filme procurado
     * @return Byte array composto do código da requisição e do tamanho e string do nome do filme
     */
    public byte[] formatRead(String movieName) throws IOException {
        int movieNameSize = movieName.length();

        int capacity = 8 + movieNameSize; 

        ByteBuffer data = ByteBuffer.allocate(capacity);

        // Código para operação 'read': 2
        data.putInt(2);
        data.putInt(movieNameSize);
        data.put(movieName.getBytes(StandardCharsets.UTF_8));

        return data.array();
    }

    /**
     * Formata requisição 'delete'
     *
     * @param movieName Nome do filme a ser deletado
     * @return Byte array composto do código da requisição e do tamanho e string do nome do filme
     */
    public byte[] formatDelete(String movieName) throws IOException {
        int movieNameSize = movieName.length();

        int capacity = 8 + movieNameSize; 

        ByteBuffer data = ByteBuffer.allocate(capacity);

        // Código para operação 'delete': 4
        data.putInt(4);
        data.putInt(movieNameSize);
        data.put(movieName.getBytes(StandardCharsets.UTF_8));

        return data.array();
    }

     /**
     * Formata requisição 'listByActor'
     *
     * @param actorName Nome do ator alvo para busca
     * @return Byte array composto do código da requisição e do tamanho e string do nome do ator
     */
    public byte[] formatListByActor(String actorName) throws IOException {
        int actorNameSize = actorName.length();

        int capacity = 8 + actorNameSize; 

        ByteBuffer data = ByteBuffer.allocate(capacity);

        // Código para operação 'listByActor': 5
        data.putInt(5);
        data.putInt(actorNameSize);
        data.put(actorName.getBytes(StandardCharsets.UTF_8));

        return data.array();
    }

    /**
     * Formata resposta de 'create'
     *
     * @param code Código de sucesso (0) ou falha (1) 
     * @param message Mensagem de sucesso ou falha
     * @return Byte array composto do código da reposta e do tamanho e conteúdo da mensagem
     */
    public byte[] formatResponseCreateDelete(int code, String message) throws IOException {
        System.out.println("format: formatResponseCreate\n");
        
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

        int messageSize = messageBytes.length;

        int capacity = 8 + messageSize; 

        ByteBuffer data = ByteBuffer.allocate(capacity);

        data.putInt(code);
        data.putInt(messageSize);
        data.put(messageBytes);

        return data.array();
    }

     /**
     * Formata resposta de 'read'
     *
     * @param code Código de sucesso (0) ou falha (1) 
     * @param content Bytes do filme ou da mensagem de falha 
     * @return Byte array composto do código da reposta e do tamanho e conteúdo da mensagem
     */
    public byte[] formatResponseRead(int code, byte[] content) throws IOException {
        //System.out.println("format: formatResponseCreate\n");
        
        int contentSize = content.length;

        int capacity = 8 + contentSize; 

        ByteBuffer data = ByteBuffer.allocate(capacity);

        data.putInt(code);
        data.putInt(contentSize);
        data.put(content);

        return data.array();
    }

    /**
     * Formata resposta de 'listByActor'
     *
     * @param listMovies Lista de filmes
     * @return Resposta do formato codigo + numMovies + [(lenghtMovie + Move) + ...]
     */
    public byte[] formatResponseListByActor(List<Movie> listMovies) {
        int listSize = listMovies.size();

        int capacity = 8; 

        int lenghtAllMovies = 0;

        for(Movie movie : listMovies) {
            lenghtAllMovies += 4;
            lenghtAllMovies += movie.toByteArray().length;
        }

        capacity += lenghtAllMovies;
        
        ByteBuffer data = ByteBuffer.allocate(capacity);

        data.putInt(205);
        data.putInt(listSize);

        for(Movie movie : listMovies) {
            byte[] movieBytes = movie.toByteArray();
            data.putInt(movieBytes.length);
            data.put(movieBytes);
        }

        return data.array();
    }

    /**
     * Formata resposta de 'listByActor' quando falha
     *
     * @param message Mensagem de falha 
     * @return Byte array composto do código da requisição e do tamanho e string da mensagem de falha
     */
    public byte[] formatResponseListByActorFail(String message) {
        int messageSize = message.length();

        int capacity = 8 + messageSize; 

        ByteBuffer data = ByteBuffer.allocate(capacity);

        data.putInt(405);
        data.putInt(messageSize);
        data.put(message.getBytes(StandardCharsets.UTF_8));

        return data.array();
    }

}
