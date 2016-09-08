package Core;

import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Bruna Koch Schmitt on 08/09/2016.
 */
public interface ConnectionFactory {
    public java.sql.Connection getConnection() throws URISyntaxException, SQLException;
}
