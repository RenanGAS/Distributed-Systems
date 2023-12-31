package project;

import io.grpc.stub.StreamObserver;
import project.javaOut.CrudMovieGrpc;
import project.javaOut.Movie;
import project.javaOut.MovieList;
import project.javaOut.ResponseMsg;
import project.javaOut.SearchParam;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import java.util.List;
import java.util.ArrayList;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.model.Filters;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import java.util.Map;
import com.google.protobuf.Descriptors;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementa interface de serviço CrudMovie em Movie.proto 
 */

public class CrudMovieImpl extends CrudMovieGrpc.CrudMovieImplBase {

	static Logger logger = LoggerFactory.getLogger(CrudMovieImpl.class);

    /**
     * Cria um Movie no MongoDB
     *
     * @param movie Objeto Movie
     * @return String com mensagem de sucesso ou erro
     */
    @Override
    public void create(Movie movie, StreamObserver<ResponseMsg> responseObserver) {
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
        int numCountries = movie.getCountriesCount();

        for (int i = 0; i < numCountries; i++) {
            countriesList.add(movie.getCountries(i));    
        }

        List<String> genresList = new ArrayList<>();
        int numGenres = movie.getGenresCount();

        for (int i = 0; i < numGenres; i++) {
            genresList.add(movie.getGenres(i));    
        }

        ResponseMsg response = null;

        try {
            String uri = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("plot", plot) 
                    .append("poster", poster) 
                    .append("genres", genresList) 
                    .append("cast", castList) 
                    .append("title", title) 
                    .append("fullplot", fullplot)
                    .append("released", released)
                    .append("directors", directorsList)
                    .append("year", year)
                    .append("countries", countriesList));

            mongoClient.close();

            logger.info("Movie created successfully");
            response = ResponseMsg.newBuilder().setMsg("SUCCESS: Movie created successfully").build();
        } catch (NullPointerException | MongoException e) {
            logger.warn("Movie creation failed");
            response = ResponseMsg.newBuilder().setMsg("ERROR: " + e.getMessage()).build();
        } 

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Recupera um Movie do MongoDB pelo seu nome
     *
     * @param movieName Nome do filme
     * @return Objeto movie
     */
    @Override
    public void read(SearchParam param, StreamObserver<Movie> responseObserver) {
        String connectionString = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        Movie movie = null;

        try {
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            Document document = collection.find(Filters.eq("title", param.getParam())).first();

            mongoClient.close();

            movie = documentToMovie(document);

            logger.info("Movie retrieved successfully");
            responseObserver.onNext(movie);
            responseObserver.onCompleted();
        } catch (NullPointerException | MongoException me) {
            logger.warn("Movie retrieve failed");
            responseObserver.onNext(movie);
            responseObserver.onCompleted();
        } 
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

        if (document.get("year") != null) {
            int year = Integer.valueOf(document.get("year").toString()); 
            movie.setYear(year);
        }

        if (document.get("released") != null) {
            movie.setReleased(document.get("released").toString());
        }

        if (document.get("poster") != null) {
            movie.setPoster(document.get("poster").toString());
        }

        if (document.get("plot") != null) {
            movie.setPlot(document.get("plot").toString());           
        }

        if (document.get("fullplot") != null) {
            movie.setFullplot(document.get("fullplot").toString());           
        }

        if (document.get("directors") != null) {
            List<String> listDirectors = document.getList("directors", String.class);

            for(String director : listDirectors) {
                movie.addDirectors(director);
            }
        }

        if (document.get("cast") != null) {
            List<String> listActors = document.getList("cast", String.class);

            for(String actor : listActors) {
                movie.addCast(actor);
            }
        }

        if (document.get("countries") != null) {
            List<String> listCountries = document.getList("countries", String.class);

            for(String country : listCountries) {
                movie.addCountries(country);
            }
        }

        if (document.get("genres") != null) {
            List<String> listGenres = document.getList("genres", String.class);

            for(String genre : listGenres) {
                movie.addGenres(genre);
            }
        }

        return movie.build();
    }

    /**
     * Edita um Movie do MongoDB 
     *
     * @param movieName Nome do Movie
     * @return Objeto Movie
     */
    @Override
    public void update(Movie movie, StreamObserver<Movie> responseObserver) {
        String connectionString = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        Document result = null;

        Movie movieUpdated = null;

        try {
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            List<Bson> updates = new ArrayList<>();

            for (Map.Entry<Descriptors.FieldDescriptor, Object> attribute: movie.getAllFields().entrySet()) {
                updates.add(Updates.set(attribute.getKey().getName(), attribute.getValue()));
            }

            Bson combinedUpdates = Updates.combine(updates);

            result = collection.findOneAndUpdate(Filters.eq("title", movie.getTitle()), combinedUpdates, new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));

            mongoClient.close();

            movieUpdated = documentToMovie(result);

            logger.info("Movie updated successfully");
            responseObserver.onNext(movieUpdated);
            responseObserver.onCompleted();
        } catch (NullPointerException | MongoException ex) {
            logger.warn("Movie update failed");
            responseObserver.onNext(movieUpdated);
            responseObserver.onCompleted();
        }
    }

    /**
     * Exclui um Movie pelo seu nome
     *
     * @param movieName Nome do Movie
     * @return Mensagem de sucesso ou falha
     */
    @Override
    public void delete(SearchParam param, StreamObserver<ResponseMsg> responseObserver) {
        String connectionString = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        ResponseMsg response = null;

        try {
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            collection.deleteOne(Filters.eq("title", param.getParam()));

            mongoClient.close();

            logger.info("Movie deleted successfully");
            response = ResponseMsg.newBuilder().setMsg("SUCCESS: Movie deleted successfully").build(); 
        } catch (NullPointerException | MongoException ex) {
            logger.warn("Movie delete failed");
            response = ResponseMsg.newBuilder().setMsg("ERROR: " + ex.getMessage()).build(); 
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Lista Movies de um ator pelo seu nome
     *
     * @param actorName Nome do ator
     * @return Lista de Movies 
     */
    public void listByActor(SearchParam param, StreamObserver<MovieList> responseObserver) {
        String connectionString = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MovieList listOfMovies = null;

        try {
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            Bson filter = Filters.in("cast", param.getParam());
            FindIterable<Document> movies = collection.find(filter).limit(10);

            if (movies.first() == null) {
                logger.warn("Movies listed by actor failed");
                responseObserver.onNext(listOfMovies);
                responseObserver.onCompleted();
                return;
            }

            MovieList.Builder listMovies = MovieList.newBuilder();

            for (Document docMovie : movies) {
                Movie movie = documentToMovie(docMovie); 
                listMovies.addMovies(movie);
            }

            listOfMovies = listMovies.build();

            mongoClient.close();

            logger.info("Movies listed by actor successfully");
            responseObserver.onNext(listOfMovies);
            responseObserver.onCompleted();
        } catch (NullPointerException | MongoException ex) {
            logger.warn("Movies listed by actor failed");
            responseObserver.onNext(listOfMovies);
            responseObserver.onCompleted();
        }
    }

    /**
     * Lista filmes de um gênero pelo seu nome
     *
     * @param genreName Nome do gênero
     * @return Lista de gêneros 
     */
    public void listByGenre(SearchParam param, StreamObserver<MovieList> responseObserver) {
        String connectionString = "mongodb+srv://renansakaoki:123@mflix.hokkmkd.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MovieList listOfMovies = null;
        
        try {
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            Bson filter = Filters.in("genres", param.getParam());
            FindIterable<Document> movies = collection.find(filter).limit(10);

            if (movies.first() == null) {
                logger.warn("Movies listed by genre failed");
                responseObserver.onNext(listOfMovies);
                responseObserver.onCompleted();
                return;
            }

            MovieList.Builder listMovies = MovieList.newBuilder();

            for (Document docMovie : movies) {
                Movie movie = documentToMovie(docMovie); 
                listMovies.addMovies(movie);
            }

            listOfMovies = listMovies.build();

            mongoClient.close();

            logger.info("Movies listed by genre successfully");
            responseObserver.onNext(listOfMovies);
            responseObserver.onCompleted();
        } catch (NullPointerException | MongoException ex) {
            logger.warn("Movies listed by genre failed");
            responseObserver.onNext(listOfMovies);
            responseObserver.onCompleted();
        }
    }
}

