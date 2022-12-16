package uz.atm.service;

import org.springframework.stereotype.Service;
import uz.atm.entity.Result;
import uz.atm.repository.ResultRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/18/22 11:50 AM
 **/
@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }




    public Optional<Result> checkPinfls(Long f, Long s) {
        return resultRepository.findByBasePinflAndCheckPinfl(f, s);
    }

    public List<Result> checkPinfls(Long f, List<Long> s) {
        return resultRepository.findAllByBasePinflAndCheckPinflIsIn(f, s);
    }

    public void save(Result result) {
        resultRepository.save(result);
    }

    public void save(Long base, Long requestId, Map<Long, Boolean> result) {
        result.forEach((K, V) -> this.save(new Result(requestId, base, K, V)));
    }
}
