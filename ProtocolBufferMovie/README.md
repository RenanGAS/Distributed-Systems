- With protocol buffers, you write a .proto description of the data structure you wish to store

- then, protocol buffer compiler creates a class that implements automatic encoding and parsing of the protocol buffer data with an efficient binary format

- faz o que fizemos com os pacotes: provê um empacotamento (PacketData) (Transformação para bytes) e desempacotamento (ParsePacket) (Transformação para dados, cria getters e setters) para a estrutura de dados que definimos em .proto, ao compilarmos ela

- protoc --java_out=. ./src/java/Movie.proto