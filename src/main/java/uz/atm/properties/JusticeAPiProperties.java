package uz.atm.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/18/22 11:01 AM
 **/

@Getter
@Setter
@ConfigurationProperties(prefix = "justice.api")
public class JusticeAPiProperties {

    private String baseUrl;

    private String key;

}
