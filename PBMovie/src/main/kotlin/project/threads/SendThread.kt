package project.threads

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.IOException
import java.net.Socket
import java.util.ArrayList
import kotlin.collections.List
import java.util.Scanner
import org.bson.json.JsonObject
import java.io.StringWriter
import org.bson.json.JsonWriter

import project.javaOut.Movie
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
import project.stream.FormatRequest

/**
 * SendThread: Thread para envio de requisições ao TCPServer 
 */
public class SendThread(socketC: Socket): Thread() {
    val input: DataInputStream
    val output: DataOutputStream
    val formatReq: FormatRequest
    val socket: Socket

    init {
        formatReq = FormatRequest()
        socket = socketC
        input = DataInputStream(socket.getInputStream())
        output = DataOutputStream(socket.getOutputStream())
    }

    fun handleCodeOperation(operation: String, scanner: Scanner) { 
        when (operation) {
            "create" -> {
                // create 
                var movie: Movie = formMovie(scanner)
                var createRequest: ByteArray = formatReq.create(movie)
                output.write(createRequest, 0, createRequest.size)  // envia a mensagem para o servidor
            }
            "read" -> {
                System.out.print("Nome do filme: ")
                var movieName: String = scanner.nextLine()
                var readRequest: ByteArray = formatReq.read(movieName)
                output.write(readRequest, 0, readRequest.size)
            }
            "update" -> {
                var movieUpdates: Movie = formEditMovie(scanner)

                println(movieUpdates.toString())

                var updateRequest: ByteArray = formatReq.update(movieUpdates)
                output.write(updateRequest, 0, updateRequest.size)
            }
            "delete" -> {
                System.out.print("Nome do filme: ")
                var movieNameDel: String = scanner.nextLine()
                var deleteRequest: ByteArray = formatReq.delete(movieNameDel)
                output.write(deleteRequest, 0, deleteRequest.size)
            }
            "listByActor" -> {
                System.out.print("Nome do ator: ")
                var actorName: String = scanner.nextLine()
                var listByActorRequest: ByteArray = formatReq.listByActor(actorName)
                output.write(listByActorRequest, 0, listByActorRequest.size)

                // Fazer isso em ReceiveThreadClient
                // var listByActorResponse: ByteArray = ByteArray(1024) 
                // input.read(listByActorResponse)
            }
            else -> {
                System.out.println("ERROR: Unsupported operation\n")
            }
        }
    }

    fun formMovie(scanner: Scanner): Movie {
        var movie: Movie.Builder = Movie.newBuilder()

        System.out.println("\nTitle: ")
        var title: String = scanner.nextLine() 
        movie.setTitle(title)

        System.out.println("\nYear: ")
        var year: Int = Integer.valueOf(scanner.nextLine()) 
        movie.setYear(year)

        System.out.println("\nDate: ")
        var released: String = scanner.nextLine() 
        movie.setReleased(released)

        System.out.println("\nURL Poster: ")
        var poster: String = scanner.nextLine() 
        movie.setPoster(poster)

        System.out.println("\nPlot: ")
        var plot: String = scanner.nextLine() 
        movie.setPlot(plot)           

        System.out.println("\nFull plot: ")
        var fullplot: String = scanner.nextLine() 
        movie.setFullplot(fullplot)           

        System.out.println("Directors' names:\n")
        System.out.println("(Press key 'q' for quit)\n")
        
        var countDirectors = 1
        while(true) {
            System.out.format("\nDirector %d: ", countDirectors)
            var directorName: String = scanner.nextLine() 

            if ("q".equals(directorName)) {
                if (countDirectors == 1) {
                   System.out.println("ERROR: At least one director must be provided\n") 
                   continue
                } else {
                    System.out.println("Quitting...\n")
                    break
                }
            }

            movie.addDirectors(directorName)
            countDirectors++
        } 

        System.out.println("Actors' names:\n")
        System.out.println("(Press key 'q' for quit)\n")

        var countCast = 1
        while(true) {
            System.out.format("\nActor %d: ", countCast)
            var actorName: String = scanner.nextLine() 

            if ("q".equals(actorName)) {
                if (countCast == 1) {
                   System.out.println("ERROR: At least one actor must be provided\n") 
                   continue
                } else {
                    System.out.println("Quitting...\n")
                    break
                }
            }

            movie.addCast(actorName)
            countCast++
        }

        System.out.println("Countries' names:\n")
        System.out.println("(Press key 'q' for quit)\n")

        var countCountry = 1
        while(true) {
            System.out.format("\nCountry %d: ", countCountry)
            var countryName: String = scanner.nextLine() 

            if ("q".equals(countryName)) {
                if (countCountry == 1) {
                   System.out.println("ERROR: At least one country must be provided\n") 
                   continue
                } else {
                    System.out.println("Quitting...\n")
                    break
                }
            }

            movie.addContries(countryName)
            countCountry++
        }

        System.out.println("Genres' names:\n")
        System.out.println("(Press key 'q' for quit)\n")

        var countGenre = 1
        while(true) {
            System.out.format("\nGenre %d: ", countGenre)
            var genreName: String = scanner.nextLine() 

            if ("q".equals(genreName)) {
                if (countGenre == 1) {
                   System.out.println("ERROR: At least one genre must be provided\n") 
                   continue
                } else {
                    System.out.println("Quitting...\n")
                    break
                }
            }

            movie.addGenres(genreName)
            countGenre++
        }

        return movie.build()
    }

    fun formEditMovie(scanner: Scanner): Movie {
        var movie: Movie.Builder = Movie.newBuilder()

        System.out.println("\nTitle: ")
        var title: String = scanner.nextLine() 
        movie.setTitle(title)

        println(movie.toString())

        System.out.println("\nYear: ")
        try {
            var yearString: String = scanner.nextLine()
            if (yearString.isNotBlank()) {
                var year: Int = Integer.valueOf(yearString) 
                movie.setYear(year)
            }
        } catch (nfe: NumberFormatException) {
            throw IOException("Expected a number")
        }

        System.out.println("\nDate: ")
        var released: String = scanner.nextLine() 
        if (released.isNotBlank()) {
            movie.setReleased(released)
        }

        System.out.println("\nURL Poster: ")
        var poster: String = scanner.nextLine() 
        if (poster.isNotBlank()) {
            movie.setPoster(poster)
        }

        System.out.println("\nPlot: ")
        var plot: String = scanner.nextLine() 
        if (plot.isNotBlank()) {
            movie.setPlot(plot)           
        }

        System.out.println("\nFull plot: ")
        var fullplot: String = scanner.nextLine() 
        if (fullplot.isNotBlank()) {
            movie.setFullplot(fullplot)           
        }

        System.out.println("Directors' names:\n")
        System.out.println("(Press key 'q' for quit)\n")
        
        var countDirectors = 1
        while(true) {
            System.out.format("\nDirector %d: ", countDirectors)
            var directorName: String = scanner.nextLine() 

            if ("q".equals(directorName) || directorName.isBlank()) {
                break
            }

            movie.addDirectors(directorName)
            countDirectors++
        } 

        System.out.println("Actors' names:\n")
        System.out.println("(Press key 'q' for quit)\n")

        var countCast = 1
        while(true) {
            System.out.format("\nActor %d: ", countCast)
            var actorName: String = scanner.nextLine() 

            if ("q".equals(actorName) || actorName.isBlank()) {
                break
            }

            movie.addCast(actorName)
            countCast++
        }

        System.out.println("Countries' names:\n")
        System.out.println("(Press key 'q' for quit)\n")

        var countCountry = 1
        while(true) {
            System.out.format("\nCountry %d: ", countCountry)
            var countryName: String = scanner.nextLine() 

            if ("q".equals(countryName) || countryName.isBlank()) {
                break
            }

            movie.addContries(countryName)
            countCountry++
        }

        System.out.println("Genres' names:\n")
        System.out.println("(Press key 'q' for quit)\n")

        var countGenre = 1
        while(true) {
            System.out.format("\nGenre %d: ", countGenre)
            var genreName: String = scanner.nextLine() 

            if ("q".equals(genreName) || genreName.isBlank()) {
                break
            }

            movie.addGenres(genreName)
            countGenre++
        }

        return movie.build()
    }

    public override fun run() {
        var scanner = Scanner(System.`in`) // ler mensagens via teclado

        try {
            var buffer: String
            while (true) {
                buffer = scanner.nextLine() // lê mensagem via teclado

                if (buffer.trim().equals("EXIT")) {
                    //logger.info("Client exiting")
                    break
                }

                try {
                    handleCodeOperation(buffer, scanner)
                } catch (ioe: IOException) {
                    println("ERROR: " + ioe.message)
                } catch (e: Exception) {
                    println("ERROR: " + e.message)
                }
            }

            System.out.println("SendThread exitted\n")
        } catch (eofe: EOFException) {
            //logger.warn("EOFException: " + eofe.message)
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
        //logger.info("SendThread finished.")
        System.out.println("SendThread finished.")

    }
}
