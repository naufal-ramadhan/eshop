package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment create(final Payment payment) {
        paymentData.add(payment);
        return payment;
    }

    public Payment findById(final String paymentId) {
        return paymentData.stream()
                .filter(payment -> payment.getId().equals(paymentId)).findFirst().orElse(null);
    }

    public Boolean existById(final String productId) {
        return paymentData.stream().anyMatch(product -> product.getId().equals(productId));
    }

    public Payment update(final Payment payment) {
        Payment old = findById(payment.getId());
        if (old == null){
            throw new NoSuchElementException("Payment not found");
        }

        this.paymentData.remove(old);
        this.paymentData.add(payment);
        return payment;
    }

    public Payment delete(final Payment payment) {
        paymentData.remove(payment);
        return payment;
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData);
    }
}
