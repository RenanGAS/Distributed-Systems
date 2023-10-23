outputJava:
	protoc --java_out=./src/main/java/ ./src/main/java/project/Movie.proto

outputKotlin:
	protoc --kotlin_out=./src/main/java/ ./src/main/java/project/Movie.proto

clearOutputs:
	rm -rf ./src/main/java/project/javaOut
	rm -rf ./src/main/java/project/kotlinOut

compile:
	mvn clean compile

server:
	mvn exec:java -Dexec.mainClass="project.TCPServer"

client:
	mvn exec:java -Dexec.mainClass="project.TCPClient"

ping:
	mvn exec:java -Dexec.mainClass="project.TesteConexao"

