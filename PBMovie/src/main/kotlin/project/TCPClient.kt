package project

import java.io.EOFException
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import project.threads.ReceiveThreadClient
import project.threads.SendThread
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory

/**
 * TCPClient: inicia threads para envio de comandos (SendThread) e recebimento de respostas (ReceiveThreadClient) 
 */
fun main() {
    var serverPort = 6666   
    var serverAddr = InetAddress.getByName("127.0.0.1")
    var serverSocket = Socket(serverAddr, serverPort)

    try  {
        var receiveThread = ReceiveThreadClient(serverSocket)

        var sendThread = SendThread(serverSocket)

        receiveThread.start()
        sendThread.start()
        sendThread.join()
        receiveThread.join()
    } catch (ue: UnknownHostException){
        //logger.warn("UnknownHostException:" + ue.getMessage())
        System.out.println("UnknownHostException: " + ue.message + "\n")
    } catch (eofe: EOFException){
        //logger.warn("EOFException:" + eofe.message)
        System.out.println("EOFException: " + eofe.message + "\n")
    } catch (ioe: IOException){
        //logger.warn("IOException:" + ioe.message)
        System.out.println("IOException: " + ioe.message + "\n")
    } catch (ie: InterruptedException) {
        //logger.warn("InterruptedException:" + ie.message)
        System.out.println("InterruptedException: " + ie.message + "\n")
    } finally {
        try {
            serverSocket.close()
        } catch (e: Exception) {
            println("Exception: " + e.message + "\n")
        }
    }
}

