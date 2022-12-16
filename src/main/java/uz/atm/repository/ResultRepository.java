package uz.atm.repository;

import org.springframework.stereotype.Repository;
import uz.atm.entity.Result;

import java.util.List;
import java.util.Optional;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/18/22 11:01 AM
 **/

@Repository
public interface ResultRepository extends BaseRepository<Result> {

    Optional<Result> findByBasePinflAndCheckPinfl(Long basePinfl, Long checkPinfl);

    List<Result> findAllByBasePinflAndCheckPinflIsIn(Long basePinfl, List<Long> checkPinfl);
}
