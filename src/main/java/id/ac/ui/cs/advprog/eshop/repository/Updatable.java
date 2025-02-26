package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface Updatable<T> {
    T update(String id, T entity);
}
