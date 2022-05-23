package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDBUtil extends BaseUtil{
    private final String dbHost = props.getProperty("db.host");
    private final String dbPort = props.getProperty("db.port");
    private final String url = "jdbc:postgresql://"+dbHost+":"+dbPort+"/";
    private final String user = props.getProperty("db.username");
    private final String password = props.getProperty("db.password");

    public Connection connect(String dbName) throws SQLException {
        return DriverManager.getConnection(url+dbName, user, password);
    }
}
