package listener;

import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utilities.BaseUtil;
import utilities.ConfigUtil;
import utilities.PostgresDBUtil;
import utilities.PropertiesUtil;

public class TestNGListenerImpl implements ITestListener {

    public void onStart(ITestContext context) {
        try {

            //Setting log4j2.xml property file path
            System.setProperty("log4j.configurationFile", System.getProperty("user.dir")+"/log4j2.xml");

            BaseUtil.logger = LogManager.getLogger(TestNGListenerImpl.class.getName());

            BaseUtil.logger.info("Loading properties file: "+ System.getProperty("user.dir") + ConfigUtil.CONFIG_PROPERTY_FILE_PATH);

            BaseUtil.props = PropertiesUtil.readPropertyFile(System.getProperty("user.dir")+ ConfigUtil.CONFIG_PROPERTY_FILE_PATH);

            //Setup connection to databases
            PostgresDBUtil customerDBConnection = new PostgresDBUtil();
            BaseUtil.customer_db_connection = customerDBConnection.connect(BaseUtil.props.getProperty("db.customer.table"));

            PostgresDBUtil orderDBConnection = new PostgresDBUtil();
            BaseUtil.order_db_connection = orderDBConnection.connect(BaseUtil.props.getProperty("db.order.table"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onFinish(ITestContext context){
        try{
            BaseUtil.customer_db_connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
