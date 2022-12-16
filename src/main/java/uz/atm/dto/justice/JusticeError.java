package uz.atm.dto.justice;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 11/16/22 9:48 AM
 **/
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JusticeError {
    public Integer code;

    public String message;
}
