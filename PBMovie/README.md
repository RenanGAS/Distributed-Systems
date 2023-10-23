Criar um canal TCP em loop para abrir conexões
Em cada conexão estabelecer comunicação com o mongo?
Tentar abrir uma conexão para todas as conexões segunintes?

Prestar atenção em como fazer a comunicação TCP:
- Tem que definir o formato da mensagem e como vamos lê-la (tamanho
        e conteúdo de cada parte)

Tarefas:
- CRUD: Criação, Leitura, Edição e Exclusão de Filmes
- Listagem de filmes por ator
- Listagem de filmes por categoria

Workflows de cada operação:
- create:
    - Cliente manda requisição para o Servidor: "1":tamanho_PB_movie:PB_movie
    - Servidor manda requisição para o MongoDB: insert(movie)  
    - MongoDB manda resposta para Servidor: sucesso() | falha()
    - Servidor manda resposta para o Cliente: sucesso() | falha()

- read:
    - Cliente manda requisição para o Servidor: "2":tamanho_nome:nome_filme
    - Servidor manda requisição para o MongoDB: searchByName(nome_filme)
    - MongoDB manda resposta para o Servidor: movie_docs | falha()
    - Servidor manda resposta para o Cliente: movie_PB | falha()

- update:
    - Cliente manda requisição para o Servidor: "2":tamanho_nome:nome_filme
    - Servidor manda requisição para o MongoDB: searchByName(nome_filme)
    - MongoDB manda resposta para o Servidor: movie_docs | falha()
    - Servidor manda resposta para o Cliente: movie_PB | falha()
    - Cliente manda requisição para o Servidor: "3":tamanho_PB_movie_editado:PB_movie_editado
    - Servidor manda requisição para o MongoDB: edit(movie_docs)
    - MongoDB manda resposta para o Servidor: sucesso() | falha()
    - Servidor manda resposta para o Cliente: sucesso() | falha()

- delete:
    - Cliente manda requisição para o Servidor: "2":tamanho_nome:nome_filme
    - Servidor manda requisição para o MongoDB: searchByName(nome_filme)
    - MongoDB manda resposta para o Servidor: movie_docs | falha()
    - Servidor manda resposta para o Cliente: movie_PB | falha()
    - Cliente manda requisição para o Servidor: "4":tamanho_nome:nome_movie
    - Servidor manda requisição para o MongoDB: deleteByName(nome_movie)
    - MongoDB manda resposta para o Servidor: sucesso() | falha()
    - Servidor manda resposta para o Cliente: sucesso() | falha()


- listByActor:
    - Cliente manda requisição para o Servidor: "5":tamanho_nome_ator:nome_ator
    - Servidor manda requisição para o MongoDB: getMoviesByActorName(nome_ator)
    - MongoDB manda resposta para o Servidor: list_movies_docs | falha()
    - Servidor manda resposta para o Cliente: list_movies_PB | falha()

- listByCategory:
    - Cliente manda requisição para o Servidor: "6":tamanho_nome_categoria:nome_categoria
    - Servidor manda requisição para o MongoDB: getMoviesByCategoryName(nome_categoria)
    - MongoDB manda resposta para o Servidor: list_movies_docs | falha()
    - Servidor manda resposta para o Cliente: list_movies_PB | falha()

- Formato de responses:
    - Sucesso: 0:corpo_sucesso
    - Falha: 1:corpo_falha

Ordem de prioridade de programação:
- Conexão TCP socket para Client.java e Server.java funcional -> CHECK
- Definição das classes necessárias para comunicação TCP:
    - Classe para criar o stream de dados de uma operação (com métodos para cada tipo)
    - Classe para fazer o parse dessa stream de dados (com métodos para cada tipo)   
- Classe para conexão com o MongoDB e conversão dos JSONs para objetos protoBuffer e vice-versa

        StringWriter sw = new StringWriter();
        JsonWriter  writer = new JsonWriter(sw);

        writer.writeString("title", title);
        writer.writeString("year", year);
        writer.writeString("released", released);
        writer.writeString("poster", poster);
        writer.writeString("plot", plot);
        writer.writeString("fullplot", fullplot);

        writer.writeStartArray("directors");
        for (int i = 0; i < directorsList.size(); i++) {
            writer.writeString(directorsList.get(i)); 
        } 
        writer.writeEndArray();

        writer.writeStartArray("cast");
        for (int i = 0; i < castList.size(); i++) {
            writer.writeString(castList.get(i)); 
        } 
        writer.writeEndArray();

        writer.writeStartArray("countries");
        for (int i = 0; i < countriesList.size(); i++) {
            writer.writeString(countriesList.get(i)); 
        } 
        writer.writeEndArray();

        writer.writeStartArray("genres");
        for (int i = 0; i < genresList.size(); i++) {
            writer.writeString(genresList.get(i)); 
        } 
        writer.writeEndArray();

        //JsonObject obj = new JsonParser().parse(sw.toString()).getAsJsonObject();  
        JsonObject jsonMovie = new JsonObject(writer.toString());

        writer.close();

        return jsonMovie;

Tentar fazer uma operação de leitura pra ver se a conexão tá de boa
