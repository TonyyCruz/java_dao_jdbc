package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
  private static Connection connection = null;

  private DB() {}

  public static Connection getConnection() {
    if (!(connection instanceof Connection))
      makeConnection();
    return connection;
  }

  private static void makeConnection() {
    try {
      Properties properties = loadProperties();
      String url = properties.getProperty("dburl");
      connection = DriverManager.getConnection(url, properties);
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    }
  }

  private static Properties loadProperties() {
    try (FileInputStream fs = new FileInputStream("db.properties")) {
      Properties properties = new Properties();
      properties.load(fs);
      return properties;
    } catch (IOException e) {
      throw new DBException(e.getMessage());
    }
  }

  public static void closeConnection() {
    if (connection == null)
      return;
    try {
      connection.close();
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    }
  }

  public static void closeStatement(Statement statement) {
    if (statement == null)
      return;
    try {
      statement.close();
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    }
  }

  public static void closeResultSet(ResultSet resultSet) {
    if (resultSet == null)
      return;
    try {
      resultSet.close();
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    }
  }
}
