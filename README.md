# Distributed-Systems

Neste repositório estão implementados projetos feitos na matéria de Sistemas Distribuídos. Dentre as principais tecnologias utilizadas estão: **Protocol Buffer,  RabbitMQ, gRPC, e TCP/UDP Socket**.

- **PBMovie**: aplicação para **CRUD de filmes** integrado com a **API do MongoDB**. Utiliza uma **arquitetura cliente-servidor** com **conexão TCP** e troca de mensagens com **objetos ProtoBuff**.
- **TCPClientServerSocket**: aplicação para **acesso remoto** a um **servidor de arquivos**. Utiliza uma **arquitetura cliente-servidor** com **conexão TCP**, com sistema de **login** e comandos para **criação de arquivos, listagem de diretórios/arquivos e navegação** pelo sistema.
- **TwitterMQ**: aplicação para **classificação de tweets** entre os **tópicos** Vôlei e Futebol. Utiliza uma **base de dados de tweets** retirada da plataforma **Kaggle**, e **RabbitMQ** para criação de uma **fila** (utilizada pelo classificador para consumir a base de dados) e criação de **canais publisher-subscriber** (utilizados pelo classificador para enviar os tweets a seus respectivos tópicos e pelos usuários incritos em cada tópico para receberem os tweets).
- **UDPP2PChat**: aplicação de **chat** e **servidor de arquivos**. Utiliza uma **arquitetura peer-to-peer** com **conexão UDP** para o **chat**, e **arquitetura cliente-servidor** com **conexão UDP** para o **servidor de arquivos**.
- **gRPCMovie**: aplicação para **CRUD de filmes** como em **PBMovie**, mas utilizando a tecnologia **gRPC** para troca de mensagens por **RPC (Remote Procedure Call)**. OBS: menos trabalhoso para implementação do protocolo de comunicação **cliente-servidor**.
