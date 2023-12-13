package project;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import com.twitter.clientlib.ApiClient;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.Configuration;
import com.twitter.clientlib.auth.*;
import com.twitter.clientlib.model.*;
import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;

import com.twitter.clientlib.api.TweetsApi;
import java.io.InputStream;
import com.google.common.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.time.OffsetDateTime;

/**
 * Conecta-se à API do Twitter para transmissão de tweets para fila.
 *
 */
public class Publisher {
    private final static String QUEUE_NAME = "hello";

    public static void main( String[] args ) throws Exception {
        // Configure HTTP bearer authorization:
        TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(System.getenv("TWITTER_BEARER_TOKEN"));
        TwitterApi apiInstance = new TwitterApi(credentials);

        // Set the params values
        Integer backfillMinutes = 56; // Integer | The number of minutes of backfill requested.
        Integer partition = 56; // Integer | The partition number.
        OffsetDateTime startTime = OffsetDateTime.parse("2021-02-14T18:40:40.000Z"); // OffsetDateTime | YYYY-MM-DDTHH:mm:ssZ. The earliest UTC timestamp to which the Tweets will be provided.
        OffsetDateTime endTime = OffsetDateTime.parse("2021-02-14T18:40:40.000Z"); // OffsetDateTime | YYYY-MM-DDTHH:mm:ssZ. The latest UTC timestamp to which the Tweets will be provided.
        Set<String> tweetFields = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of Tweet fields to display.
        Set<String> expansions = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of fields to expand.
        Set<String> mediaFields = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of Media fields to display.
        Set<String> pollFields = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of Poll fields to display.
        Set<String> userFields = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of User fields to display.
        Set<String> placeFields = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of Place fields to display.
        try {
            InputStream result = apiInstance.tweets().getTweetsSample10Stream(partition)
                .backfillMinutes(backfillMinutes)
                .startTime(startTime)
                .endTime(endTime)
                .tweetFields(tweetFields)
                .expansions(expansions)
                .mediaFields(mediaFields)
                .pollFields(pollFields)
                .userFields(userFields)
                .placeFields(placeFields)
                .execute();
            try{
                Gson json = new Gson();
                Type localVarReturnType = new TypeToken<Get2TweetsSample10StreamResponse>(){}.getType();
                BufferedReader reader = new BufferedReader(new InputStreamReader(result));
                String line = reader.readLine();
                while (line != null) {
                    if(line.isEmpty()) {
                        System.out.println("==> Empty line");
                        line = reader.readLine();
                        continue;
                    }
                    Object jsonObject = json.fromJson(line, localVarReturnType);
                    System.out.println(jsonObject != null ? jsonObject.toString() : "Null object");
                    line = reader.readLine();
                }
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        } catch (ApiException e) {
            System.err.println("Exception when calling TweetsApi#getTweetsSample10Stream");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

//        ConnectionFactory factory = new ConnectionFactory();
  //      factory.setHost("localhost");
    //    try (Connection connection = factory.newConnection();
      //          Channel channel = connection.createChannel()) {

        //    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
          //  String message = "Hello World!";
           // channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
           // System.out.println(" [x] Sent '" + message + "'");
             //   }
    }
}
