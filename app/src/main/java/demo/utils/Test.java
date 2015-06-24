package demo.utils;

import org.json.JSONObject;

/**
 * Created by ragavendran on 23-06-2015.
 */
public class Test
{
    public static void main(String args[])
    {
        String data = RestfulConnection.source(AppConstants.URLConstants.essentials);
        System.out.println("JSOSN"+" "+data);
    }
}
