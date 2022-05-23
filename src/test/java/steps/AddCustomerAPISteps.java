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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AddCustomerAPISteps extends BaseUtil{

    private BaseUtil base;
    private Map<String,Object> headers = new HashMap<>();

    public AddCustomerAPISteps(BaseUtil base){
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(AddCustomerAPISteps.class.getName());
        this.base.requestWriter = new StringWriter();
        this.base.requestCapture = new PrintStream(new WriterOutputStream(this.base.requestWriter));
    }

    @Given("I populate add customer request")
    public void i_create_add_pupil_request() {
        try{

            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.ADD_CUSTOMER);

            BaseUtil.logger.log(Level.INFO,"Set Add Customer request URL as "+props.getProperty("base.path")+APIPaths.ADD_CUSTOMER);

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @When("I send add customer request with valid name and account balance")
    public void i_send_add_customer_request_with_following_details(io.cucumber.datatable.DataTable dataTable) {
        try{

            List<Map<String,String>> listOfData = dataTable.asMaps();
            String name = (listOfData.get(0).get("name"));
            String updated_name = "";
            BigDecimal account_balance;
            if(!name.equals("empty")){
                updated_name = CommonUtil.getNameWithTimeStamp(name.substring(0,name.indexOf("_")));
            }
            if(!listOfData.get(0).get("account_balance").equals("string")){
                account_balance = new BigDecimal(listOfData.get(0).get("account_balance"));
                this.base.account_balance = account_balance;
                this.base.response = given().when().queryParam("name",updated_name).queryParam("balance",account_balance).post();
            }else {
                this.base.response = given().when().queryParam("name",updated_name).queryParam("balance",listOfData.get(0).get("account_balance")).post();
            }

            this.base.customer_name = updated_name;
            //send request


            this.base.requestCapture.flush();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is");
        }
    }

    @Then("I can see customer saved in database")
    public void i_can_see_customer_saved_in_database() {
        try{
            CustomerDBUtil.verifyCustomerInDatabase(this.base.customer_name,this.base.customer_uuid);
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            }
    }

    @Then("I should see following values in response body")
    public void i_should_see_response_body_as_following_details(io.cucumber.datatable.DataTable dataTable) throws AssertionError {
        try{
            List<Map<String,String>> listOfResponseData = dataTable.asMaps();
            for(int i=0;i<listOfResponseData.size();i++){
                APIValidationUtil.validateBodyHasParameter(this.base.response,listOfResponseData.get(i).get("FIELD"));
                if(listOfResponseData.get(i).get("FIELD").equals("status")){
                    APIValidationUtil.validateBodyParameterWithIntValue(this.base.response,listOfResponseData.get(i).get("FIELD"),Integer.parseInt(listOfResponseData.get(i).get("VALUE")));
                }else if(listOfResponseData.get(i).get("FIELD").equals("timestamp")){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    APIValidationUtil.validateBodyParameterWithStringValueContainsValue(this.base.response, listOfResponseData.get(i).get("FIELD"), formatter.format(date));
                }else if(listOfResponseData.get(i).get("FIELD").equals("message")){
                    APIValidationUtil.validateBodyParameterWithStringValueContainsValue(this.base.response, listOfResponseData.get(i).get("FIELD"), "");
                }else {
                    APIValidationUtil.validateBodyParameterWithStringValue(this.base.response, listOfResponseData.get(i).get("FIELD"), listOfResponseData.get(i).get("VALUE"));
                }
            }

        }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see customer uuid in response body")
    public void i_should_see_customer_uuid() throws AssertionError {
        try{
            String uuid = BaseUtil.response.getBody().asString();
            Assert.assertNotNull(uuid,"Response body does not contain uuid");
            BaseUtil.customer_uuid = uuid;
            }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I can see customer account is saved in database with account balance {string}")
    public void iCanSeeCustomerAccountIsSavedWithAccountBalanceBalanceInDatabase(String account_balance) throws SQLException {
        try{
            BigDecimal acc_balance = new BigDecimal(account_balance);
            OrderDBUtil.verifyCustomerAccountInDatabase(acc_balance,this.base.customer_uuid);
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
    }

    @Then("I can see account balance of customer account updated correctly in database")
    public void iCanSeeAccountBalanceOfCustomerAccountUpdatedCorrectlyInDatabase() throws SQLException {
        try{
            double totalPriceOfOrder = this.base.quantity * this.base.product_price;
            BigDecimal accBalance = new BigDecimal(String.valueOf(this.base.account_balance));
            DecimalFormat df = new DecimalFormat("0.00");
            BigDecimal totalToDeduct = new BigDecimal(df.format(totalPriceOfOrder));
            BigDecimal updatedAccountBalance = accBalance.subtract(totalToDeduct);
            OrderDBUtil.verifyCustomerAccountInDatabase(updatedAccountBalance,this.base.customer_uuid);
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
    }

    @Then("I can see kafka consumer receives a message for the created customer account with customer uuid and account balance {string}")
    public void iCanSeeKafkaConsumerReceivesAMessageForTheCreatedCustomerAccountWithCustomerUuidAndAccountBalance(String account_balance) {
        try{
            List<String> kafkaMessages = KafkaUtil.getKafkaMessages(props.getProperty("kafka.customer.service.topic"),5);
            Assert.assertNotNull(kafkaMessages);
            String customerKafkaMessage = KafkaUtil.verifyKakfaMessageForCustomer(kafkaMessages,this.base.customer_uuid);
            Assert.assertEquals(customerKafkaMessage,"{\"customerId\":\""+this.base.customer_uuid+"\",\"balance\":"+account_balance+"}");
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
    }
}
