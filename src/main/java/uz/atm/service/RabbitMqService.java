package uz.atm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.atm.dto.etp.EtpResponseDto;
import uz.atm.properties.RabbitMQProperties;

import java.util.List;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/21/22 2:38 PM
 **/
@Service
public class RabbitMqService {


    private final RabbitTemplate rabbitTemplate;
    private final JusticeService justiceService;
    private final RabbitMQProperties rabbitMQProperties;

    public RabbitMqService(RabbitTemplate rabbitTemplate, @Lazy JusticeService justiceService, RabbitMQProperties rabbitMQProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.justiceService = justiceService;
        this.rabbitMQProperties = rabbitMQProperties;
    }

    public void sendResult(List<EtpResponseDto> message, Integer etpId) {
        if (etpId == 2) {
            message
                    .forEach(f -> {
                        try {
                            rabbitTemplate.send(rabbitMQProperties.getPublisherExchangeName(), rabbitMQProperties.getPublisherRoutingKeyXtXarid(), new Message(new ObjectMapper().writeValueAsBytes(f)));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    });

        } else if (etpId == 1) {
            message
                    .forEach(f -> {
                        try {
                            rabbitTemplate.send(rabbitMQProperties.getPublisherExchangeName(), rabbitMQProperties.getPublisherRoutingKeyUzEx(), new Message(new ObjectMapper().writeValueAsBytes(f)));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    @Profile(value = "prod")
    @RabbitListener(queues = {"${etp.rabbit.consumer-queue-name}"})
    public void listener(JsonNode str) {
        justiceService.consume(str);
    }
}
