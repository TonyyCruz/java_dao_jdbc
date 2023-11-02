package application;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class ProgramSeller {

  public static void main(String[] args) {
    DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
    System.out.println("---------- Find Department By Id ----------");
    Department departmentById = departmentDao.findById(2);
    System.out.println(departmentById);
    System.out.println();
    System.out.println("---------- Find All Departments ----------");
    List<Department> departmentList = departmentDao.findAll();
    departmentList.forEach(System.out::println);
    System.out.println();
    System.out.println("---------- Department Create ----------");
    Department departmentObj = new Department(null, "Departamento novo");
    departmentDao.insert(departmentObj);
    System.out.println(departmentObj);
    System.out.println();
    System.out.println("---------- Department Update ----------");
    Department departmentToUpdate = departmentDao.findById(departmentObj.getId());
    departmentToUpdate.setName("Departamento atualizado");
    departmentDao.update(departmentToUpdate);
    System.out.println(departmentToUpdate);
    System.out.println();
    System.out.println("---------- Department Delete ----------");
    departmentDao.deleteById(departmentToUpdate.getId() - 1);
  }
}
