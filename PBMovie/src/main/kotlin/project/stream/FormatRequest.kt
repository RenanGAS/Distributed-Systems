package project.stream

import org.bson.json.JsonObject
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import project.javaOut.Movie

/**
 * FormatRequest: Formata uma requisição conforme seu tipo para ser enviada ao ReceiveThreadServer. 
 */
public class FormatRequest {

    /**
     * Formata requisição 'create': código | tamanhoMovie | objetoMovie
     *
     * @param movie Objeto Movie
     * @return Byte array com os bytes da requisição
     */
    fun create(movie: Movie): ByteArray {
        var movieBytes: ByteArray = movie.toByteArray()

        var movieSize: Int = movieBytes.size
        var capacity: Int = 8 + movieSize 

        var data: ByteBuffer = ByteBuffer.allocate(capacity)

        // Código para operação 'create': 1
        data.putInt(1)
        data.putInt(movieSize)
        data.put(movieBytes)

        return data.array()
    }

    /**
     * Formata requisição 'read': código | tamanhoNomeMovie | nomeMovie
     *
     * @param movieName Nome do Movie procurado
     * @return Byte array com os bytes da requisição
     */
    fun read(movieName: String): ByteArray {
        var movieNameSize: Int = movieName.length

        var capacity: Int = 8 + movieNameSize 

        var data: ByteBuffer = ByteBuffer.allocate(capacity)

        // Código para operação 'read': 2
        data.putInt(2)
        data.putInt(movieNameSize)
        data.put(movieName.toByteArray(StandardCharsets.UTF_8))

        return data.array()
    }

    /**
     * Formata requisição 'update': código | tamanhoMovieEditado | movieEditado
     *
     * @param movieUpdates Movie com campos a serem atualizados 
     * @return Byte array com os bytes da requisição  
     */
    fun update(movieUpdates: Movie): ByteArray {
        var movieUpdatesBytes: ByteArray = movieUpdates.toByteArray()

        var movieUpdatesByteSize: Int = movieUpdatesBytes.size

        var capacity: Int = 8 + movieUpdatesByteSize 

        var data: ByteBuffer = ByteBuffer.allocate(capacity)

        // Código para operação 'update': 3
        data.putInt(3)
        data.putInt(movieUpdatesByteSize)
        data.put(movieUpdatesBytes)

        return data.array()
    }

    /**
     * Formata requisição 'delete': código | tamanhoNomeMovieDeletar | nomeMovieDeletar
     *
     * @param movieName Nome do Movie a ser deletado
     * @return Byte array com os bytes da requisição
     */
    fun delete(movieName: String): ByteArray {
        var movieNameSize: Int = movieName.length

        var capacity: Int  = 8 + movieNameSize 

        var data: ByteBuffer = ByteBuffer.allocate(capacity)

        // Código para operação 'delete': 4
        data.putInt(4)
        data.putInt(movieNameSize)
        data.put(movieName.toByteArray(StandardCharsets.UTF_8))

        return data.array()
    }

     /**
     * Formata as requisições 'listByActor' e 'listByGenre': código | tamanhoValorAtributo | valorAtributo
     *
     * @param code Código da requisição (5 ou 6)
     * @param attValue Valor do atributo para busca 
     * @return Byte array com os bytes da requisição 
     */
    fun listByAttribute(code: Int, attValue: String): ByteArray {
        var attValueSize: Int = attValue.length

        var capacity: Int = 8 + attValueSize 

        var data: ByteBuffer = ByteBuffer.allocate(capacity)

        data.putInt(code)
        data.putInt(attValueSize)
        data.put(attValue.toByteArray(StandardCharsets.UTF_8))

        return data.array()
    }
}

