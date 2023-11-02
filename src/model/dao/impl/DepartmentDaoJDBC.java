package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import db.DB;
import db.DBException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
  private Connection connection;

  public DepartmentDaoJDBC() {}

  public DepartmentDaoJDBC(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void insert(Department obj) {
    String query = "INSERT INTO department (Name) VALUES (?)";
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;
    try {
      prepStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      prepStatement.setString(1, obj.getName());
      int affectedRows = prepStatement.executeUpdate();
      if (affectedRows == 0)
        throw new DBException("Insertion error, 0 rows affected.");
      resultSet = prepStatement.getGeneratedKeys();
      resultSet.next();
      int generatedId = resultSet.getInt(1);
      obj.setId(generatedId);
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    } finally {
      DB.closeStatement(prepStatement);
      DB.closeResultSet(resultSet);
    }
  }

  @Override
  public void update(Department obj) {
    String query = "UPDATE department SET Name = ? WHERE id = ?";
    PreparedStatement prepStatement = null;
    try {
      prepStatement = connection.prepareStatement(query);
      prepStatement.setString(1, obj.getName());
      prepStatement.setInt(2, obj.getId());
      prepStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    } finally {
      DB.closeStatement(prepStatement);
    }
  }

  @Override
  public void deleteById(Integer id) {
    String query = "DELETE FROM department WHERE Id = ?";
    PreparedStatement prepStatement = null;
    try {
      prepStatement = connection.prepareStatement(query);
      prepStatement.setInt(1, id);
      prepStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    } finally {
      DB.closeStatement(prepStatement);
    }
  }

  @Override
  public Department findById(Integer id) {
    String query = "SELECT * FROM department WHERE id = ?";
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;
    try {
      prepStatement = connection.prepareStatement(query);
      prepStatement.setInt(1, id);
      resultSet = prepStatement.executeQuery();
      if (!resultSet.next())
        return null;
      return departmentInstantiate(resultSet);
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    } finally {
      DB.closeStatement(prepStatement);
      DB.closeResultSet(resultSet);
    }
  }

  @Override
  public List<Department> findAll() {
    String query = "SELECT * FROM department";
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;
    List<Department> departmentList = new ArrayList<>();
    try {
      prepStatement = connection.prepareStatement(query);
      resultSet = prepStatement.executeQuery();
      while (resultSet.next()) {
        departmentList.add(departmentInstantiate(resultSet));
      }
      return departmentList;
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    } finally {
      DB.closeStatement(prepStatement);
      DB.closeResultSet(resultSet);
    }
  }

  private Department departmentInstantiate(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("Id");
    String name = resultSet.getString("Name");
    return new Department(id, name);
  }
}
