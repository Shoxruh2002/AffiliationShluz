package uz.atm.dto.etp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/18/22 11:52 AM
 **/
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EtpResponseDto {

    @JsonProperty("REQUEST_ID")
    public Long requestId;

    @JsonProperty("ETP_ID")
    public Integer etpId;

    @JsonProperty("RESPONSE_ID")
    public Long responseId;

    @JsonProperty("METHOD_NAME")
    public String methodName;

    @JsonProperty("PAYLAOD")
    public Payload paylaod;

    @ToString
    @AllArgsConstructor
    public static class Payload {

        @JsonProperty("BASE")
        public Long base;

        @JsonProperty("CHECK")
        public Long check;

        @JsonProperty("RESULT")
        public Boolean result;

    }

}
