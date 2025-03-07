package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;
    private List<Payment> payments;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepositoryImpl();
        payments = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        order.setStatus(OrderStatus.WAITING_PAYMENT.getValue());
    }

    @Test
    void testAddPaymentVoucherValid(){
        when(paymentRepository.create(payment)).thenReturn(payment);

        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertEquals(order.getStatus(), OrderStatus.WAITING_PAYMENT.getValue());

        Payment result = paymentService.addPayment(order, PaymentMethod.VOUCHER.getValue(), paymentData);

        assertNotNull(result);
        assertEquals(PaymentMethod.VOUCHER.getValue(), result.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(result.getOrder().getStatus(), OrderStatus.SUCCESS.getValue());
        assertEquals(order, result.getOrder());
    }

    @Test
    void testAddPaymentVoucherNotValid(){
        when(paymentRepository.create(payment)).thenReturn(payment);

        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "1234ABC5678");
        assertEquals(order.getStatus(), OrderStatus.WAITING_PAYMENT.getValue());

        Payment result = paymentService.addPayment(order, PaymentMethod.VOUCHER.getValue(), paymentData);

        assertNotNull(result);
        assertEquals(PaymentMethod.VOUCHER.getValue(), result.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(result.getOrder().getStatus(), OrderStatus.FAILED.getValue());
        assertEquals(order, result.getOrder());
    }

    @Test
    void testAddPaymentCashValid(){
        when(paymentRepository.create(payment)).thenReturn(payment);

        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("address", "New York");
        paymentData.put("deliveryFee", "100");
        assertEquals(order.getStatus(), OrderStatus.WAITING_PAYMENT.getValue());

        Payment result = paymentService.addPayment(order, PaymentMethod.CASH.getValue(), paymentData);

        assertNotNull(result);
        assertEquals(PaymentMethod.CASH.getValue(), result.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(result.getOrder().getStatus(), OrderStatus.SUCCESS.getValue());
        assertEquals(order, result.getOrder());
    }

    @Test
    void testAddPaymentCashNotValid(){
        when(paymentRepository.create(payment)).thenReturn(payment);

        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("deliveryFee", "");
        assertEquals(order.getStatus(), OrderStatus.WAITING_PAYMENT.getValue());

        Payment result = paymentService.addPayment(order, PaymentMethod.CASH.getValue(), paymentData);

        assertNotNull(result);
        assertEquals(PaymentMethod.CASH.getValue(), result.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(result.getOrder().getStatus(), OrderStatus.FAILED.getValue());
        assertEquals(order, result.getOrder());
    }

    @Test
    void testAddPaymentBankValid(){
        when(paymentRepository.create(payment)).thenReturn(payment);

        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "100");
        assertEquals(order.getStatus(), OrderStatus.WAITING_PAYMENT.getValue());

        Payment result = paymentService.addPayment(order, PaymentMethod.BANK.getValue(), paymentData);

        assertNotNull(result);
        assertEquals(PaymentMethod.BANK.getValue(), result.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(result.getOrder().getStatus(), OrderStatus.SUCCESS.getValue());
        assertEquals(order, result.getOrder());
    }

    @Test
    void testAddPaymentBankNotValid(){
        when(paymentRepository.create(payment)).thenReturn(payment);

        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("BankName", "");
        assertEquals(order.getStatus(), OrderStatus.WAITING_PAYMENT.getValue());

        Payment result = paymentService.addPayment(order, PaymentMethod.BANK.getValue(), paymentData);

        assertNotNull(result);
        assertEquals(PaymentMethod.BANK.getValue(), result.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(result.getOrder().getStatus(), OrderStatus.FAILED.getValue());
        assertEquals(order, result.getOrder());
    }

    @Test
    void testSetStatusSuccess(){
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("BankName", "");
        assertEquals(order.getStatus(), OrderStatus.WAITING_PAYMENT.getValue());

        Payment old = paymentService.addPayment(order, PaymentMethod.BANK.getValue(), paymentData);
        assertNotNull(old);
        assertEquals(PaymentMethod.BANK.getValue(), old.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), old.getStatus());
        assertEquals(old.getOrder().getStatus(), OrderStatus.FAILED.getValue());
        assertEquals(order, old.getOrder());

        Payment result = paymentService.setStatus(old, PaymentStatus.SUCCESS.getValue());
        assertNotNull(result);
        assertEquals(PaymentMethod.BANK.getValue(), result.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(result.getOrder().getStatus(), OrderStatus.SUCCESS.getValue());
        assertEquals(order, result.getOrder());
    }

    @Test
    void testSetStatusRejected(){
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "100");
        assertEquals(order.getStatus(), OrderStatus.WAITING_PAYMENT.getValue());

        Payment old = paymentService.addPayment(order, PaymentMethod.BANK.getValue(), paymentData);

        assertNotNull(old);
        assertEquals(PaymentMethod.BANK.getValue(), old.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), old.getStatus());
        assertEquals(old.getOrder().getStatus(), OrderStatus.SUCCESS.getValue());
        assertEquals(order, old.getOrder());

        Payment result = paymentService.setStatus(old, PaymentStatus.REJECTED.getValue());
        assertNotNull(result);
        assertEquals(PaymentMethod.BANK.getValue(), result.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(result.getOrder().getStatus(), OrderStatus.FAILED.getValue());
        assertEquals(order, result.getOrder());
    }

    @Test
    void testSetStatusNotValid() {
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "100");
        assertEquals(order.getStatus(), OrderStatus.WAITING_PAYMENT.getValue());

        Payment old = paymentService.addPayment(order, PaymentMethod.BANK.getValue(), paymentData);

        assertNotNull(old);
        assertEquals(PaymentMethod.BANK.getValue(), old.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), old.getStatus());
        assertEquals(old.getOrder().getStatus(), OrderStatus.SUCCESS.getValue());
        assertEquals(order, old.getOrder());

        assertThrows(IllegalArgumentException.class, () -> {
            Payment result = paymentService.setStatus(old, "PENDING");
        });
    }
}
