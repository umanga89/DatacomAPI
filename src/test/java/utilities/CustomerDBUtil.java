package utilities;

import org.apache.logging.log4j.Level;
import org.junit.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDBUtil extends BaseUtil {

    public static void verifyCustomerInDatabase(String customerName, String customer_uuid) throws SQLException {
        try{
            PreparedStatement pst = BaseUtil.customer_db_connection.prepareStatement("select * from customer where customer_id='"+customer_uuid+"'");
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                Assert.assertEquals(customer_uuid,rs.getString("customer_id"));
                Assert.assertEquals(customerName,rs.getString("name"));
            }else{
                Assert.assertTrue( "customer table did not return a record for the given customer:"+customerName+" and uuid:"+customer_uuid, false);
            }
    } catch (SQLException e) {
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
        catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
    }

    public static void deleteCustomerFromDatabase(String customer_uuid) throws SQLException {
        try{
            PreparedStatement pst = BaseUtil.customer_db_connection.prepareStatement("delete from customer where customer_id='"+customer_uuid+"'");
            pst.executeUpdate();

        } catch (SQLException e) {
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
        catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
    }
}
