package com.project.antifakebook.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateRegisterAccountRequestUtils {
    private static final String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password, String email) {
        return password.length() >= 6 && password.length() <= 8 && !password.equals(email);
    }
}
