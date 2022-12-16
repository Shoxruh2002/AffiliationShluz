package uz.atm.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.UUID;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/24/22 10:12 AM
 **/
@Slf4j
public class BaseUtils {

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static Long generateLongId(String path) {

        // TODO: 12/15/22 Should optimaze

        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            Scanner scanner = new Scanner(fileInputStream);
            long l = scanner.nextLong();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(String.valueOf(l + 1));
            bufferedWriter.close();
            return l;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Exception occurred while generating requestId ; Cause : {}", e.getMessage());
            return new SecureRandom().nextLong(999999999999L, 99999999999999999L);
        }
    }
}
