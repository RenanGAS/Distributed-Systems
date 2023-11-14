package project.stream

import org.bson.json.JsonObject

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

import project.javaOut.Movie

/**
 * Format: Formata uma requisição conforme seu tipo para ser enviada ao servidor. 
 */
public class FormatRequest {

    /**
     * Formata requisição 'create'
     *
     * @param movie Objeto Movie
     * @return Byte array composto do código da requisição e do tamanho e objeto proto
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
     * Formata requisição 'read'
     *
     * @param movieName Nome do filme procurado
     * @return Byte array composto do código da requisição e do tamanho e string do nome do filme
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
     * Formata requisição 'update'
     *
     * @param movieUpdates Movie com campos a serem atualizados 
     * @return Byte array composto do código da requisição, tamanho do filme e objeto proto  
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
     * Formata requisição 'delete'
     *
     * @param movieName Nome do filme a ser deletado
     * @return Byte array composto do código da requisição e do tamanho e string do nome do filme
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
     * Formata requisição 'listByActor'
     *
     * @param actorName Nome do ator alvo para busca
     * @return Byte array composto do código da requisição e do tamanho e string do nome do ator
     */
    fun listByActor(actorName: String): ByteArray {
        var actorNameSize: Int = actorName.length

        var capacity: Int = 8 + actorNameSize 

        var data: ByteBuffer = ByteBuffer.allocate(capacity)

        // Código para operação 'listByActor': 5
        data.putInt(5)
        data.putInt(actorNameSize)
        data.put(actorName.toByteArray(StandardCharsets.UTF_8))

        return data.array()
    }
}

