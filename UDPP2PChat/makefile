compile:
	export CLASSPATH=./resources/emoji-java-5.1.1.jar:./resources/json-20230618.jar:. && javac src/*.java src/tools/*.java

initPeer:
	export CLASSPATH=./resources/emoji-java-5.1.1.jar:./resources/json-20230618.jar:. && java src.UDPPeer 6666

initServer:
	java src.UDPServer 6668
