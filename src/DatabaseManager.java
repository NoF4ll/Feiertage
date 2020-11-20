import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import models.Feiertag;

public class DatabaseManager {
	
	private static DatabaseManager instance;
	
	public static DatabaseManager getInstance() {
		if(instance != null) {
			return instance;
		}
		instance = new DatabaseManager();
		return instance;
	}
	
	public Connection getDatabaseConnection(final int port, final String database) throws SQLException {
        System.out.println(String.format("[mysql]: Connecting to MySQL:%s - %s", port, database));
        return DriverManager.getConnection(
            String.format("jdbc:mysql://localhost:%s/%s?serverTimezone=UTC&useSSL=false", port, database),
            "root",
            "bichl601"
        );
    }
	
	public void releaseConnection(final Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
	
	public void insertIntoDatabase(final Connection connection, final Feiertag feiertag) throws SQLException {
		final String SQL = "insert into feiertage (Datum, Wochentag) values (?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(SQL)) {
			statement.setDate(1, feiertag.getSQLDatum());
			statement.setString(2, feiertag.getWochentag());
			statement.executeUpdate();
		}
		
	}
	
}
