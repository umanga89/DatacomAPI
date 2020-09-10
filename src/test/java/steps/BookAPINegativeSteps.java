package steps;

import io.cucumber.java.en.When;
import org.apache.logging.log4j.Level;
import utilities.APIHeaderUtil;
import utilities.BaseUtil;
import utilities.BookAPIBodyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookAPINegativeSteps extends BaseUtil {

    private BaseUtil base;
    private Map<String,Object> headers = new HashMap<>();
    private Map<String,Object> bookRequest = new HashMap<>();
    public BookAPINegativeSteps(BaseUtil base){
        this.base = base;
    }

    @When("I send Book request without source X parameter")
    public void i_send_book_request_without_source_x_parameter(io.cucumber.datatable.DataTable dataTable) {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            int sourceY = Integer.valueOf(listOfData.get(0).get("SourceY"));
            int destinationX = Integer.valueOf(listOfData.get(0).get("DestinationX"));
            int destinationY = Integer.valueOf(listOfData.get(0).get("DestinationY"));

            //creating headers
            headers.putAll(APIHeaderUtil.defaultHeaders());
            headers.putAll(APIHeaderUtil.xFasSignatureHeader(props.getProperty("x-fas-signature")));

            this.base.response = given().headers(headers).
                    when().body(BookAPIBodyUtil.createBookBodyIntegerWithoutSourceX(sourceY,destinationX,destinationY)).post();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }

    @When("I send Book request without source Y parameter")
    public void i_send_book_request_without_source_y_parameter(io.cucumber.datatable.DataTable dataTable) {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            int sourceX = Integer.valueOf(listOfData.get(0).get("SourceX"));
            int destinationX = Integer.valueOf(listOfData.get(0).get("DestinationX"));
            int destinationY = Integer.valueOf(listOfData.get(0).get("DestinationY"));

            //creating headers
            headers.putAll(APIHeaderUtil.defaultHeaders());
            headers.putAll(APIHeaderUtil.xFasSignatureHeader(props.getProperty("x-fas-signature")));

            this.base.response = given().headers(headers).
                    when().body(BookAPIBodyUtil.createBookBodyIntegerWithoutSourceY(sourceX,destinationX,destinationY)).post();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }

    @When("I send Book request without destination X parameter")
    public void i_send_book_request_without_destination_x_parameter(io.cucumber.datatable.DataTable dataTable) {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            int sourceX = Integer.valueOf(listOfData.get(0).get("SourceX"));
            int sourceY = Integer.valueOf(listOfData.get(0).get("SourceY"));
            int destinationY = Integer.valueOf(listOfData.get(0).get("DestinationY"));

            //creating headers
            headers.putAll(APIHeaderUtil.defaultHeaders());
            headers.putAll(APIHeaderUtil.xFasSignatureHeader(props.getProperty("x-fas-signature")));

            this.base.response = given().headers(headers).
                    when().body(BookAPIBodyUtil.createBookBodyIntegerWithoutDestinationX(sourceX,sourceY,destinationY)).post();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }

    @When("I send Book request without destination Y parameter")
    public void i_send_book_request_without_destination_y_parameter(io.cucumber.datatable.DataTable dataTable) {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            int sourceX = Integer.valueOf(listOfData.get(0).get("SourceX"));
            int sourceY = Integer.valueOf(listOfData.get(0).get("SourceY"));
            int destinationX = Integer.valueOf(listOfData.get(0).get("DestinationX"));

            //creating headers
            headers.putAll(APIHeaderUtil.defaultHeaders());
            headers.putAll(APIHeaderUtil.xFasSignatureHeader(props.getProperty("x-fas-signature")));

            this.base.response = given().headers(headers).
                    when().body(BookAPIBodyUtil.createBookBodyIntegerWithoutDestinationY(sourceX,sourceY,destinationX)).post();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }

    @When("I send Book request without source tag in request")
    public void i_send_book_request_without_source_tag_in_request(io.cucumber.datatable.DataTable dataTable) {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            int destinationX = Integer.valueOf(listOfData.get(0).get("DestinationX"));
            int destinationY = Integer.valueOf(listOfData.get(0).get("DestinationY"));

            //creating headers
            headers.putAll(APIHeaderUtil.defaultHeaders());
            headers.putAll(APIHeaderUtil.xFasSignatureHeader(props.getProperty("x-fas-signature")));

            this.base.response = given().headers(headers).
                    when().body(BookAPIBodyUtil.createBookBodyIntegerWithoutSourceTag(destinationX,destinationY)).post();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }

    @When("I send Book request without destination tag in request")
    public void i_send_book_request_without_destination_tag_in_request(io.cucumber.datatable.DataTable dataTable) {
        try{
            List<Map<String,String>> listOfData = dataTable.asMaps();
            int sourceX = Integer.valueOf(listOfData.get(0).get("SourceX"));
            int sourceY = Integer.valueOf(listOfData.get(0).get("SourceY"));

            //creating headers
            headers.putAll(APIHeaderUtil.defaultHeaders());
            headers.putAll(APIHeaderUtil.xFasSignatureHeader(props.getProperty("x-fas-signature")));

            this.base.response = given().headers(headers).
                    when().body(BookAPIBodyUtil.createBookBodyIntegerWithoutDestinationTag(sourceX,sourceY)).post();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw e;
        }
    }
}
