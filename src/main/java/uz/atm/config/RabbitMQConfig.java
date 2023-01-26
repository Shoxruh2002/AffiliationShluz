package uz.atm.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import uz.atm.properties.RabbitMQProperties;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/21/22 2:35 PM
 **/
@EnableRabbit
@Configuration
public class RabbitMQConfig {

    private final RabbitMQProperties rabbitMQProperties;

    public RabbitMQConfig(RabbitMQProperties rabbitMQProperties) {
        this.rabbitMQProperties = rabbitMQProperties;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean( name = "connection-factory-dev" )
    public ConnectionFactory connectionFactoryDev() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        RabbitMQProperties.Credentials credentials = rabbitMQProperties.getDev();
        connectionFactory.setHost(credentials.getHost());
        connectionFactory.setPort(credentials.getPort());
        connectionFactory.setUsername(credentials.getUsername());
        connectionFactory.setPassword(credentials.getPassword());
        return connectionFactory;
    }

    @Bean( name = "connection-factory-prod" )
    @Primary
    public ConnectionFactory connectionFactoryProd() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        RabbitMQProperties.Credentials credentials = rabbitMQProperties.getProd();
        connectionFactory.setHost(credentials.getHost());
        connectionFactory.setPort(credentials.getPort());
        connectionFactory.setUsername(credentials.getUsername());
        connectionFactory.setPassword(credentials.getPassword());
        return connectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean( "rabbit-template-dev" )
    public RabbitTemplate rabbitTemplateDev(@Qualifier( "connection-factory-dev" ) ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Primary
    @Bean( "rabbit-template-prod" )
    public RabbitTemplate rabbitTemplateProd(@Qualifier( "connection-factory-prod" ) ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean( name = "rabbit-listener-container-factory-dev" )
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactoryDev(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier( "connection-factory-dev" ) ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

    @Bean( name = "rabbit-listener-container-factory-prod" )
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactoryProd(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier( "connection-factory-prod" ) ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }



}
