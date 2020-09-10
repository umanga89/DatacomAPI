package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Level;
import utilities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ResetAPISteps extends BaseUtil {

    private BaseUtil base;

    public ResetAPISteps(BaseUtil base){
        this.base = base;
    }

    @Given("I populate data for reset request")
    public void i_create_reset_request() {
        try{
            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.RESET_PATH);

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getStackTrace());
            throw e;
        }
    }

    @When("I send reset request")
    public void i_send_reset_request() {
        try{
            this.base.response = given().headers(APIHeaderUtil.allHeaders(props.getProperty("x-fas-signature"))).
                    when().put();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }
}
