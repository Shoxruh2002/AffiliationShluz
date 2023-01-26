package uz.atm.controller;

import org.springframework.web.bind.annotation.*;
import uz.atm.dto.etp.EtpRequestDto;
import uz.atm.service.JusticeService;

import java.util.Date;

/**
 * Author: Bekpulatov Shoxruh
 * Date: 19/10/22
 * Time: 10:10
 */
@RestController
@RequestMapping("/justice")
public class JusticeController {

    private final JusticeService justiceService;


    public JusticeController(JusticeService justiceService) {
        this.justiceService = justiceService;
    }


    @PostMapping("/check")
    public boolean check(@RequestBody EtpRequestDto dto) {
        try {
            justiceService.sendJustice(dto,"dev");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping()
    public String check2() {
        return new Date().toString();
    }


}
