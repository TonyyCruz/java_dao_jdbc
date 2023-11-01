package application;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

  public static void main(String[] args) {
    SellerDao sellerDao = DaoFactory.createSellerDao();
    System.out.println("---------- Find Seller By Id ----------");
    Seller seller = sellerDao.findById(2);
    System.out.println(seller);
    System.out.println();
    System.out.println("---------- Find Sellers By department ----------");
    Department department = new Department(2, "Electronics");
    List<Seller> departmentSellers = sellerDao.findByDepartment(department);
    departmentSellers.forEach(System.out::println);
    System.out.println();
    System.out.println("---------- Find All Sellers ----------");
    List<Seller> sellerList = sellerDao.findAll();
    sellerList.forEach(System.out::println);
  }
}
