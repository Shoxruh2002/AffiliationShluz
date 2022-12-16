package uz.atm.dto.etp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 12/14/22 3:37 PM
 **/
@AllArgsConstructor
@NoArgsConstructor
public class EtpRequestDto {

    @JsonProperty("ETP_ID")
    public Integer etpId;

    @JsonProperty("REQUEST_ID")
    public Long requestId;

    @JsonProperty("METHOD_NAME")
    public String methodName;

    @JsonProperty("PAYLOAD")
    public Payload payload;

    public static class Payload {

        @JsonProperty("BASE")
        public List<Long> base;

        @JsonProperty("CHECK")
        public List<Long> check;

    }


}
