package uz.atm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Slf4j
@Service
public class RabbitMqService {


    private final RabbitTemplate rabbitTemplateProd;
    private final RabbitTemplate rabbitTemplateDev;
    private final JusticeService justiceService;
    private final RabbitMQProperties rabbitMQProperties;

    public RabbitMqService(@Qualifier( "rabbit-template-prod" ) RabbitTemplate rabbitTemplateProd, @Qualifier( "rabbit-template-dev" ) RabbitTemplate rabbitTemplateDev, @Lazy JusticeService justiceService, RabbitMQProperties rabbitMQProperties) {
        this.rabbitTemplateProd = rabbitTemplateProd;
        this.rabbitTemplateDev = rabbitTemplateDev;
        this.justiceService = justiceService;
        this.rabbitMQProperties = rabbitMQProperties;
    }

    public void sendResult(List<EtpResponseDto> message, Integer etpId, String type) {
        if ( type.equals("prod") ) {
            if ( etpId == 2 ) {
                message
                        .forEach(f -> {
                            try {
                                rabbitTemplateProd.send(rabbitMQProperties.getPublisherExchangeName(), rabbitMQProperties.getPublisherRoutingKeyXtXarid(), new Message(new ObjectMapper().writeValueAsBytes(f)));
                            } catch ( JsonProcessingException e ) {
                                log.error("Error while Sending Response etpId :{},response : {} ", etpId, f.toString());
                                e.printStackTrace();
                            }
                        });

            } else if ( etpId == 1 ) {
                message
                        .forEach(f -> {
                            try {
                                rabbitTemplateProd.send(rabbitMQProperties.getPublisherExchangeName(), rabbitMQProperties.getPublisherRoutingKeyUzEx(), new Message(new ObjectMapper().writeValueAsBytes(f)));
                            } catch ( JsonProcessingException e ) {
                                log.error("Error while Sending Response etpId :{},response : {} ", etpId, f.toString());
                                e.printStackTrace();
                            }
                        });
            }
        } else if ( type.equals("dev") ) {
            if ( etpId == 2 ) {
                message
                        .forEach(f -> {
                            try {
                                rabbitTemplateDev.send(rabbitMQProperties.getPublisherExchangeName(), rabbitMQProperties.getPublisherRoutingKeyXtXarid(), new Message(new ObjectMapper().writeValueAsBytes(f)));
                            } catch ( JsonProcessingException e ) {
                                log.error("Error while Sending Response etpId :{},response : {} ", etpId, f.toString());
                                e.printStackTrace();
                            }
                        });

            } else if ( etpId == 1 ) {
                message
                        .forEach(f -> {
                            try {
                                rabbitTemplateDev.send(rabbitMQProperties.getPublisherExchangeName(), rabbitMQProperties.getPublisherRoutingKeyUzEx(), new Message(new ObjectMapper().writeValueAsBytes(f)));
                            } catch ( JsonProcessingException e ) {
                                log.error("Error while Sending Response etpId :{},response : {} ", etpId, f.toString());
                                e.printStackTrace();
                            }
                        });
            }
        }

    }

    @Profile( value = "prod" )
    @RabbitListener( containerFactory = "rabbit-listener-container-factory-prod", queues = {"${etp.rabbit.consumer-queue-name}"} )
    public void listenerProd(JsonNode node) {
        new Thread(() -> {
            try {
                justiceService.consume(node, "prod");
            } catch ( Exception e ) {
                log.error("Error: Service : prod;  Json ->  " + node.toPrettyString());
                log.error("Error:    Consuming Node from : " + ExceptionUtils.getStackTrace(e));
            }
        }).start();

    }

    @Profile( value = "prod" )
    @RabbitListener( containerFactory = "rabbit-listener-container-factory-dev", queues = {"${etp.rabbit.consumer-queue-name}"} )
    public void listenerDev(JsonNode node) {
        new Thread(() -> {
            try {
                justiceService.consume(node, "dev");
            } catch ( Exception e ) {
                log.error("Error: Service : prod;  Json ->  " + node.toPrettyString());
                log.error("Error:    Consuming Node from : " + ExceptionUtils.getStackTrace(e));
            }
        }).start();
    }
}
