package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

import java.util.Iterator;

public interface Findable<T> {
    Iterator<T> findAll();
    T findById(String id);
}
