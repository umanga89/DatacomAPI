package utilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks extends BaseUtil {

    private BaseUtil base;

    public Hooks(BaseUtil base) {
        this.base = base;
    }

    @Before
    public void setup(Scenario scenario) throws Exception {
        BaseUtil.logger.info("Scenario Started: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        BaseUtil.logger.info("Scenario Ended: " + scenario.getName());
    }
}
