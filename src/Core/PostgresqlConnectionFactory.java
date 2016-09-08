package Core;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Bruna Koch Schmitt on 08/09/2016.
 */
public class PostgresqlConnectionFactory implements ConnectionFactory {

    @Override
    public Connection getConnection() throws URISyntaxException, SQLException {
        String dbUrl = System.getenv("DATABASE_URL");
        return DriverManager.getConnection(dbUrl);
    }
}
