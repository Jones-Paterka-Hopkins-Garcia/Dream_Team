package com.codeup.adlister.util;

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
}
