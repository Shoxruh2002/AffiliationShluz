package uz.atm.service.caller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.atm.dto.justice.JusticeRequestDto;
import uz.atm.dto.justice.JusticeResponse;
import uz.atm.exception.messages.ApiMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/18/22 11:01 AM
 **/


@Slf4j
@Service
public class JusticeCaller {
    private final RestTemplate restTemplate;

    public JusticeCaller(@Qualifier("justice-rest-template") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Optional<JusticeResponse> postCall(JusticeRequestDto request, String endpoint) {
        log.info("postCall is calling with request : {},  endpoint : {}", request.toString(), endpoint);

        if (request.params.toCheck.size() == 0) {
            return Optional.of(new JusticeResponse(request.jsonrpc, request.id, new HashMap<>()));
        }

        ResponseEntity<JusticeResponse> response = restTemplate.postForEntity(endpoint, request, JusticeResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            JusticeResponse justiceResponse = response.getBody();
            if (Objects.nonNull(justiceResponse.error)) {
                log.error("Exception occurred while calling justiceService; Couse : {}; requestBody : {}", justiceResponse.error.message, request.toString());
                return Optional.of(new JusticeResponse(request.jsonrpc, request.id, new HashMap<>()));
            } else {
                log.info("Response of calling JusticeService : {}", justiceResponse.toString());
                return Optional.of(justiceResponse);
            }

        } else {
            log.error("Exception : {}, response : {} ; code {} ", ApiMessages.SERVICE_UNAVAILABLE, response.getBody().toString(), response.getStatusCode().value());
            return Optional.of(new JusticeResponse(request.jsonrpc, request.id, new HashMap<>()));
        }
    }

    public Optional<JusticeResponse> postCall(JusticeRequestDto request, String endpoint, boolean isTest) {
        log.info("postCall is calling with request : {},  endpoint : {}", request.toString(), endpoint);
        Map<Long, Boolean> result = new HashMap<>();
        for (Long aLong : request.params.toCheck) {
            result.put(aLong, true);
        }

        return Optional.of(new JusticeResponse(request.jsonrpc, request.id, result));
//
//        if (request.params.toCheck.size() == 0) {
//            return Optional.of(new JusticeResponse(request.jsonrpc, request.id, new HashMap<>()));
//        }
//
//        ResponseEntity<JusticeResponse> response = restTemplate.postForEntity(endpoint, request, JusticeResponse.class);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            JusticeResponse justiceResponse = response.getBody();
//            if (Objects.nonNull(justiceResponse.error)) {
//                log.error("Exception occurred while calling justiceService; Couse : {}; requestBody : {}", justiceResponse.error.message, request.toString());
//                return Optional.of(new JusticeResponse(request.jsonrpc, request.id, new HashMap<>()));
//            } else {
//                log.info("Response of calling JusticeService : {}", justiceResponse.toString());
//                return Optional.of(justiceResponse);
//            }
//
//        } else {
//            log.error("Exception : {}, response : {} ; code {} ", ApiMessages.SERVICE_UNAVAILABLE, response.getBody().toString(), response.getStatusCode().value());
//            return Optional.of(new JusticeResponse(request.jsonrpc, request.id, new HashMap<>()));
//        }
    }

}
