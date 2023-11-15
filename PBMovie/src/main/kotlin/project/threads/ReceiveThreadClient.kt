package project.threads

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.IOException
import java.net.Socket
import java.nio.charset.StandardCharsets
import kotlin.collections.List
import org.bson.json.JsonObject
import com.mongodb.MongoException
import project.stream.ParserResponse
import project.javaOut.Movie
import project.mongodb.CrudMovie
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory

/**
 * ReceiveThreadClient: Thread para receber respostas de ReceiveThreadServer 
 */
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
                System.out.println("\n" + successResponseCreate + "\n")
            }
            401 -> {
                var failResponseCreate: String = parserResp.message(code, response) 
                System.out.println("\n" + failResponseCreate + "\n")
            }
            202 -> {
                var movieRead: Movie = parserResp.read(response) 
                System.out.println("\n" + movieRead.toString()) 
            }
            402 -> {
                var failResponseRead: String = parserResp.message(code, response) 
                System.out.println("\n" + failResponseRead + "\n")
            }
            203 -> {
                var movieUpdate: Movie = parserResp.read(response) 
                System.out.println("\n" + movieUpdate.toString()) 
            }
            403 -> {
                var failResponseUpdate: String = parserResp.message(code, response) 
                System.out.println("\n" + failResponseUpdate + "\n")
            }
            204 -> {
                var successResponseDelete: String = parserResp.message(code, response) 
                System.out.println("\n" + successResponseDelete + "\n") 
            }
            404 -> {
                var failResponseDelete: String = parserResp.message(code, response) 
                System.out.println("\n" + failResponseDelete + "\n")
            }
            205 -> {
                var listMoviesByActor: List<Movie> = parserResp.listByAttribute(response) 
                
                System.out.println("\nMovies of this actor:")

               for (i in 0..listMoviesByActor.size - 1) { 
                    System.out.println("\n-------------------- Movie " + (i + 1).toString() + " --------------------\n")
                    System.out.println(listMoviesByActor.get(i).toString()) 
                }
            }
            405 -> {
                var failResponseListByActor: String = parserResp.message(code, response) 
                System.out.println("\n" + failResponseListByActor + "\n")
            }
            206 -> {
                var listMoviesByGenre: List<Movie> = parserResp.listByAttribute(response) 
                
                System.out.println("\nMovies of this genre:")

               for (i in 0..listMoviesByGenre.size - 1) { 
                    System.out.println("\n-------------------- Movie " + (i + 1).toString() + " --------------------\n")
                    System.out.println(listMoviesByGenre.get(i).toString()) 
                }
            }
            406 -> {
                var failResponseListByGenre: String = parserResp.message(code, response) 
                System.out.println("\n" + failResponseListByGenre + "\n")
            }
            500 -> {
                var failResponseServer: String = parserResp.message(code, response) 
                System.out.println("\n" + failResponseServer + "\n")
            }
            else -> {
                System.out.println("\nERROR: Unexpected response from the server\n")
            }
        }
    }

    public override fun run() {
        try {
            var buffer: ByteArray = ByteArray(12024) 

            while (true) {
                input.read(buffer) 

                try {
                    var code: Int = parserResp.getCode(buffer)
                    handleCodeResponse(code, buffer)
                } catch (ioe: IOException) {
                    //logger.warn("IOException: " + ioe.getMessage())
                    System.out.println("ERROR: " + ioe.message + "\n")
                }
            }
        } catch (eofe: EOFException) {
        	//logger.warn("IOException: " + eofe.message)
            System.out.println("\nEOFException: " + eofe.message + "\n")
        } catch (ioe: IOException) {
        	//logger.warn("IOException: " + ioe.message)
            System.out.println("\nIOException: " + ioe.message + "\n")
        } finally {
            try {
                input.close()
                output.close()
                socket.close()
            } catch (ioe: IOException) {
            	//logger.warn("IOException: " + ioe.message)
                System.err.println("\nIOException: " + ioe.message + "\n")
            }
        }
        //logger.info("ReceiveThread finished")
        System.out.println("\nReceiveThreadClient finished\n")
    } 
} 

