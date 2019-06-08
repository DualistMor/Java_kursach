package com.example.demo.amqp;

import dto.SingerDto;
import keys.RabbitKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor

@Component
public class UserExistAmqpListener {

    @RabbitListener(queues = {RabbitKeys.QUEUE_USERS_DELETE})
    public void receiveMessage(SingerDto userId) {
        log.info("Received Message: {}", userId.toString());

    }
}
