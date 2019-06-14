package com.example.demo.amqp;

import com.example.demo.clients.SingersServiceClient;
import dto.SingerDto;
import keys.RabbitKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor

@Component
public class UserExistAmqpListener {
    @Autowired
    SingersServiceClient singersServiceClient;

    @RabbitListener(queues = {RabbitKeys.QUEUE_SINGERS_CHANGE_NAME})
    public void receiveMessage(SingerDto singer) {

        singer.setName("RabbitMQName");
        singersServiceClient.updateSinger(singer, singer.getId());
    }
}
