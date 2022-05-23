package utilities;

import org.apache.logging.log4j.Level;
import org.junit.Assert;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderDBUtil extends BaseUtil {

    public static void verifyCustomerAccountInDatabase(BigDecimal account_balance, String customer_uuid) throws SQLException {
        try{
            PreparedStatement pst = BaseUtil.order_db_connection.prepareStatement("select * from account where customer_id='"+customer_uuid+"'");
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                Assert.assertNotNull(rs.getInt("id"));
                BaseUtil.customer_account_id = rs.getInt("id");
                Assert.assertEquals(customer_uuid,rs.getString("customer_id"));
                Assert.assertEquals(account_balance,rs.getBigDecimal("balance"));
            }else{
                Assert.assertTrue( "account table did not return a record for the given customer uuid:"+customer_uuid, false);
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

    public static void verifyOrderInDatabase(int accountID, String isin, double price, double quantity, String status) throws SQLException {
        try{
            PreparedStatement pst = BaseUtil.order_db_connection.prepareStatement("select * from account_order where id = (select max(id) from account_order where account_id="+accountID+")");
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                Assert.assertNotNull(rs.getInt("id"));
                Assert.assertEquals(accountID, rs.getInt("account_id"));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                if(!rs.getString("date").contains(formatter.format(date))){
                    Assert.assertTrue("Date in database does not match with current date", false);
                }
                Assert.assertEquals(isin,rs.getString("isin"));
                Assert.assertEquals(price,rs.getDouble("price"),2);
                Assert.assertEquals(quantity,rs.getInt("quantity"),0);
                Assert.assertEquals(status,rs.getString("status"));
            }else{
                Assert.assertTrue( "account table did not return a record for the given customer uuid:"+customer_uuid, false);
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

    public static void deleteCustomerAccountFromDatabase(String customer_uuid) throws SQLException {
        try{
            PreparedStatement pst = BaseUtil.order_db_connection.prepareStatement("delete from account where customer_id='"+customer_uuid+"'");
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
