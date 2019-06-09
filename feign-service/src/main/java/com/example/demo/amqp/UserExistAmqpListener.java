package com.example.demo.amqp;

import com.example.demo.clients.SingersServiceClient;
import dto.SingerDto;
import dto.TrackDto;
import keys.RabbitKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor

@Component
public class UserExistAmqpListener {
    @Autowired
    SingersServiceClient singersServiceClient;

    @RabbitListener(queues = {RabbitKeys.QUEUE_USERS_DELETE})
    public void receiveMessage(SingerDto singer) {
        
        log.info("Received Message: ", singer.toString());
        singer.setName("RabbitsName");
        singersServiceClient.updateSinger(singer, singer.getId());
    }
}
