package com.senai.receiver.rabbitmq;

import com.senai.receiver.domain.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {

    @RabbitListener(queues = RabbitConfig.queueName) // <-- FAZ A LIGAÇÃO COM UMA FILA
    public void consumer(Student student) {
        System.out.println("Bem vindo ao Mini Curso sobre Microservicos e Comunicacao Assincrona: " + student.getName());
    }
}
