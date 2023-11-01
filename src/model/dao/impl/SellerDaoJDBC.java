package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import db.DB;
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
  private Connection connection;

  public SellerDaoJDBC(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void insert(Seller obj) {
    // TODO Auto-generated method stub

  }

  @Override
  public void update(Seller obj) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteById(Integer id) {
    // TODO Auto-generated method stub

  }

  @Override
  public Seller findById(Integer id) {
    String query = "select seller.*, department.Name AS DepName from seller INNER join department "
        + "WHERE seller.Id = ?;";
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;
    try {
      prepStatement = connection.prepareStatement(query);
      prepStatement.setInt(1, id);
      resultSet = prepStatement.executeQuery();
      if (!resultSet.next())
        return null;
      Department department = departmentInstantiate(resultSet);
      return sellerInstantiate(resultSet, department);
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    } finally {
      DB.closeStatement(prepStatement);
      DB.closeResultSet(resultSet);
    }
  }

  @Override
  public List<Seller> findAll() {
    // TODO Auto-generated method stub
    return null;
  }

  private Department departmentInstantiate(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("DepartmentId");
    String name = resultSet.getString("DepName");
    return new Department(id, name);
  }

  private Seller sellerInstantiate(ResultSet resultSet, Department department) throws SQLException {
    int id = resultSet.getInt("Id");
    String name = resultSet.getString("Name");
    String email = resultSet.getString("Email");
    Date birthDate = resultSet.getDate("BirthDate");
    double salary = resultSet.getDouble("BaseSalary");
    return new Seller(id, name, email, birthDate, salary, department);
  }
}
