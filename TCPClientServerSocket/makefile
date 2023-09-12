initServer:
	export CLASSPATH=./resources/slf4j-api-2.0.9.jar:./resources/logback-classic-1.4.11.jar:./resources/logback-core-1.4.11.jar:. && java src.TCPServer

initClient:
	export CLASSPATH=./resources/slf4j-api-2.0.9.jar:./resources/logback-classic-1.4.11.jar:./resources/logback-core-1.4.11.jar:. && java src.TCPClient

compile:
	export CLASSPATH=./resources/slf4j-api-2.0.9.jar:./resources/logback-classic-1.4.11.jar:./resources/logback-core-1.4.11.jar:. && javac src/*.java src/tools/*.java

clear:
	rm -f src/*.class src/tools/*.class

resetLogs:
	truncate -s 0 serverLogs.log
