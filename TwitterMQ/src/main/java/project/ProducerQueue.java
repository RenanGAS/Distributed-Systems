package project;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Lê um arquivo "csv" com os tweets.
 *
 */
public class ProducerQueue {
    private final static String QUEUE_NAME = "tweets";

    public static void main( String[] args ) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            try (BufferedReader br = new BufferedReader(new FileReader("tweets.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.isBlank()) {
                        String[] values = line.split(",");

                        String tweet;

                        if (values.length > 1) {
                            tweet = values[0] + ": " + values[1];
                        } else {
                            tweet = values[0];
                        }

                        channel.basicPublish("", QUEUE_NAME, null, tweet.getBytes());
                    }
                }
            }

                }
    }
}

