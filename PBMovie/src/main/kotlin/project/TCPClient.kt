package project

import java.io.EOFException
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException

//import org.slf4j.Logger
//import org.slf4j.LoggerFactory

import project.threads.ReceiveThreadClient
import project.threads.SendThread

/**
 * TCPClient: inicia threads para envio de comandos (SendThread) e recebimento de respostas (ReceiveThreadClient) 
 */
fun main() {
    /* Endereço e porta do servidor */
    var serverPort = 6666   
    var serverAddr = InetAddress.getByName("127.0.0.1")
    var serverSocket = Socket(serverAddr, serverPort)

    /* conecta com o servidor */  
    try  {
        /* cria um thread para receber mensagens */
        var receiveThread = ReceiveThreadClient(serverSocket)

        /* cria um thread para enviar mensagens */
        var sendThread = SendThread(serverSocket)

        receiveThread.start()
        sendThread.start()
        sendThread.join()
        receiveThread.join()
    } catch (ue: UnknownHostException){
        //logger.warn("UnknownHostException:" + ue.getMessage())
        System.out.println("UnknownHostException:" + ue.message)
    } catch (eofe: EOFException){
        //logger.warn("EOFException:" + eofe.message)
        System.out.println("EOFException:" + eofe.message)
    } catch (ioe: IOException){
        //logger.warn("IOException:" + ioe.message)
        System.out.println("IOException:" + ioe.message)
    } catch (ie: InterruptedException) {
        //logger.warn("InterruptedException:" + ie.message)
        System.out.println("InterruptedException:" + ie.message)
    } finally {
        println("Terminando")
        try {
            serverSocket.close()
        } catch (e: Exception) {
            println("Erro ao terminar TCPClient")
        }
    }
}
