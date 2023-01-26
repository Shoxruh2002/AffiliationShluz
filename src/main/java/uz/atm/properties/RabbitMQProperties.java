package uz.atm.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 11/16/22 11:39 AM
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "etp.rabbit")
public class RabbitMQProperties {

    private String consumerQueueName;

    private String publisherExchangeName;

    private String publisherRoutingKeyXtXarid;

    private String publisherRoutingKeyUzEx;

    private Credentials dev;

    private Credentials prod;

    @Data
    public static class Credentials {

        private String username;
        private String password;
        private Integer port;
        private String host;
    }


}
