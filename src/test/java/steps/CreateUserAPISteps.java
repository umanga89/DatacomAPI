package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import pojos.users.Address;
import pojos.users.Company;
import pojos.users.Geo;
import pojos.users.User;
import utilities.APIHelperUtil;
import utilities.APIPaths;
import utilities.APIValidationUtil;
import utilities.BaseUtil;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateUserAPISteps extends BaseUtil{

    private BaseUtil base;
    private Map<String,Object> headers = new HashMap<>();

    public CreateUserAPISteps(BaseUtil base){
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(CreateUserAPISteps.class.getName());
        this.base.requestWriter = new StringWriter();
        this.base.requestCapture = new PrintStream(new WriterOutputStream(this.base.requestWriter));
    }

    @Given("I populate create user request")
    public void i_create_create_user_request() throws Exception {
        try{

            //Set base uri
            APIHelperUtil.setBaseURI(props.getProperty("base.path"));

            //Set base path
            APIHelperUtil.setBasePath(APIPaths.CREATE_USER);

            BaseUtil.logger.log(Level.INFO,"Set Get User request URL as "+props.getProperty("base.path")+APIPaths.CREATE_USER);

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @When("I send create user request with following details")
    public void i_send_create_users_request_with_details(DataTable dataTable) throws Exception {
        try{
            List<List<String>> listOfData = dataTable.asLists(String.class);

            Geo geo = new Geo();
            geo.setLat(listOfData.get(8).get(1));
            geo.setLng(listOfData.get(9).get(1));

            Address address = new Address();
            address.setStreet(listOfData.get(4).get(1));
            address.setSuite(listOfData.get(5).get(1));
            address.setCity(listOfData.get(6).get(1));
            address.setZipcode(listOfData.get(7).get(1));
            address.setGeo(geo);

            Company company = new Company();
            company.setName(listOfData.get(12).get(1));
            company.setCatchPhrase(listOfData.get(13).get(1));
            company.setBs(listOfData.get(14).get(1));

            User user = new User();
            user.setName(listOfData.get(1).get(1));
            user.setUsername(listOfData.get(2).get(1));
            user.setEmail(listOfData.get(3).get(1));
            user.setAddress(address);
            user.setPhone(listOfData.get(10).get(1));
            user.setWebsite(listOfData.get(11).get(1));
            user.setCompany(company);

            this.base.response = given().header("Content-type", "application/json").body(user).when().post();

            this.base.requestCapture.flush();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new Exception(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

    @Then("I should see created user is returned in response with correct data")
    public void i_should_see_user_in_response(DataTable dataTable) {
        try{

            List<List<String>> listOfData = dataTable.asLists(String.class);
            APIValidationUtil.validateBodyHasParameter(this.base.response,"id");
            User user = this.base.response.as(User.class);
            Assert.assertEquals(user.getName(),listOfData.get(1).get(1));
            Assert.assertEquals(user.getUsername(),listOfData.get(2).get(1));
            Assert.assertEquals(user.getEmail(),listOfData.get(3).get(1));
            Assert.assertEquals(user.getAddress().getStreet(),listOfData.get(4).get(1));
            Assert.assertEquals(user.getAddress().getSuite(),listOfData.get(5).get(1));
            Assert.assertEquals(user.getAddress().getCity(),listOfData.get(6).get(1));
            Assert.assertEquals(user.getAddress().getZipcode(),listOfData.get(7).get(1));
            Assert.assertEquals(user.getAddress().getGeo().getLat(),listOfData.get(8).get(1));
            Assert.assertEquals(user.getAddress().getGeo().getLng(),listOfData.get(9).get(1));
            Assert.assertEquals(user.getPhone(),listOfData.get(10).get(1));
            Assert.assertEquals(user.getWebsite(),listOfData.get(11).get(1));
            Assert.assertEquals(user.getCompany().getName(),listOfData.get(12).get(1));
            Assert.assertEquals(user.getCompany().getCatchPhrase(),listOfData.get(13).get(1));
            Assert.assertEquals(user.getCompany().getBs(),listOfData.get(14).get(1));

        }catch (AssertionError e){
            BaseUtil.logger.log(Level.ERROR,"Response body assertion error");
            BaseUtil.logger.log(Level.ERROR,e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+this.base.response.asString()+"\n");
        }
    }

}
