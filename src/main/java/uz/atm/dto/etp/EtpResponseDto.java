package uz.atm.dto.etp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/18/22 11:52 AM
 **/
@AllArgsConstructor
@NoArgsConstructor
public class EtpResponseDto {

    @JsonProperty("REQUEST_ID")
    public Long requestId;

    @JsonProperty("BASE")
    public Long base;

    @JsonProperty("CHECK")
    public Long check;

    @JsonProperty("RESULT")
    public Boolean result;
}
