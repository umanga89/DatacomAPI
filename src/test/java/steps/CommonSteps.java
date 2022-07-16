package steps;

import io.cucumber.java.en.Then;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
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
}
