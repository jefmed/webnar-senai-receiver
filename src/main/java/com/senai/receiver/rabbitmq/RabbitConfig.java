package com.senai.receiver.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;
    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;
//    @Value("${spring.rabbitmq.virtual_host}")
//    private String rabbitVHost;
    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;
    @Value("${spring.rabbitmq.port}")
    private Integer rabbitPort;


    static final String exchange = "my-exchange";
    static final String routingkey = "mini-curso-senai-rk";
    static final String queueName = "mini-curso-senai";
//    static final String dlq= "my-dead-letter-queue";
//    static final String dlx = "my-dead-letter-exchange";
//    static final String dlk = "my-dead-letter-routing-key";



    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(rabbitHost, rabbitPort) {{
            setUsername(rabbitUsername);
            setPassword(rabbitPassword);
//            setVirtualHost(rabbitVHost);
        }};
    }



    @Bean
    Queue queue() {   // <-- CRIA UMA FILA, COM ARGUMENTOS
//        Map<String, Object> args = new HashMap<String, Object>();
//        args.put("x-dead-letter-exchange", dlx);
//        args.put("x-dead-letter-routing-key", dlk);
        return new Queue(queueName, true);
    }



    @Bean
    DirectExchange exchange() { // <-- CRIA UMA EXCHANGE PARA TOPIC
        return new DirectExchange(exchange);
    }


    @Bean
    Binding binding(Queue queue, DirectExchange exchange) { // <-- FAZ A LIGAÇÃO ENTRE FILA, EXCHANGE E ROUTINGKEY
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


}
