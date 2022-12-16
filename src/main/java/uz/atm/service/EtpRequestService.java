package uz.atm.service;

import org.springframework.stereotype.Service;
import uz.atm.dto.etp.EtpRequestDto;
import uz.atm.entity.EtpRequest;
import uz.atm.repository.EtpRequestRepository;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 12/16/22 11:44 AM
 **/
@Service
public class EtpRequestService {

    private final EtpRequestRepository repository;

    public EtpRequestService(EtpRequestRepository repository) {
        this.repository = repository;
    }

    public EtpRequest save(EtpRequestDto dto) {
        EtpRequest etpRequest = new EtpRequest(dto.etpId, dto.requestId, dto.methodName, dto.payload.toString());
        return repository.save(etpRequest);
    }
}
