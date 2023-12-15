install:
	mvn clean install

compile:
	mvn clean compile

producer:
	mvn exec:java -Dexec.mainClass="project.ProducerQueue"

classifier:
	mvn exec:java -Dexec.mainClass="project.Classifier"

subscriber:
	mvn exec:java -Dexec.mainClass="project.Subscriber"

subscriberGo:
	go run ./src/main/java/project/SubscriberGo.go

javadoc:
	mvn javadoc:javadoc

dokka:
	mvn dokka:javadoc
