package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, String method, String status, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.paymentData = paymentData;
        this.order = order;

        if (method.equals("VOUCHER") || method.equals("BANK") || method.equals("CASH")) {
            this.method = method;
        } else {
            throw new IllegalArgumentException();
        }

        if (status.equals("SUCCESS") || status.equals("REJECTED")){
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
