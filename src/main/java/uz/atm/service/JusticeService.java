package uz.atm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.atm.dto.etp.EtpRequestDto;
import uz.atm.dto.etp.EtpResponseDto;
import uz.atm.dto.justice.JusticeRequestDto;
import uz.atm.dto.justice.JusticeResponse;
import uz.atm.entity.Result;
import uz.atm.exception.JsonParserException;
import uz.atm.properties.JusticeRequestProperties;
import uz.atm.service.caller.JusticeCaller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static uz.atm.utils.BaseUtils.generateLongId;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/18/22 11:01 AM
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class JusticeService {

    private final ObjectMapper objectMapper;
    private final JusticeCaller justiceCaller;
    private final ResultService resultService;
    private final RabbitMqService rabbitMqService;
    private final JusticeRequestProperties justiceRequestProperties;
    private final EtpResponseService etpResponseService;
    private final EtpRequestService etpRequestService;
    @Value("${request.id.file.path}")
    private String getRequestFilePath;

    public void consume(JsonNode node) {
        try {
            EtpRequestDto etpRequestDto = objectMapper.readValue(node.toString(), EtpRequestDto.class);
            this.sendJustice(etpRequestDto);
        } catch (JsonProcessingException e) {
            throw new JsonParserException(ExceptionUtils.getRootCauseMessage(e));
        }
    }

    public void sendJustice(EtpRequestDto etpRequestDto) {
        etpRequestService.save(etpRequestDto);
        List<Long> base = etpRequestDto.payload.base;
        List<Long> check = etpRequestDto.payload.check;
        base.forEach(f -> {
            List<Long> tempCheck = new ArrayList<>(check);
            List<Result> dbResults = resultService.checkPinfls(f, check);
            List<Long> dbCheckPinfl = dbResults.stream().map(Result::getCheckPinfl).toList();
            List<EtpResponseDto> etpResponseDtos = dbResults.stream().map(m -> getEtpResponseDto.apply(m, etpRequestDto)).toList();
            etpResponseService.save(etpResponseDtos);
            rabbitMqService.sendResult(etpResponseDtos, etpRequestDto.etpId);
            dbCheckPinfl.forEach(tempCheck::remove);

            Optional<JusticeResponse> justiceResponse = justiceCaller.postCall(
                    new JusticeRequestDto(
                            justiceRequestProperties.getJsonRpc(), justiceRequestProperties.getId(), justiceRequestProperties.getMethod(),
                            new JusticeRequestDto.Params(f, tempCheck)), "/");
            if (justiceResponse.isPresent()) {
                JusticeResponse response = justiceResponse.get();
                resultService.save(f, etpRequestDto.requestId, response.result);
                List<EtpResponseDto> etpResponseDtosFromJustica = response.result
                        .entrySet()
                        .stream()
                        .map(m ->
                                new EtpResponseDto(etpRequestDto.requestId, etpRequestDto.etpId, generateLongId(getRequestFilePath), etpRequestDto.methodName,
                                        new EtpResponseDto.Payload(f, m.getKey(), m.getValue()))
                        ).toList();
                etpResponseService.save(etpResponseDtosFromJustica);
                rabbitMqService.sendResult(etpResponseDtosFromJustica, etpRequestDto.etpId);
            }
        });
    }

    private final BiFunction<Result, EtpRequestDto, EtpResponseDto> getEtpResponseDto = (result, requestDto) ->
            new EtpResponseDto(requestDto.requestId, requestDto.etpId, generateLongId(getRequestFilePath), requestDto.methodName, new EtpResponseDto.Payload(result.getBasePinfl(), result.getCheckPinfl(), result.getResult()));


}