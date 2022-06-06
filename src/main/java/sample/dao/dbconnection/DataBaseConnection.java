package sample.dao.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static Connection connection = null;

    static
    {
        try {
            connection = DriverManager
                    .getConnection(DbConnectionConst.DATABASE_URL, DbConnectionConst.DATABASE_USERNAME,
                            DbConnectionConst.DATABASE_PASSWORD);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection()
    {
        return connection;
    }
}
