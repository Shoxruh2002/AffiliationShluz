package uz.atm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import uz.atm.properties.JusticeAPiProperties;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

import static uz.atm.utils.BaseUtils.longToBytes;


/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/18/22 11:01 AM
 **/

@Slf4j
@Configuration
public class BaseConfig {

    @Value( "${request.id.file.path}" )
    private String filePath;
    private final JusticeAPiProperties justiceAPiProperties;

    public BaseConfig(JusticeAPiProperties justiceAPiProperties) {
        this.justiceAPiProperties = justiceAPiProperties;
    }


    @PostConstruct
    public void init() {
        if ( Files.notExists(Path.of(filePath)) ) {
            log.error("File Not Found with path : {}", filePath);
            throw new FileSystemNotFoundException("File Not Found with path : " + filePath);
        }

        try ( RandomAccessFile file = new RandomAccessFile(filePath, "rw");
              FileChannel channel = file.getChannel();
              FileLock lock = channel.lock() ) {
            file.seek(0);
            file.readLong();
        } catch ( IOException e ) {
            try ( RandomAccessFile file = new RandomAccessFile(filePath, "rw");
                  FileChannel channel = file.getChannel();
                  FileLock lock = channel.lock() ) {
                file.seek(0);
                file.write(longToBytes(1_000));
            } catch ( IOException ee ) {
                log.error("Exception occurred while generating requestId ; Cause : {}", ee.getMessage());
            }
            log.error("Exception occurred while generating requestId ; Cause : {}", e.getMessage());
        }
    }


    @Bean(name = "justice-web-client")
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(justiceAPiProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Api-Token", justiceAPiProperties.getKey())
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean(name = "justice-rest-template")
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .rootUri(justiceAPiProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Api-Token", justiceAPiProperties.getKey())
                .build();
    }
}
