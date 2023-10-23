package com.project.antifakebook.util;

import java.util.Random;

public class OTPUtils {
    public static String generateOTP() {
        String numbers = "0123456789";
        Random random_method = new Random();
        char[] otp = new char[6];
        for (int i = 0; i < 6; i++) {
            otp[i] =
                    numbers.charAt(random_method.nextInt(numbers.length()));
        }
        return new String(otp);
    }

}
