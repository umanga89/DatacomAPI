package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.filter.log.RequestLoggingFilter;
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

public class GetPupilsSteps extends BaseUtil {

    private BaseUtil base;
    private Map<String, Object> headers = new HashMap<>();
    private Map<String, Object> bookRequest = new HashMap<>();

    public GetPupilsSteps(BaseUtil base) {
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(GetPupilsSteps.class.getName());
        this.base.requestWriter = new StringWriter();
        this.base.requestCapture = new PrintStream(new WriterOutputStream(this.base.requestWriter));
    }

    @When("I send Get Pupils request")
    public void i_send_get_pupils_request() {
        try{

            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.GET_PUPILS);

            //send request
            this.base.response = given().filter(new RequestLoggingFilter(this.base.requestCapture)).and().
                    when().get();

            this.base.requestCapture.flush();

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");

        }
    }
}
