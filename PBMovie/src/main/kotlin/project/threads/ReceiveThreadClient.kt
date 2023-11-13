package project.threads

/**
 * ReceiveThreadClient: Thread para receber respostas do Servidor 
 */

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.IOException
import java.net.Socket
import java.nio.charset.StandardCharsets
import kotlin.collections.List
import org.bson.json.JsonObject

import com.mongodb.MongoException

//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
import project.stream.ParserResponse
import project.javaOut.Movie
import project.mongodb.CrudMovie

public class ReceiveThreadClient(socketC: Socket): Thread() {
    val input: DataInputStream
    val output: DataOutputStream
    val parserResp: ParserResponse
    val socket: Socket

    init {
        parserResp = ParserResponse()
        socket = socketC
        input = DataInputStream(socket.getInputStream())
        output = DataOutputStream(socket.getOutputStream())
    }

    /**
     * Lida com respostas do Servidor
     *
     * @param code Código da resposta
     * @param response Resposta em bytes
     * @return Resultado da requisição ou mensagem de sucesso ou erro 
     */
    fun handleCodeResponse(code: Int, response: ByteArray) { 
        when (code) {
            201 -> {
                var successResponseCreate: String = parserResp.message(code, response) 
                System.out.println(successResponseCreate)
 
            }
            401 -> {
                var failResponseCreate: String = parserResp.message(code, response) 
                System.out.println(failResponseCreate)

            }
            202 -> {
                var movieRead: Movie = parserResp.read(response) 
                System.out.println(movieRead.toString()) 
            }
            402 -> {
                var failResponseRead: String = parserResp.message(code, response) 
                System.out.println(failResponseRead)

            }
            203 -> {
                var successResponseUpdate: String = parserResp.message(code, response) 
                System.out.println(successResponseUpdate)
 
            }
            403 -> {
                var failResponseUpdate: String = parserResp.message(code, response) 
                System.out.println(failResponseUpdate)

            }
            204 -> {
                var successResponseDelete: String = parserResp.message(code, response) 
                System.out.println(successResponseDelete) 
            }
            404 -> {
                var failResponseDelete: String = parserResp.message(code, response) 
                System.out.println(failResponseDelete)

            }
            205 -> {
                var listMoviesByActor: List<Movie> = parserResp.listByActor(response) 
                
                System.out.println("\nLista de filmes do ator:")

               for (i in 0..listMoviesByActor.size - 1) { 
                    System.out.println("\nFilme " + i.toString() + " ---------------------------------------------\n")
                    System.out.println(listMoviesByActor.get(i).toString()) 
                }

            }
            405 -> {
                var failResponseListByActor: String = parserResp.message(code, response) 
                System.out.println(failResponseListByActor)

            }
            else -> {
                System.out.println("ERROR: Unexpected response code\n")
            }
        }
    }


    public override fun run() {
        try {
            var buffer: ByteArray = ByteArray(12024) // Limite de 10 filmes na listagem

            while (true) {
                input.read(buffer) 

                try {
                    var code: Int = parserResp.getCode(buffer)
                    handleCodeResponse(code, buffer)
                } catch (ioe: IOException) {
                    //logger.warn("IOException: " + ioe.getMessage())
                    //output.writeUTF("ERROR: " + ioe.getMessage())
                    System.out.println(ioe.message)
                }
            }
        } catch (eofe: EOFException) {
        	//logger.warn("IOException: " + eofe.message)
            System.out.println("EOFException: " + eofe.message)
        } catch (ioe: IOException) {
        	//logger.warn("IOException: " + ioe.message)
            System.out.println("IOException: " + ioe.message)
        } finally {
            try {
                input.close()
                output.close()
                socket.close()
            } catch (ioe: IOException) {
            	//logger.warn("IOException: " + ioe.message)
                System.err.println("IOException: " + ioe.message)
            }
        }
        //logger.info("ReceiveThread finished")
        System.out.println("ReceiveThreadClient finished.")
    } //run
} //class

