package com.example.d308vacationplanner.utils;

public class DateUtils {
    public static String getCurrentTimestamp() {
        return java.time.LocalDateTime.now().toString();
    }
}
