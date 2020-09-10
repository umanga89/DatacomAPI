package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Level;
import utilities.APIHeaderUtil;
import utilities.APIHelperUtil;
import utilities.APIPaths;
import utilities.BaseUtil;

import static io.restassured.RestAssured.given;

public class TickAPISteps extends BaseUtil {

    private BaseUtil base;

    public TickAPISteps(BaseUtil base){
        this.base = base;
    }

    @Given("I send tick request {int} times")
    public void i_send_tick_request_times(Integer count) {
        try{
            while(count!=0) {
                i_create_tick_request();
                i_send_tick_request();
                count--;
            }
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }



    @Given("I create tick request")
    public void i_create_tick_request() {
        try{
            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.TICK_PATH);

        }catch (Exception e){
            throw e;
        }
    }

    @When("I send tick request")
    public void i_send_tick_request() {
        try{
            this.base.response = given().headers(APIHeaderUtil.allHeaders(props.getProperty("x-fas-signature"))).
                    when().post();
        }catch (Exception e){
            throw e;
        }
    }
}
