package project;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Thread para contagem de palavras-chave num tweet.
 *
 */
public class Find extends Thread {
    String tweet;
    List<String> keywords;
    int startIndex;
    int endIndex;
    volatile int count;

    public Find(String tweet, List<String> keywords, int startIndex, int endIndex) {
        this.tweet = tweet;
        this.keywords = keywords;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    /**
     * Verifica a existência de um termo num tweet
     * @param tweet Texto do tweet
     * @param term Termo procurado
     * @return Resultado como valor booleano
     */
    boolean isTermInTweet(String tweet, String term) {
        Pattern pattern = Pattern.compile(term, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(tweet);
		
		if (matcher.find()) {
            return true;
		}

        return false;
    }

    /**
     * Retorna a contagem de termos encontrados no tweet
     * @return Número de termos encontrados
     */
    int getCount() {
        return this.count;
    }

    @Override
    public void run() {
        for (int i=this.startIndex; i<=this.endIndex; i++) {
           if (isTermInTweet(this.tweet, this.keywords.get(i))) {
               this.count++; 
           } 
        }
    }
}

