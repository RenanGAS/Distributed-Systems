package project;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Consome tweets para classificação e publicação nos tópicos correspondentes.
 *
 */
public class Classifier {
    private final static String QUEUE_NAME = "tweets";
    private static final String EXCHANGE_NAME = "filter";

    private static String[] soccerKeywords = {"football","soccer", "team","defense","hat-trick","player","goal","game",
        "score","ball","premier","midfield","kick","goalkeeper","championship","goal-kick",
        "dribble", "referee", "penalty", "fifa", "serie", "formation", "cup", "field"};

    private static String[] volleyKeywords = {"volleyball","volley","match","net","antenna","jump","rally","serve",
        "point","rotation","setter","spike", "underhand", "libero", "blockers", "ace",
        "hut","shank","game", "player", "score", "ball", "field", "team"};

    /**
     * Verifica existência de um termo num tweet por regex
     *
     * @param tweet Texto do tweet 
     * @param term Termo procurado
     * @return Resultado como valor booleano
     */
    static boolean isTermInTweet(String tweet, String term) {
        Pattern pattern = Pattern.compile(term, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(tweet);
		
		if (matcher.find()) {
            return true;
		}

        return false;
    }

    /**
     * Conta ocorrências de termos num tweet, referentes a um tópico
     *
     * @param listTerms Lista de termos do tópico
     * @param mainTerms Lista de termos obrigatórios
     * @param divisionFactor Determina o número de threads para o processo de classificação dos tweets: listTerms.lenght / divisionFactor
     */
    static int getTermsOccurrence(String tweet, List<String> listTerms, List<String> mainTerms, int divisionFactor) throws InterruptedException {
        int result = 0;

        boolean oneMainTerm = false;

        for (String mainT : mainTerms) {
            if (isTermInTweet(tweet, mainT)) {
                oneMainTerm = true; 
                break;
            } 
        }

        if (!oneMainTerm) {
            return result; 
        }

        // 8 keywords para cada thread
        List<Find> findThreads = new ArrayList<>();

        int numIt = listTerms.size() / divisionFactor;

        for (int i = 1; i <= numIt; i++) {
            Find finder = new Find(tweet, listTerms,  (i * divisionFactor) - divisionFactor, (i * divisionFactor) - 1); 
            finder.start();
            findThreads.add(finder);
        }

        for (Find finder : findThreads) {
            finder.join(); 

            result += finder.getCount();
        }

        return result;
    }

    /**
     * Define um tópico para o tweet
     *
     * @param tweet Texto do tweet
     * @return Tópico do tweet
     */
    static String assignTopic(String tweet) throws InterruptedException {
        List<String> listSoccerkw = Arrays.asList(soccerKeywords);

        List<String> listMainTermsSoccer = Arrays.asList("football", "soccer");

        int resultSoccer = getTermsOccurrence(tweet, listSoccerkw, listMainTermsSoccer, 8);


        List<String> listVolleykw = Arrays.asList(volleyKeywords);

        List<String> listMainTermsVolley = Arrays.asList("volleyball", "volley");

        int resultVolley = getTermsOccurrence(tweet, listVolleykw, listMainTermsVolley, 8);

        if (resultSoccer >= 1) {
            if (resultVolley >= 1) {
                if (resultSoccer > resultVolley) {
                    return "soccer"; 
                } else {
                    return "volley";
                }
            }

            return "soccer";
        } else if (resultVolley >= 1) {
            return "volley"; 
        }

        return null;
    }

    public static void main(String args[]) throws IOException,TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueDeclare("soccer", false, false, false, null);
        channel.queueDeclare("volley", false, false, false, null);
        System.out.println("Consumindo e classificando tweets...\n");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            try {
                String topic = assignTopic(message);

                if (topic != null) {
                    // topic == routing key
                    channel.basicPublish(EXCHANGE_NAME, topic, null, message.getBytes());
                }
            } catch (InterruptedException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}

