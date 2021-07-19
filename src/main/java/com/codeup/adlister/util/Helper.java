package com.codeup.adlister.util;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    public static String formatReturnAddress(StringBuffer url, String queryString)
    {
        String address = "";
        String urlString = url.toString();
        if(!urlString.isEmpty())
        {
            address += urlString;
            if(queryString!=null && !queryString.isEmpty() && !queryString.equals("null"))
            {
                address += "?"+queryString;
            }
        }
        return address;
    }

    public static boolean validUsername(String username){
        // regex to check valid username
        String regexUsername = "^[A-Za-z]\\w{5,29}$";

        // this is compiling the regex
        Pattern pattern = Pattern.compile(regexUsername);

        // if the username is empty it returns false
        if(username == null){
            return false;
        }

        // pattern class the matcher method which is used to find matching between the input username and the regular expression
        Matcher matcher = pattern.matcher(username);

        //returns if the username matches the regex
        return matcher.matches();
    }

    public static boolean validEmail(String email){
        // regex to check valid email
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.+[a-zA-Z0-9_+&*-]+)*@+(?:[a-zA-Z0-9-]+\\.)+[a-z+A-Z]{2,7}$";

        // this is compiling the regex
        Pattern pattern = Pattern.compile(regexEmail);

        // if the email is empty it returns false
        if (email == null){
            return false;
        }

        // pattern class the matcher method which is used to find matching between the input email and the regular expression
        Matcher matcher = pattern.matcher(email);

        //returns if the email matches the regex
        return matcher.matches();
    }

    public static boolean usernameNotExist(String username){
        User nameTest = DaoFactory.getUsersDao().findByUsername(username);
        return nameTest == null;
    }

    public static boolean emailNotExist(String email){
        User emailTest = DaoFactory.getUsersDao().findByUsername(email);
        return emailTest == null;
    }
}
