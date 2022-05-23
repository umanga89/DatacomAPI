package utilities;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Properties;

public class BaseUtil {

    public static Logger logger;
    public static StringWriter requestWriter;
    public static PrintStream requestCapture;
    public static Response response;
    public static Properties props;
    public static String customer_name;
    public static BigDecimal account_balance;
    public static String customer_uuid;
    public static int customer_account_id;
    public static String isin;
    public static double product_price;
    public static int quantity;
    public static Connection customer_db_connection;
    public static Connection order_db_connection;

}
