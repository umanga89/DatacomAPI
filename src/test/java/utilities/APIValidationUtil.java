package utilities;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.File;

import static org.hamcrest.Matchers.*;

public class APIValidationUtil extends BaseUtil {

    public APIValidationUtil() {
        BaseUtil.logger = LogManager.getLogger(APIValidationUtil.class.getName());
    }

    public static void validateStatusCode(Response response, int statusCode) throws AssertionError {
        try {
            response.then().statusCode(statusCode);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateBodyHasParameter(Response response, String key) throws AssertionError {
        try {
            response.then().body("$", hasKey(key));
        } catch (AssertionError e) {
            throw new AssertionError("\"" + key + "\" field does not exist in response body");
        }
    }

    public static void validateBodyParameterIsNotNullOrEmpty(Response response, String key) throws AssertionError {
        try {
            response.then().body(key, not(isEmptyOrNullString()));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateBodyParameterWithStringValue(Response response, String key, String value) throws AssertionError {
        try {
            response.then().body(key, equalTo(value));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateBodyParameterWithStringValueContainsValue(Response response, String key, String value) throws AssertionError {
        try {
            response.then().body(key, containsString(value));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateBodyParameterWithBooleanValue(Response response, String key, boolean value) throws AssertionError {
        try {
            response.then().body(key, equalTo(value));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateBodyAllHasKey(Response response, String key) throws AssertionError {
        try {
            response.then().body("$", everyItem((hasKey(key))));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateBodyParameterWithIntValue(Response response, String key, int value) throws AssertionError {
        try {
            response.then().body(key, equalTo(value));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateBodyIsEmpty(Response response) throws AssertionError {
        try {
            String responseString = response.then().extract().asString();
            Assert.assertEquals(responseString, null, "Response body is not null");
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateBodyIsNotEmpty(Response response) throws AssertionError {
        try {
            String responseString = response.then().extract().asString();
            Assert.assertNotEquals(responseString, null, "Response body is not null");
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateAllRecordsHasGivenParameter(Response response, String fieldName) throws AssertionError {
        boolean hasKey = true;
        try {
            JSONArray jsonArray = new JSONArray(response.getBody().asString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (!jsonObject.has(fieldName)) {
                    hasKey = false;
                }
            }
            Assert.assertTrue(hasKey);
        } catch (AssertionError e) {
            throw new AssertionError("field \"" + fieldName + "\" is not found in one or many records");
        }
    }

    public static String getValueForKey(Response response, String key) throws AssertionError {

        String pupilId = "";
        try {
            JSONObject jsonObject = new JSONObject(response.asString());
            Assert.assertTrue(jsonObject.has(key));
            pupilId = jsonObject.get(key).toString();
        } catch (AssertionError e) {
            throw new AssertionError("field \"" + key + "\" is not found in response");
        }
        return pupilId;
    }

    public static String validateJsonSchema(Response response) throws AssertionError {

        String pupilId = "";
        //Need to set additionalProperties:false if no additional property is accepted in json response
        //https://easy-json-schema.github.io/
        File schemaFile = new File("src/test/java/jsonSchema/addPupilSchema.json");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
        } catch (AssertionError e) {
            throw e;
        }
        return pupilId;
    }

    public static String validateArrayJsonSchema(Response response) throws AssertionError {

        String pupilId = "";
        //Need to set additionalProperties:false if no additional property is accepted in json response
        //https://easy-json-schema.github.io/
        File schemaFile = new File("src/test/java/jsonSchema/getAllPupilsSchema.json");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
        } catch (AssertionError e) {
            throw e;
        }
        return pupilId;
    }
}
