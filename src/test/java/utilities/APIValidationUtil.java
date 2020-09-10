package utilities;

import io.restassured.response.Response;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import steps.BookAPISteps;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class APIValidationUtil extends BaseUtil{

    public APIValidationUtil(BaseUtil base){
        BaseUtil.logger = LogManager.getLogger(APIValidationUtil.class.getName());
    }

    public static void validateStatusCode(Response response, int statusCode) throws AssertionError{
        try {
            response.then().statusCode(statusCode);
        }catch (AssertionError e){
//            BaseUtil.logger.log(Level.ERROR,"Response status code assertion error");
//            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw e;
        }
    }

    public static void validateBodyParameterWithStringValue(Response response, String key, String value) throws AssertionError {
        try {
            response.then().body(key, equalTo(value));
        }catch (AssertionError e){
//            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
//            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw e;
        }
    }

    public static void validateBodyParameterWithIntValue(Response response, String key, int value) throws AssertionError {
        try {
            response.then().body(key, equalTo(value));
        }catch (AssertionError e){
//            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
//            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw e;
        }
    }

    public static void validateBodyParameterWithLongValue(Response response, String key, long value) throws AssertionError {
        try {
            response.then().body(key, equalTo(value));
        }catch (AssertionError e){
//            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
//            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw e;
        }
    }

    public static void validateBodyIsEmpty(Response response) throws AssertionError {
        try {
            String responseString = response.then().extract().asString();
            Assert.assertEquals(responseString,"null","Response body is not null");
        }catch (AssertionError e){
//            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
//            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw e;
        }
    }
}
