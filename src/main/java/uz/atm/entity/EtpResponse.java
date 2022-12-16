package uz.atm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 12/16/22 11:41 AM
 **/
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EtpResponse extends Auditable {

    @JsonProperty("REQUEST_ID")
    public Long requestId;

    @JsonProperty("ETP_ID")
    public Integer etpId;

    @JsonProperty("RESPONSE_ID")
    public Long responseId;

    @JsonProperty("METHOD_NAME")
    public String methodName;

    @JsonProperty("PAYLAOD")
    public String paylaod;

}
