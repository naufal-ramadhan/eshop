package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.ArrayList;

import java.util.List;

public class PaymentRepositoryImpl implements PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment create(final Payment payment) {return null;}

    public Payment findById(final String paymentId) {return null;}

    public Boolean existById(final String productId) {return null;}

    public Payment delete(final Payment payment) {return null;}

    public List<Payment> findAll() {return null;}
}
