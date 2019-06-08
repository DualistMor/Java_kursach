package com.example.services.configs;

import dto.SingerDto;
import keys.RabbitKeys;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringAmqpConfig {

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.setClassMapper(classMapper());
        return jsonMessageConverter;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public DefaultClassMapper classMapper()
    {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.cargotrans.common.dto.UserDto", SingerDto.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }

    @Bean
    public Queue queue() {
        return new Queue(RabbitKeys.QUEUE_USERS_DELETE, false);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange(RabbitKeys.EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(RabbitKeys.QUEUE_USERS_DELETE);
    }

    @Bean
    public Binding binding2(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(RabbitKeys.QUEUE_USER_EXIST);
    }
//
//    @Bean
//    public SimpleMessageListenerContainer springAmqpContainer(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(RabbitKeys.QUEUE_USERS_DELETE);
////        container.setMessageListener(listenerAdapter);
//        return container;
//    }

}
