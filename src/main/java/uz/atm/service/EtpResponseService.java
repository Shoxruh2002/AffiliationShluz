package uz.atm.service;

import org.springframework.stereotype.Service;
import uz.atm.dto.etp.EtpResponseDto;
import uz.atm.entity.EtpResponse;
import uz.atm.repository.EtpResponseRepository;

import java.util.List;
import java.util.function.Function;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 12/16/22 11:46 AM
 **/
@Service
public class EtpResponseService {
    private final EtpResponseRepository repository;

    public EtpResponseService(EtpResponseRepository repository) {
        this.repository = repository;
    }

    public EtpResponse save(EtpResponseDto dto) {
        return repository.save(fromDto.apply(dto));
    }

    public List<EtpResponse> save(List<EtpResponseDto> dtos) {
        List<EtpResponse> etpResponses = dtos.stream().map(fromDto).toList();
        return repository.saveAll(etpResponses);
    }

    private final Function<EtpResponseDto, EtpResponse> fromDto = (dto) -> new EtpResponse(dto.requestId, dto.etpId, dto.responseId, dto.methodName, dto.paylaod.toString());
}
