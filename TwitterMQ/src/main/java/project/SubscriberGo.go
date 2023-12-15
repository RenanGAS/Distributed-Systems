package main

import (
    "log"
    "fmt"
    amqp "github.com/rabbitmq/amqp091-go"
)

func failOnError(err error, msg string) {
    if err != nil {
        log.Panicf("%s: %s", msg, err)
    }
}

// Implementa consumidor para os tópicos "soccer" e "volley"
func main() {
    conn, err := amqp.Dial("amqp://guest:guest@localhost:5672/")
    failOnError(err, "Failed to connect to RabbitMQ")
    defer conn.Close()

    ch, err := conn.Channel()
    failOnError(err, "Failed to open a channel")
    defer ch.Close()

    err = ch.ExchangeDeclare("filter","direct",false,false,false,false,nil)
    failOnError(err, "Failed to declare an exchange")

    var queueName string;

    for {
        log.Printf("Escolha uma das opções de inscrição:\n\n")
        log.Printf("1 - soccer\n")
        log.Printf("2 - volley\n")
        log.Printf("3 - soccer e volley\n")

        fmt.Scan(&queueName)

        switch queueName {
        case "1":
            queueName = "soccer"
        case "2":
            queueName = "volley"
        case "3":
            queueName = "both"
        default:
            log.Printf("\nERROR: Invalid choice\n")
            continue;
        }

        break;
    }

    queueOptions := []string{"soccer", "volley"}

    if queueName == "both" {
        for _, qOption := range queueOptions {
            //q, err := ch.QueueDeclare(qOption,false,false,true,false,nil)
            //failOnError(err, "Failed to declare a queue")

            err = ch.QueueBind(qOption,qOption,"filter",false,nil)
            failOnError(err, "Failed to bind a queue")

            msgs, err := ch.Consume(qOption,"",true,false,false,false,nil)
            failOnError(err, "Failed to register a consumer")

            go func() {
                for d := range msgs {
                    log.Printf("%s\n", d.Body)
                }
            }()
        }
    } else {
        //q, err := ch.QueueDeclare(queueName,false,false,true,false,nil)
        //failOnError(err, "Failed to declare a queue")

        err = ch.QueueBind(queueName,queueName,"filter",false,nil)
        failOnError(err, "Failed to bind a queue")

        msgs, err := ch.Consume(queueName,"",true,false,false,false,nil)
        failOnError(err, "Failed to register a consumer")

        go func() {
            for d := range msgs {
                log.Printf("%s\n", d.Body)
            }
        }()
    }

    var forever chan struct{}

    log.Printf("Aguardando tweets....\n")
    <-forever
}

