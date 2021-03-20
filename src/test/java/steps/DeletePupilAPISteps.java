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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DeletePupilAPISteps extends BaseUtil{

    private BaseUtil base;
    private Map<String,Object> headers = new HashMap<>();
    private Map<String,Object> bookRequest = new HashMap<>();
    public DeletePupilAPISteps(BaseUtil base){
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(DeletePupilAPISteps.class.getName());
        this.base.requestWriter = new StringWriter();
        this.base.requestCapture = new PrintStream(new WriterOutputStream(this.base.requestWriter));
    }

    @Given("I populate delete pupil request")
    public void i_create_delete_pupil_request() {
        try{

            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.DELETE_PUPIL);

            BaseUtil.logger.log(Level.INFO,"Set Delete Pupil request URL as "+props.getProperty("base.path")+APIPaths.DELETE_PUPIL);

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            //throw e;
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @When("I send delete pupil request with {string} pupilId")
    public void i_send_delete_pupil_request_with_valid_pupilId(String key) {
        try {
             if(key.equals("non-existent")){
                 BaseUtil.pupilId = "12321";
            }else if(key.equals("empty")){
                 BaseUtil.pupilId = "";
             }

            //add default header
            headers.putAll(APIHeaderUtil.defaultHeaders());

            //getting basic auth values
            String username = APIHeaderUtil.basicAuthorizationHeader(props).get("username");
            String password = APIHeaderUtil.basicAuthorizationHeader(props).get("password");

            //send request
            this.base.response = given().headers(headers).auth().preemptive().basic(username, password).
                    when().delete(BaseUtil.pupilId);

            this.base.requestCapture.flush();
        } catch (Exception e) {
            BaseUtil.logger.log(Level.ERROR, "ERROR Occured =>\n" + e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            //throw e;
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @When("I send delete pupil request without basic authentication header")
    public void i_send_delete_pupil_request_without_basic_authentication_header() {
        try {
            //add default header
            headers.putAll(APIHeaderUtil.defaultHeaders());

            //send request
            this.base.response = given().headers(headers).
                    when().delete(BaseUtil.pupilId);

            this.base.requestCapture.flush();
        } catch (Exception e) {
            BaseUtil.logger.log(Level.ERROR, "ERROR Occured =>\n" + e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }
}
