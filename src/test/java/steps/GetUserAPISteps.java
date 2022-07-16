package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import pojos.users.User;
import utilities.APIHelperUtil;
import utilities.APIPaths;
import utilities.APIValidationUtil;
import utilities.BaseUtil;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetUserAPISteps extends BaseUtil{

    private BaseUtil base;
    private Map<String,Object> headers = new HashMap<>();

    public GetUserAPISteps(BaseUtil base){
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(GetUserAPISteps.class.getName());
        this.base.requestWriter = new StringWriter();
        this.base.requestCapture = new PrintStream(new WriterOutputStream(this.base.requestWriter));
    }

    @When("I send get user request by Id {int}")
    public void i_send_get_user_request_with_id(int userId) throws Exception {
        try{
            this.base.response = given().when().get("/"+userId);

            this.base.requestCapture.flush();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new Exception(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see {string} of the user is returned as {string}")
    public void i_should_see_users_in_response_and_each_user_should_have_id_parameter(String paramName, String value) {
        try{

            User returnedUser = this.base.response.as(User.class);
            Assert.assertEquals(returnedUser.getName(), value, "Returned name is not matched with expected name");

        }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

}
