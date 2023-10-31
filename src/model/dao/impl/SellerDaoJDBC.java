package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
      Department department = new Department();
      department.setId(resultSet.getInt("DepartmentId"));
      department.setName(resultSet.getString("DepName"));
      Seller seller = new Seller();
      seller.setId(resultSet.getInt("Id"));
      seller.setName(resultSet.getString("Name"));
      seller.setEmail(resultSet.getString("Email"));
      seller.setBirthDate(resultSet.getDate("BirthDate"));
      seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
      seller.setDepartment(department);
      return seller;
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

}
