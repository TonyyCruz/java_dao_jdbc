package model.dao;

import java.util.List;

public interface IDao<T> {
  void insert(T obj);

  void update(T obj);

  void deleteById(Integer id);

  T findById(Integer id);

  List<T> findAll();
}
