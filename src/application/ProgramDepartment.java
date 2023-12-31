package application;

import java.util.Date;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class ProgramDepartment {

  public static void main(String[] args) {
    SellerDao sellerDao = DaoFactory.createSellerDao();
    System.out.println("---------- Find Seller By Id ----------");
    Seller sellerById = sellerDao.findById(2);
    System.out.println(sellerById);
    System.out.println();
    System.out.println("---------- Find Sellers By department ----------");
    Department department = new Department(2, "Electronics");
    List<Seller> departmentSellers = sellerDao.findByDepartment(department);
    departmentSellers.forEach(System.out::println);
    System.out.println();
    System.out.println("---------- Find All Sellers ----------");
    List<Seller> sellerList = sellerDao.findAll();
    sellerList.forEach(System.out::println);
    System.out.println();
    System.out.println("---------- Seller Create ----------");
    Department departmentObj = new Department(1, "Computers");
    Seller sellerObj =
        new Seller(null, "Anthony Cruz", "tony@email.com", new Date(), 5500.00, departmentObj);
    sellerDao.insert(sellerObj);
    System.out.println(sellerObj);
    System.out.println();
    System.out.println("---------- Seller Update ----------");
    Seller sellerToUpdate = sellerDao.findById(sellerObj.getId());
    sellerToUpdate.setBaseSalary(6200.00);
    sellerToUpdate.setBirthDate(new Date("1988/02/25"));
    sellerDao.update(sellerToUpdate);
    System.out.println(sellerToUpdate);
    System.out.println();
    System.out.println("---------- Seller Delete ----------");
    sellerDao.deleteById(sellerToUpdate.getId() - 1);
  }
}
