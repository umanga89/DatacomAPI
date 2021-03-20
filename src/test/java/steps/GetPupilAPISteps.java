package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import utilities.APIHeaderUtil;
import utilities.APIHelperUtil;
import utilities.APIPaths;
import utilities.BaseUtil;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetPupilAPISteps extends BaseUtil{

    private BaseUtil base;
    private Map<String,Object> headers = new HashMap<>();
    private Map<String,Object> bookRequest = new HashMap<>();
    public GetPupilAPISteps(BaseUtil base){
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(GetPupilAPISteps.class.getName());
        this.base.requestWriter = new StringWriter();
        this.base.requestCapture = new PrintStream(new WriterOutputStream(this.base.requestWriter));
    }

    @Given("I populate get pupil request")
    public void i_create_get_pupil_request() {
        try{

            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.GET_PUPIL);

            BaseUtil.logger.log(Level.INFO,"Set Get Pupil request URL as "+props.getProperty("base.path")+APIPaths.GET_PUPIL);

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @When("I send get pupil request with {string} pupilId")
    public void i_send_get_pupil_request_with_valid_pupilId(String key) {
        try {
             if(key.equals("non-existent")){
                 BaseUtil.pupilId = "12321";
            }else if(key.equals("empty")){
                 BaseUtil.pupilId = "";
             }

            //add default header
            headers.putAll(APIHeaderUtil.defaultHeaders());

            //send request
            this.base.response = given().headers(headers).
                    when().get(BaseUtil.pupilId);

            this.base.requestCapture.flush();

        } catch (Exception e) {
            BaseUtil.logger.log(Level.ERROR, "ERROR Occured =>\n" + e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }
}
