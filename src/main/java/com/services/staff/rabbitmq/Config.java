package com.services.staff.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.vetQueue}")
    private String vetQueue;
    @Value("${rabbitmq.vetRoutingKey}")
    private String vetRoutingKey;
    @Value("${rabbitmq.groomQueue}")
    private String groomQueue;
    @Value("${rabbitmq.groomRoutingKey}")
    private String groomRoutingKey;
    @Value("${rabbitmq.trainerQueue}")
    private String trainerQueue;
    @Value("${rabbitmq.trainerRoutingKey}")
    private String trainerRoutingKey;

    @Bean
    public Queue vetQueue(){
        return new Queue(vetQueue);
    }

    @Bean
    public Queue groomQueue(){
        return new Queue(groomQueue);
    }

    @Bean
    public Queue trainerQueue(){
        return new Queue(trainerQueue);
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding vetBinding(DirectExchange exchange, Queue vetQueue){
        return BindingBuilder.bind(vetQueue).to(exchange).with(vetRoutingKey);
    }

    @Bean
    public Binding groomBinding(DirectExchange exchange, Queue groomQueue){
        return BindingBuilder.bind(groomQueue).to(exchange).with(groomRoutingKey);
    }

    @Bean
    public Binding trainerBinding(DirectExchange exchange, Queue trainerQueue){
        return BindingBuilder.bind(trainerQueue).to(exchange).with(trainerRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
