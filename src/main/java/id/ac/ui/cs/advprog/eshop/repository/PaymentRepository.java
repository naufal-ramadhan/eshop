package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.List;

public interface PaymentRepository extends Creatable<Payment>{
    Payment create(Payment car);
    List<Payment> findAll();
    Payment findById(String id);
    Payment delete(Payment payment);
}