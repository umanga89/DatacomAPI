package utilities;

import java.util.Date;

public class CommonUtil {
    public static String getNameWithTimeStamp(String name) {

        Date date = new Date();
        long timeMilli = date.getTime();
        return name+"_"+timeMilli;
    }
}
