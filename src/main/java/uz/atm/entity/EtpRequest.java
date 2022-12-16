package uz.atm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 12/16/22 11:42 AM
 **/
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EtpRequest extends Auditable {

    @JsonProperty("ETP_ID")
    public Integer etpId;

    @JsonProperty("REQUEST_ID")
    public Long requestId;

    @JsonProperty("METHOD_NAME")
    public String methodName;

    @JsonProperty("PAYLOAD")
    public String payload;

}
