package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import utilities.*;

import java.io.PrintStream;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AddOrderAPISteps extends BaseUtil {

    private BaseUtil base;
    private Map<String, Object> headers = new HashMap<>();
    private Map<String, Object> bookRequest = new HashMap<>();

    public AddOrderAPISteps(BaseUtil base) {
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(AddOrderAPISteps.class.getName());
        this.base.requestWriter = new StringWriter();
        this.base.requestCapture = new PrintStream(new WriterOutputStream(this.base.requestWriter));
    }

    @Then("I send new order request with valid isin and quantity")
    public void iSendNewOrderRequestWithValidIsinAndQuantity(io.cucumber.datatable.DataTable dataTable) {
        try{

            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.ADD_ORDER);

            BaseUtil.logger.log(Level.INFO,"Set Add Order request URL as "+props.getProperty("base.path")+APIPaths.ADD_ORDER);

            List<Map<String,String>> listOfData = dataTable.asMaps();
            String isin = (listOfData.get(0).get("isin"));
            String updated_isin = "";
            int quantity = 0;
            if(isin.equals("empty")){
                updated_isin = isin;
            }else{
                updated_isin = CommonUtil.getNameWithTimeStamp(isin.substring(0,isin.indexOf("_")));
            }
            this.base.isin = updated_isin;
            if(!listOfData.get(0).get("quantity").equals("string")){
                quantity = Integer.valueOf(listOfData.get(0).get("quantity"));
                this.base.response = given().when().queryParam("customerId",this.base.customer_uuid).queryParam("isin",updated_isin).queryParam("quantity",quantity).post();
            }else {
                //this.base.response = given().when().queryParam("name",updated_name).queryParam("balance",listOfData.get(0).get("account_balance")).post();
            }
            this.base.quantity = quantity;
            this.base.requestCapture.flush();

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR, "Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is");
        }
    }

    @Then("I can see kafka consumer receives a message for the created order with isin {string} , quantity {int} and status {string}")
    public void iCanSeeKafkaConsumerReceivesAMessageForTheCreatedCustomerAccountWithCustomerUuidAndAccountBalance(String passedIsin, int quantity, String status) {
        try{
            BaseUtil.logger.log(Level.INFO,"starting kakfa record fetch");
            System.out.println("Yay it is starting");
            List<String> kafkaMessages = KafkaUtil.getKafkaMessages(props.getProperty("kafka.create.order.topic"),5);
            Assert.assertNotNull(kafkaMessages);
            String orderKafkaMessage = KafkaUtil.verifyKakfaMessageForOrder(kafkaMessages,this.base.isin);
            BaseUtil.logger.log(Level.INFO,"kakfa record fetch completed");
            String[] orderMessageArray = orderKafkaMessage.split(",");
            double isin_price = Double.valueOf(orderMessageArray[3].substring(orderMessageArray[3].indexOf(":")+1,orderMessageArray[3].length()));
            this.base.product_price = isin_price;
            String croppedKafkaMessage = orderKafkaMessage.substring(0,orderKafkaMessage.lastIndexOf(" "));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            Assert.assertEquals(croppedKafkaMessage,"{\"accountId\":"+this.base.customer_account_id+",\"isin\":\""+this.base.isin+"\",\"quantity\":"+quantity+",\"price\":"+isin_price+",\"status\":\""+status+"\",\"date\":\""+formatter.format(date));
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
    }

    @Then("I can see kafka consumer receives a message for the created order with isin {string} , quantity {int} and action {string}")
    public void iCanSeeKafkaConsumerReceivesAMessageForTheCreatedOrderWithDetails(String passedIsin, int quantity, String action) {
        try{
            List<String> kafkaMessages = KafkaUtil.getKafkaMessages(props.getProperty("kafka.order.request.topic"),20);
            Assert.assertNotNull(kafkaMessages);
            String orderKafkaMessage = KafkaUtil.verifyKakfaMessageForOrder(kafkaMessages,this.base.isin);
            Assert.assertEquals(orderKafkaMessage,"{\"customerId\":\""+this.base.customer_uuid+"\",\"isin\":\""+this.base.isin+"\",\"quantity\":"+quantity+",\"action\":\""+action+"\"}");
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
    }

    @And("I can see order data is saved in database")
    public void iCanSeeOrderDataIsSavedInDatabase(io.cucumber.datatable.DataTable dataTable) throws SQLException {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            double quantity = Double.valueOf(listOfData.get(0).get("quantity"));
            String status = listOfData.get(0).get("status");
            OrderDBUtil.verifyOrderInDatabase(this.base.customer_account_id,this.base.isin,this.base.product_price,quantity,status);
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
    }
}
