package utilities;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Properties;

public class BaseUtil {

    public static Logger logger;
    public static StringWriter requestWriter;
    public static PrintStream requestCapture;
    public static Response response;
    public static Properties props;
    public static String pupilId;

}
