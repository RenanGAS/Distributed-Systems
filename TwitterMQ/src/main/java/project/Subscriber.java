package project;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.util.Scanner;

/**
 * Permite inscrição nos tópicos "soccer" e "volley", para recebimento de tweets relacionados ao tópico.
 *
 */
public class Subscriber {
    private static final String EXCHANGE_NAME = "filter";
    private static String[] QUEUES_NAME = {"soccer","volley"};

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        Scanner reader = new Scanner(System.in);

        String queueName;

        while(true) {
            System.out.println("Escolha um ou os dois tópicos: soccer, volley\n\n");
            System.out.println("1 - soccer\n");
            System.out.println("2 - volley\n");
            System.out.println("3 - soccer e volley\n");
            System.out.print("-> ");

            String option = reader.nextLine();

            switch(option) {
                case "1":
                    queueName = "soccer"; 
                    break;
                case "2":
                    queueName = "volley"; 
                    break;
                case "3":
                    queueName = "both"; 
                    break;
                default:
                    System.out.println("\nERROR: This option is not included\n");
                    continue;
            }

            break;
        }

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(message + "\n");
        };

        if(queueName == "both") {
            for(String qName : QUEUES_NAME) {
                channel.queueDeclare(qName, false, false, false, null);
                channel.queueBind(qName, EXCHANGE_NAME, qName);
                channel.basicConsume(qName, true, deliverCallback, consumerTag -> { });
            }
        } else {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, queueName);
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        }

        System.out.println("\nAguardando tweets...\n");
    }
}

