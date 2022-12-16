package uz.atm.utils;


import java.util.UUID;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/24/22 10:12 AM
 **/
public class BaseUtils {

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
