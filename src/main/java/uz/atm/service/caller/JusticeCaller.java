package uz.atm.service.caller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.atm.dto.justice.JusticeRequestDto;
import uz.atm.dto.justice.JusticeResponse;
import uz.atm.exception.messages.ApiMessages;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/18/22 11:01 AM
 **/


@Slf4j
@Service
public class JusticeCaller {

    private final WebClient webClient;

    public JusticeCaller(@Qualifier( "justice-web-client" ) WebClient webClient) {
        this.webClient = webClient;
    }


//    public Optional<JusticeResponse> postCall(JusticeRequestDto request, String endpoint) {
//        log.info("postCall is calling with request : {},  endpoint : {}", request.toString(), endpoint);
//
//        if ( request.params.toCheck.size() == 0 ) {
//            return Optional.of(new JusticeResponse(request.jsonrpc, request.id, new HashMap<>()));
//        }
//
//        ResponseEntity<JusticeResponse> response = restTemplate.postForEntity(endpoint, request, JusticeResponse.class);
//        if ( response.getStatusCode().is2xxSuccessful() ) {
//            JusticeResponse justiceResponse = response.getBody();
//            if ( Objects.nonNull(justiceResponse.error) ) {
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
//    }

    public <RES> Optional<RES> postCall(JusticeRequestDto request, String endpoint, Class<RES> responseType) {
        try {
            RES response = webClient.post()
                    .uri(endpoint)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();
            return Optional.ofNullable(response);
        } catch ( Exception e ) {
            log.error("Exception Occurred calling DSQ : Request : {} ; Cause {}", request.toString(), e.getMessage());
            return Optional.empty();
        }
    }
}
