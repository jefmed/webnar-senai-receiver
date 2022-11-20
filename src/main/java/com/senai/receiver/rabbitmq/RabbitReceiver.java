package com.senai.receiver.rabbitmq;

import com.senai.receiver.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitReceiver {

    @RabbitListener(queues = RabbitConfig.queueName) // <-- FAZ A LIGAÇÃO COM UMA FILA
    public void consumer(Student student) {
        log.info("Bem vindo ao Mini Curso sobre Microservicos e Comunicacao Assincrona: " + student.getName());
    }
}
