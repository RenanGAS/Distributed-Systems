package project.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import project.javaOut.Movie;
import project.javaOut.MovieOrBuilder;
import project.javaOut.MovieWrapper;

import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.MongoException;
import java.util.Arrays;
import java.util.Date;
import java.time.Instant;
import org.bson.types.ObjectId;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
import java.util.Map;
import org.bson.BsonValue;
import com.mongodb.client.model.Filters;

/**
 * CrudMovie: reponsável pela construção das requisições para o MongoDB
 */
public class CrudMovie {

    /**
     * Faz requisição de insert para o MongoDB
     *
     * @param movie Objeto protobuff do filme
     * @return String com mensagem de sucesso ou erro
     */
    public String createMovie(Movie movie) throws MongoException {
        String title = movie.getTitle();

        String year = String.valueOf(movie.getYear());

        String released = movie.getReleased();

        String poster = movie.getPoster();

        String plot = movie.getPlot();           

        String fullplot = movie.getFullplot();           

        List<String> directorsList = new ArrayList<>();
        int numDirectors = movie.getDirectorsCount();

        for (int i = 0; i < numDirectors; i++) {
            directorsList.add(movie.getDirectors(i));    
        }

        List<String> castList = new ArrayList<>();
        int numCast = movie.getCastCount();

        for (int i = 0; i < numCast; i++) {
            castList.add(movie.getCast(i));    
        }

        List<String> countriesList = new ArrayList<>();
        int numCountries = movie.getContriesCount();

        for (int i = 0; i < numCountries; i++) {
            countriesList.add(movie.getContries(i));    
        }

        List<String> genresList = new ArrayList<>();
        int numGenres = movie.getGenresCount();

        for (int i = 0; i < numGenres; i++) {
            genresList.add(movie.getGenres(i));    
        }

        String uri = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";
        
        try {
            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("plot", plot) 
                    .append("genres", genresList) 
                    .append("cast", castList) 
                    .append("title", title) 
                    .append("fullplot", fullplot)
                    .append("released", released)
                    .append("directors", directorsList)
                    .append("year", year)
                    .append("countries", countriesList));

            return "Movie created successfully";
        } catch (MongoException e) {
            return e.getMessage();
        }
    }

    /**
     * Procura um filme pelo seu nome
     *
     * @param movieName Nome do filme
     * @return Objeto movie
     */
    public Movie retrieveMovie(String movieName) throws MongoException {
        String connectionString = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            Document document = collection.find(eq("title", movieName)).first();

            Movie movie;

            try {
                movie = documentToMovie(document);
            } catch (MongoException me) {
                throw new MongoException(me.getMessage());
            } catch (NullPointerException npe) {
                throw new MongoException(npe.getMessage());
            }

            return movie;
        } 
    }

    /**
     * Edita um filme 
     *
     * @param movieName Nome do filme
     * @return Objeto movie
     */
    public String editMovie(JsonObject movieJson) {
        String connectionString = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            BsonDocument movieBson = movieJson.toBsonDocument();

            Document query = new Document().append("title", movieBson.get("title"));

            List<Bson> updates = new ArrayList<>();

            for (Map.Entry<String, BsonValue> attribute: movieBson.entrySet()) {
                updates.add(Updates.set(attribute.getKey(), attribute.getValue()));
            }

            Bson combinedUpdates = Updates.combine(updates);
            
            try {
                UpdateResult result = collection.updateOne(query, combinedUpdates);

                // Prints the number of updated documents and the upserted document ID, if an upsert was performed
                System.out.println("Modified document count: " + result.getModifiedCount());
                System.out.println("Upserted id: " + result.getUpsertedId());
            } catch (MongoException me) {
                return me.getMessage();
            }
        } catch (MongoException me) {
            return me.getMessage();
        }
        
        return "Movie edited successfully";
    }

    /**
     * Transforma um Document do MongoDB em Movie ProtoBuffer
     *
     * @param document Documento MongoDB
     * @return Movie ProtoBuffer
     */
    public Movie documentToMovie(Document document) {
        Movie.Builder movie = Movie.newBuilder();

        movie.setTitle(document.get("title").toString());

        int year = Integer.valueOf(document.get("year").toString()); 
        movie.setYear(year);

        movie.setReleased(document.get("released").toString());

        movie.setPoster(document.get("poster").toString());

        movie.setPlot(document.get("plot").toString());           

        movie.setFullplot(document.get("fullplot").toString());           

        List<String> listDirectors = document.getList("directors", String.class);

        for(String director : listDirectors) {
            movie.addDirectors(director);
        }

        List<String> listActors = document.getList("cast", String.class);

        for(String actor : listActors) {
            movie.addCast(actor);
        }

        List<String> listCountries = document.getList("countries", String.class);

        for(String country : listCountries) {
            movie.addContries(country);
        }

        List<String> listGenres = document.getList("genres", String.class);

        for(String genre : listGenres) {
            movie.addGenres(genre);
        }

        return movie.build();
    }

    /**
     * Exclui um filme pelo seu nome
     *
     * @param movieName Nome do filme
     * @return Mensagem de sucesso ou falha
     */
    public String deleteMovie(String movieName) throws MongoException {
        String connectionString = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");
            
            collection.deleteOne(eq("title", movieName));

            return "Movie deleted successfuly";
        }
    }

    /**
     * Lista filmes de um ator pelo seu nome
     *
     * @param actorName Nome do ator
     * @return Lista de movies 
     */
    public List<Movie> listByActor(String actorName) throws MongoException {
        String connectionString = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        List<Movie> listMovies = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            Bson filter = Filters.in("cast", actorName);
            FindIterable<Document> movies = collection.find(filter).limit(10);

            try {
                for (Document docMovie : movies) {
                    Movie movie = documentToMovie(docMovie); 
                    listMovies.add(movie);
                }
            } catch (MongoException me) {
               throw new MongoException(me.getMessage()); 
            } catch (NullPointerException npe) {
               throw new MongoException(npe.getMessage());
            }
       }

       return listMovies;
    }

    /**
     * Pinga o servidor MongoDB
     */
    public void ping() {
        String connectionString = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();

        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("sample_mflix");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

}
