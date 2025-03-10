package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.Iterator;


public interface CarRepository extends Creatable<Car>, Deletable, Findable<Car>, Updatable<Car> {
    Car create(Car car);
    Iterator<Car> findAll();
    Car findById(String id);
    Car update(String id, Car car);
    void delete(String id);
}
