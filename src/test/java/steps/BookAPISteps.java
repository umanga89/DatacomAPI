package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import utilities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookAPISteps extends BaseUtil{

    private BaseUtil base;
    private Map<String,Object> headers = new HashMap<>();
    private Map<String,Object> bookRequest = new HashMap<>();
    public BookAPISteps(BaseUtil base){
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(BookAPISteps.class.getName());
    }

    @Given("I populate data for Book request")
    public void i_create_book_request() {
        try{

            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.BOOK_PATH);

            //BaseUtil.logger.log(Level.INFO,"Set Book request URL as "+props.getProperty("base.path")+APIPaths.BOOK_PATH);

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }

    @When("I send Book request with following details")
    @When("I send Book request for 1st car with following details")
    @When("I send Book request for 2nd car with following details")
    @When("I send Book request for 3rd car with following details")
    public void i_send_book_request_with_following_details(io.cucumber.datatable.DataTable dataTable) {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            int sourceX = Integer.valueOf(listOfData.get(0).get("SourceX"));
            int sourceY = Integer.valueOf(listOfData.get(0).get("SourceY"));
            int destinationX = Integer.valueOf(listOfData.get(0).get("DestinationX"));
            int destinationY = Integer.valueOf(listOfData.get(0).get("DestinationY"));

            //creating headers
            headers.putAll(APIHeaderUtil.defaultHeaders());
            headers.putAll(APIHeaderUtil.xFasSignatureHeader(props.getProperty("x-fas-signature")));

            this.base.response = given().headers(headers).
                                when().body(BookAPIBodyUtil.createBookBodyInteger(sourceX,sourceY,destinationX,destinationY)).post();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }

    @When("I send Book request with a given values")
    public void i_send_book_request_with_given_details(io.cucumber.datatable.DataTable dataTable) {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            long sourceX = Long.valueOf(listOfData.get(0).get("SourceX"));
            long sourceY = Long.valueOf(listOfData.get(0).get("SourceY"));
            long destinationX = Long.valueOf(listOfData.get(0).get("DestinationX"));
            long destinationY = Long.valueOf(listOfData.get(0).get("DestinationY"));

            //creating headers
            headers.putAll(APIHeaderUtil.defaultHeaders());
            headers.putAll(APIHeaderUtil.xFasSignatureHeader(props.getProperty("x-fas-signature")));

            this.base.response = given().headers(headers).
                    when().body(BookAPIBodyUtil.createBookBodyLong(sourceX,sourceY,destinationX,destinationY)).post();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }

    @Then("I should see response body as following details")
    public void i_should_see_response_body_as_following_details(io.cucumber.datatable.DataTable dataTable) throws AssertionError {
        try{
            List<Map<String,String>> listOfResponseData = dataTable.asMaps();
            APIValidationUtil.validateBodyParameterWithIntValue(this.base.response,"car_id",Integer.parseInt(listOfResponseData.get(0).get("car_id")));
            APIValidationUtil.validateBodyParameterWithStringValue(this.base.response,"state",listOfResponseData.get(0).get("state"));
            APIValidationUtil.validateBodyParameterWithIntValue(this.base.response,"total_time",Integer.parseInt(listOfResponseData.get(0).get("total_time")));
        }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            BaseUtil.logger.log(Level.ERROR,"Response body is "+this.base.response.asString());
            throw e;
        }
    }

    @Then("I should see response body as given below")
    public void i_should_see_response_body_as_given_below(io.cucumber.datatable.DataTable dataTable) throws Exception {
        try{
            List<Map<String,String>> listOfResponseData = dataTable.asMaps();
            APIValidationUtil.validateBodyParameterWithIntValue(this.base.response,"car_id",Integer.parseInt(listOfResponseData.get(0).get("car_id")));
            APIValidationUtil.validateBodyParameterWithStringValue(this.base.response,"state",listOfResponseData.get(0).get("state"));
            APIValidationUtil.validateBodyParameterWithLongValue(this.base.response,"total_time",Long.parseLong(listOfResponseData.get(0).get("total_time")));
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            BaseUtil.logger.log(Level.ERROR,"Response body is "+this.base.response.asString());
            throw e;
        }
    }
}
