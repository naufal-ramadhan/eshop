package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    VOUCHER("VOUCHER"),
    CASH("CASH"),
    BANK("BANK");

    private final String value;
    private PaymentMethod(String value) {
        this.value = value;
    }

    static public boolean contains(String value) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}