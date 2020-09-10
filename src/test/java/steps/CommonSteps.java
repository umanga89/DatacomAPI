package steps;

import io.cucumber.java.en.Then;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import utilities.APIPaths;
import utilities.APIValidationUtil;
import utilities.BaseUtil;

public class CommonSteps extends BaseUtil {

    private BaseUtil base;

    public CommonSteps(BaseUtil base) {
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(CommonSteps.class.getName());
    }

    @Then("I should see response with status code as {int}")
    public void i_should_see_response_with_status_code_as(Integer statusCode) throws AssertionError {
        try {
            APIValidationUtil.validateStatusCode(this.base.response, statusCode);
        } catch (AssertionError e) {
            BaseUtil.logger.log(Level.ERROR, "Status code mismatch");
            BaseUtil.logger.log(Level.ERROR, e.getMessage());
            throw e;
        }
    }

    @Then("I should see empty response body")
    public void i_should_see_empty_response_body() throws AssertionError {
        try {
            APIValidationUtil.validateBodyIsEmpty(this.base.response);
        } catch (AssertionError e) {
            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
            BaseUtil.logger.log(Level.ERROR, e.getMessage());
            BaseUtil.logger.log(Level.ERROR, "Response body is " + this.base.response.asString());
            throw e;
        }
    }
}
