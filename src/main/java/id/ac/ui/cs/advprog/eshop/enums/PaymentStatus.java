package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    SUCCESS("SUCCESS"),
    REJECTED("REJECTED");

    private final String value;
    private PaymentStatus(String status) {
        this.value = status;
    }

    static public boolean contains(String value) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}