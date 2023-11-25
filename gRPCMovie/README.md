## Funcionalidades:

- CRUD de filmes

- Listagem de filmes por ator e gênero

## Recursos:

- gRPC

- MongoDB API

- SLF4J e LogBack

- Javadoc e Dokka

## Instruções:

### Para instalação de dependências:

- `make install`

### Para compilação:

- `make compile`

### Para execução:

- `make server` | `make client`

- Operações disponíveis (Cliente): create, read, update, delete, listByActor, listByGenre

### Para geração de documentação para java:

- `make javadoc`

- Disponível em `/target/site/apidocs`

### Para geração de documentação para kotlin:

- `make dokka`

- Disponível em `/target/dokkaJavadoc`

## Anotações

- Fazer .proto com as operações que o servidor disponibilizará para o cliente (CRUD e listagens) usando o objeto proto Movie

- Gerar arquivos proto com java como fez-se antes. Se funcionasse o do Kotlin, nos arquivos kt utilizaria os arquivos gerados para Kotlin do protobuf

- A diferença dessa atividade é que não precisamos montar as requisições, elas são definidas no arquivo proto com a integração do gRPC no Protocol Buffers

- Vamos fazer chamadas remotas e não requisições e respostas

- O código vai ficar bem simplificado

- Não precisa de SendThread, ReceiveThread, Parsers, Formaters

 - O que tá em CrudMovie vai ser a implementação da interface de serviço

- Colocar código de sucesso/erro, ver comentários, gerar docs, e se tiver tempo, criar threads pra não compartilhar logs 

