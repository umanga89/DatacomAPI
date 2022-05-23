package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.hamcrest.Matchers;
import org.testng.Assert;
import utilities.APIPaths;
import utilities.APIValidationUtil;
import utilities.BaseUtil;

import java.io.PrintStream;
import java.io.StringWriter;

public class CommonSteps extends BaseUtil {

    private BaseUtil base;

    public CommonSteps(BaseUtil base) {
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(CommonSteps.class.getName());
        this.base.requestWriter = new StringWriter();
        this.base.requestCapture = new PrintStream(new WriterOutputStream(this.base.requestWriter));
    }

    @Then("I should see response with status code as {int}")
    public void i_should_see_response_with_status_code_as(Integer statusCode) throws AssertionError {
        try {
            APIValidationUtil.validateStatusCode(this.base.response, statusCode);
        } catch (AssertionError e) {
            BaseUtil.logger.log(Level.ERROR, "Status code mismatch");
            BaseUtil.logger.log(Level.ERROR, e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see {string} in response body")
    public void i_should_see_customer_uuid(String message) throws AssertionError {
        try{
            String returnedMessage = BaseUtil.response.getBody().asString();
            Assert.assertEquals(returnedMessage,message);
        }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see empty response body")
    public void i_should_see_empty_response_body() throws AssertionError {
//        try {
//            //APIValidationUtil.validateBodyIsEmpty(this.base.response);
//            this.base.response.then().body("", Matchers.hasProperty("inix"));
//        } catch (AssertionError e) {
//            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
//            BaseUtil.logger.log(Level.ERROR, e.getMessage());
//            BaseUtil.logger.log(Level.ERROR,"Request: "+this.base.requestWriter.toString());
//            BaseUtil.logger.log(Level.ERROR,"Response: "+this.base.response.asString());
//            throw e;
//        }
    }

    @Then("I should see {string} is {string} in response body")
    public void i_should_see_is_in_response_body(String key, String value) {
        try {
            APIValidationUtil.validateBodyParameterWithStringValue(this.base.response,key,value);
        } catch (AssertionError e) {
            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
            BaseUtil.logger.log(Level.ERROR, e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @And("I should see all records have {string} field")
    public void i_should_see_all_records_have_id_field(String key) {
        try {
            APIValidationUtil.validateAllRecordsHasGivenParameter(this.base.response,key);
            //APIValidationUtil.validateBodyAllHasKey(this.base.response,key);
        } catch (AssertionError e) {
            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
            BaseUtil.logger.log(Level.ERROR, e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see {string} field in response")
    public void i_should_see_pupil_id_field_in_response(String key) {
        try {
            APIValidationUtil.validateBodyHasParameter(this.base.response,key);
            APIValidationUtil.validateBodyParameterIsNotNullOrEmpty(this.base.response,key);
        } catch (AssertionError e) {
            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
            BaseUtil.logger.log(Level.ERROR, e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see jsonSchema is validated")
    public void i_should_see_json_schema_is_validated() {
        try {
            APIValidationUtil.validateJsonSchema(this.base.response);
        } catch (AssertionError e) {
            throw e;
        }
    }

    @Then("I should see array jsonSchema is validated")
    public void i_should_see_array_json_schema_is_validated() {
        try {
            APIValidationUtil.validateArrayJsonSchema(this.base.response);
        } catch (AssertionError e) {
            throw e;
        }
    }

    @Then("I should see {string} description in response")
    public void i_should_see_field_in_response(String key) {
        try {
            APIValidationUtil.validateBodyHasParameter(this.base.response,key);
        } catch (AssertionError e) {
            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
            BaseUtil.logger.log(Level.ERROR, e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

}
