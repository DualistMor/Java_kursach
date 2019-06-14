package com.example.services.amqp;

import dto.SingerDto;
import keys.RabbitKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SingerAmqpProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(SingerDto userDto) {
        rabbitTemplate.convertAndSend(RabbitKeys.QUEUE_SINGERS_CHANGE_NAME, userDto);
    }

}
