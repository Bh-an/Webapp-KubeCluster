package com.csye7125group1.Webapp.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailCheck {

    public static boolean checkEmail(String email){

        Pattern pattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches()){
            return true;
        }
        else {
            return false;
        }
    }
}
