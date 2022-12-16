package uz.atm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/18/22 11:01 AM
 **/

@Entity(name = "results")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "base_pinfl_index", columnList = "base_pinfl"),
        @Index(name = "check_pinfl_index", columnList = "check_pinfl")
})
public class Result extends Auditable {

    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "base_pinfl")
    private Long basePinfl;

    @Column(name = "check_pinfl")
    private Long checkPinfl;

    @Column(name = "result")
    private Boolean result;

}
