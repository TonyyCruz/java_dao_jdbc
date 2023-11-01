package model.dao;

import java.util.List;
import model.entities.Department;
import model.entities.Seller;

public interface SellerDao extends IDao<Seller> {

  List<Seller> findByDepartment(Department department);
}
