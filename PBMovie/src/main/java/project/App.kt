package project;

import java.net.Socket;
import java.net.InetAddress;
import project.kotlinOut.output.MovieKt;

fun main(args: Array<String>) {
    val server = ServerSocket(6666)
    println("Server is running on port ${server.localPort}")

    while (true) {
        val client = server.accept()
        println("Client connected: ${client.inetAddress.hostAddress}")
        val inClient = DataInputStream(client.getInputStream());

        val valueStr = inClient.readLine();
        val sizeBuffer = valueStr.toInt();

        val buffer = ByteArray(sizeBuffer);

        inClient.read(buffer);

        val movie = MovieKt.parseFrom(buffer);

        /* exibe na tela */
        print(movie);
    }
}
