package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import utilities.*;

import java.io.PrintStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetUsersAPISteps extends BaseUtil{

    private BaseUtil base;
    private Map<String,Object> headers = new HashMap<>();

    public GetUsersAPISteps(BaseUtil base){
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(GetUsersAPISteps.class.getName());
        this.base.requestWriter = new StringWriter();
        this.base.requestCapture = new PrintStream(new WriterOutputStream(this.base.requestWriter));
    }

    @Given("I populate get users request")
    public void i_create_get_users_request() throws Exception {
        try{

            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.GET_USERS);

            BaseUtil.logger.log(Level.INFO,"Set Get User request URL as "+props.getProperty("base.path")+APIPaths.GET_USERS);

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @When("I send get users request")
    public void i_send_get_users_request() throws Exception {
        try{
            this.base.response = given().when().get();

            this.base.requestCapture.flush();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new Exception(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see {int} users in response and each user should have {string} parameter")
    public void i_should_see_users_in_response_and_each_user_should_have_id_parameter(int userCount, String paramName) {
        try{

            //verify n number of users are returned in users request
            APIValidationUtil.validateResponseHasNnumberOfRecords(this.base.response, userCount);
            //verify each record as id parameter
            APIValidationUtil.validateAllRecordsHasGivenParameter(this.base.response, paramName);

        }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see following values in response body")
    public void i_should_see_response_body_as_following_details(io.cucumber.datatable.DataTable dataTable) throws AssertionError {
        try{

            //verify each record as

        }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

}
