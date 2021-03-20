package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import utilities.*;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AddPupilAPISteps extends BaseUtil{

    private BaseUtil base;
    private Map<String,Object> headers = new HashMap<>();

    public AddPupilAPISteps(BaseUtil base){
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(AddPupilAPISteps.class.getName());
        this.base.requestWriter = new StringWriter();
        this.base.requestCapture = new PrintStream(new WriterOutputStream(this.base.requestWriter));
    }

    @Given("I populate add pupil request")
    public void i_create_add_pupil_request() {
        try{

            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.ADD_PUPIL);

            BaseUtil.logger.log(Level.INFO,"Set Add Pupil request URL as "+props.getProperty("base.path")+APIPaths.ADD_PUPIL);

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @When("I send add pupil request with mandatory values")
    public void i_send_add_pupil_request_with_following_details(io.cucumber.datatable.DataTable dataTable) {
        try{

            List<Map<String,String>> listOfData = dataTable.asMaps();
            String firstName = (listOfData.get(0).get("firstName"));
            String lastName = (listOfData.get(0).get("lastName"));
            int gradeId = Integer.valueOf(listOfData.get(0).get("gradeId"));

            //add default header
            headers.putAll(APIHeaderUtil.defaultHeaders());

            //getting basic auth values
            String username = APIHeaderUtil.basicAuthorizationHeader(props).get("username");
            String password = APIHeaderUtil.basicAuthorizationHeader(props).get("password");

            //send request
            this.base.response = given().headers(headers).auth().preemptive().basic(username,password).
                    when().body(AddPupilAPIBodyUtil.addPupilMandatoryBodyInteger(firstName,lastName,gradeId)).post();

            this.base.requestCapture.flush();

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @When("I send add pupil request with mandatory values without basic authentication header")
    public void i_send_add_pupil_request_without_basic_auth_header(io.cucumber.datatable.DataTable dataTable) {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            String firstName = (listOfData.get(0).get("firstName"));
            String lastName = (listOfData.get(0).get("lastName"));
            int gradeId = Integer.valueOf(listOfData.get(0).get("gradeId"));

            //add default header
            headers.putAll(APIHeaderUtil.defaultHeaders());

            //send request
            this.base.response = given().headers(headers).
                                 when().body(AddPupilAPIBodyUtil.addPupilMandatoryBodyInteger(firstName,lastName,gradeId))
                                .post();

            this.base.requestCapture.flush();

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @When("I send add pupil request with all values")
    public void i_send_add_pupil_request_with_all_details(io.cucumber.datatable.DataTable dataTable) {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            String firstName = (listOfData.get(0).get("firstName"));
            String lastName = (listOfData.get(0).get("lastName"));
            int gradeId = Integer.valueOf(listOfData.get(0).get("gradeId"));
            String infix = (listOfData.get(0).get("infix"));
            int classId = Integer.valueOf(listOfData.get(0).get("classId"));
            String email = (listOfData.get(0).get("email"));
            boolean isDisabled = Boolean.valueOf(listOfData.get(0).get("isDisabled"));

            //add default header
            headers.putAll(APIHeaderUtil.defaultHeaders());

            //getting basic auth values
            String username = APIHeaderUtil.basicAuthorizationHeader(props).get("username");
            String password = APIHeaderUtil.basicAuthorizationHeader(props).get("password");

            //send request
            this.base.response = given().headers(headers).auth().preemptive().basic(username,password).
                    when().body(AddPupilAPIBodyUtil.addPupilAllBodyInteger(firstName,lastName,gradeId,infix,classId,email,isDisabled)).post();

            this.base.requestCapture.flush();

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see following values in response body")
    public void i_should_see_response_body_as_following_details(io.cucumber.datatable.DataTable dataTable) throws AssertionError {
        try{
            List<Map<String,String>> listOfResponseData = dataTable.asMaps();
            for(int i=0;i<listOfResponseData.size();i++){
                APIValidationUtil.validateBodyHasParameter(this.base.response,listOfResponseData.get(i).get("FIELD"));
                if(listOfResponseData.get(i).get("FIELD").equals("gradeId") || listOfResponseData.get(i).get("FIELD").equals("classId") || listOfResponseData.get(i).get("FIELD").equals("status")){
                    APIValidationUtil.validateBodyParameterWithIntValue(this.base.response,listOfResponseData.get(i).get("FIELD"),Integer.parseInt(listOfResponseData.get(i).get("VALUE")));
                }else if(listOfResponseData.get(i).get("FIELD").equals("isDisabled")){
                    APIValidationUtil.validateBodyParameterWithBooleanValue(this.base.response, listOfResponseData.get(i).get("FIELD"), Boolean.parseBoolean(listOfResponseData.get(i).get("VALUE")));
                }else {
                    APIValidationUtil.validateBodyParameterWithStringValue(this.base.response, listOfResponseData.get(i).get("FIELD"), listOfResponseData.get(i).get("VALUE"));
                }
            }
            //APIValidationUtil.validateBodyParameterWithStringValue(this.base.response,"state",listOfResponseData.get(0).get("state"));
            //APIValidationUtil.validateBodyParameterWithIntValue(this.base.response,"total_time",Integer.parseInt(listOfResponseData.get(0).get("total_time")));

        }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I save {string} from response")
    public void i_save_pupil_id_from_response(String key) throws AssertionError{
        try{
            BaseUtil.pupilId = APIValidationUtil.getValueForKey(this.base.response, key);

        }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see {string} field with value {string}")
    public void i_should_see_field_with_value(String key, String value) {
        String expectedValue = value;
        String actualValue="";
        try{
            if(value.contains("Pupil with ID:")){
                expectedValue = value.replaceAll("pupilId", BaseUtil.pupilId);
            }
            APIValidationUtil.validateBodyHasParameter(this.base.response, key);
            actualValue = APIValidationUtil.getValueForKey(this.base.response, key);
            Assert.assertEquals(actualValue,expectedValue);

        }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }
}
