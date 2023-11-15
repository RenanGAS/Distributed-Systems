outputJava:
	protoc --java_out=./src/main/java/ ./src/main/java/project/Movie.proto

outputKotlin:
	protoc --kotlin_out=./src/main/kotlin/ ./src/main/kotlin/project/Movie.proto

clearOutputs:
	rm -rf ./src/main/java/project/javaOut
	rm -rf ./src/main/kotlin/project/kotlinOut

install:
	mvn clean install

compile:
	mvn clean compile

server:
	mvn exec:java -Dexec.mainClass="project.TCPServer"

client:
	mvn exec:java -Dexec.mainClass="project.TCPClientKt"

javadoc:
	mvn javadoc:javadoc

dokka:
	mvn dokka:javadoc
