package listener;

import org.apache.logging.log4j.LogManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import utilities.BaseUtil;
import utilities.ConfigUtil;
import utilities.PropertiesUtil;

public class TestNGListenerImpl implements ITestListener {

    public void onStart(ITestContext context) {
        try {

            //Setting log4j2.xml property file path
            System.setProperty("log4j.configurationFile", System.getProperty("user.dir")+"/log4j2.xml");

            BaseUtil.logger = LogManager.getLogger(TestNGListenerImpl.class.getName());

            BaseUtil.logger.info("Loading properties file: "+ System.getProperty("user.dir") + ConfigUtil.CONFIG_PROPERTY_FILE_PATH);

            BaseUtil.props = PropertiesUtil.readPropertyFile(System.getProperty("user.dir")+ ConfigUtil.CONFIG_PROPERTY_FILE_PATH);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
