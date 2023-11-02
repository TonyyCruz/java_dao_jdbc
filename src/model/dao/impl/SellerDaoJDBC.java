package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    String query = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) "
        + "VALUES (?, ?, ?, ?, ?)";
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;
    try {
      prepStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      prepStatement.setString(1, obj.getName());
      prepStatement.setString(2, obj.getEmail());
      prepStatement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
      prepStatement.setDouble(4, obj.getBaseSalary());
      prepStatement.setInt(5, obj.getDepartment().getId());
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
    }
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
        + "ON seller.DepartmentId = department.Id  WHERE seller.Id = ?;";
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
  public List<Seller> findByDepartment(Department department) {
    String query = "SELECT seller.* ,department.Name as DepName "
        + "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id "
        + "WHERE DepartmentId = ? AND department.Name = ? ORDER BY Name";
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;
    List<Seller> sellerList = new ArrayList<>();
    try {
      prepStatement = connection.prepareStatement(query);
      prepStatement.setInt(1, department.getId());
      prepStatement.setString(2, department.getName());
      resultSet = prepStatement.executeQuery();
      while (resultSet.next())
        sellerList.add(sellerInstantiate(resultSet, department));
      return sellerList;
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    } finally {
      DB.closeStatement(prepStatement);
      DB.closeResultSet(resultSet);
    }
  }

  @Override
  public List<Seller> findAll() {
    String query = "SELECT seller.* ,department.Name as DepName "
        + "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id "
        + "ORDER BY Id";
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;
    List<Seller> sellerList = new ArrayList<>();
    Map<Integer, Department> departmentMap = new HashMap<>();
    try {
      prepStatement = connection.prepareStatement(query);
      resultSet = prepStatement.executeQuery();
      while (resultSet.next()) {
        Integer departmentId = resultSet.getInt("DepartmentId");
        if (departmentMap.get(departmentId) == null)
          departmentMap.put(departmentId, departmentInstantiate(resultSet));
        sellerList.add(sellerInstantiate(resultSet, departmentMap.get(departmentId)));
      }
      return sellerList;
    } catch (SQLException e) {
      throw new DBException(e.getMessage());
    } finally {
      DB.closeStatement(prepStatement);
      DB.closeResultSet(resultSet);
    }
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
