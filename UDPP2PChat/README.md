Para compilação:

- `make compile`

Para execução:

- `make initPeer1`
- `make initPeer2`

- ou

- `make initPeer1`
- `make initServer`

Exemplo de uso:

- `make initPeer1`
	- renangas
	- y
	- 127.0.0.1
	- 6667
	- 1:"Salve!"
	- 2:"swag"
	- 3:"https://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html"
	
- `make initPeer2`
	- sagnaner
	- y
	- 127.0.0.1
	- 6666
	- 1:"Salve!"
	- 2:"happy ok"
	
- ou

- `make initPeer1`
	- renangas
	- y
	- 127.0.0.1
	- 6668
	- 4:"Salve servidor!"
	- 5:"textFile.txt"
	- 5:"hikaru.png"
	
- `make initServer` (Caso não esteja respondendo requisições, execute de novo esta instância)
