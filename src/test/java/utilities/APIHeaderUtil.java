package utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class APIHeaderUtil{

    public static Map<String, String> defaultHeaders(){
        Map<String,String> defaultHeaders = new HashMap<>();
        defaultHeaders.put("Content-Type","application/json");
        return defaultHeaders;
    }

    public static Map<String, String> basicAuthorizationHeader(Properties props){
        Map<String,String> basicAuthCredentials = new HashMap<>();
        basicAuthCredentials.put("username",props.getProperty("username"));
        basicAuthCredentials.put("password",props.getProperty("password"));
        return basicAuthCredentials;
    }
}
