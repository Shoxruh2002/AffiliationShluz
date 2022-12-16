package uz.atm.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Bekpulatov Shoxruh
 * Date: 03/10/22
 * Time: 10:35
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DSQDataDto<T> {

    public T body;

    public String error;

    public boolean success;

    public DSQDataDto(T body) {
        this.body = body;
        this.success = true;
    }

    public DSQDataDto(String error) {
        this.error = error;
        this.success = false;
    }
}
