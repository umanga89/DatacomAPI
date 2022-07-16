package utilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import java.sql.SQLException;

public class Hooks extends BaseUtil {

    private BaseUtil base;

    public Hooks(BaseUtil base) {
        this.base = base;
    }

    @Before
    public void setup(Scenario scenario) throws Exception {
        BaseUtil.logger.info("---------------------------------------------------------------------");
        BaseUtil.logger.info("Scenario Started: " + scenario.getName());
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @After
    public void tearDown(Scenario scenario) throws SQLException {
        BaseUtil.logger.info("Scenario Ended: " + scenario.getName());
    }
}
