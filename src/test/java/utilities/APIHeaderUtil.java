package utilities;

import java.util.HashMap;
import java.util.Map;

public class APIHeaderUtil {

    public static Map<String, String> defaultHeaders(){
        Map<String,String> defaultHeaders = new HashMap<>();
        defaultHeaders.put("Content-Type","application/json");
        return defaultHeaders;
    }

    public static Map<String, String> xFasSignatureHeader(String x_fas_signature){
        Map<String,String> xFasSignatureHeader = new HashMap<>();
        xFasSignatureHeader.put("x-fas-signature",x_fas_signature);
        return xFasSignatureHeader;
    }

    public static Map<String, String> allHeaders(String x_fas_signature){
        Map<String,String> allHeaders = new HashMap<>();
        allHeaders.put("Content-Type","application/json");
        allHeaders.put("x-fas-signature",x_fas_signature);
        return allHeaders;
    }
}
