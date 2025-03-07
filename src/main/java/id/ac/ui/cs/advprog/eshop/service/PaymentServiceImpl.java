package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepositoryImpl;
import id.ac.ui.cs.advprog.eshop.utils.PaymentDataValidator;
import id.ac.ui.cs.advprog.eshop.utils.VoucherCodeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepositoryImpl paymentRepository;

    @Autowired
    private OrderServiceImpl orderService;


    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData){

        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method");
        }

        if (!PaymentDataValidator.isValidPaymentData(method, paymentData)){
            orderService.updateStatus(order.getId(), OrderStatus.FAILED.getValue());
            order.setStatus(OrderStatus.FAILED.getValue());

            Payment payment = new Payment(UUID.randomUUID().toString(),method, PaymentStatus.REJECTED.getValue(), paymentData,order);
            paymentRepository.create(payment);
            return payment;
        }

        orderService.updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());
        order.setStatus(OrderStatus.SUCCESS.getValue());

        Payment payment = new Payment(UUID.randomUUID().toString(),method, PaymentStatus.SUCCESS.getValue(), paymentData,order);
        paymentRepository.create(payment);
        return payment;
    };

    @Override
    public Payment setStatus(Payment payment, String status){
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment method");
        }

        payment.setStatus(status);
        paymentRepository.update(payment);
        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }
        return payment;
    };

    @Override
    public Payment getPayment(String paymentId){
        return paymentRepository.findById(paymentId);
    };

    @Override
    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    };
}
