package id.ac.ui.cs.advprog.eshop.utils;

public class VoucherCodeValidator {
    private static final String PREFIX = "ESHOP";
    private static final int LENGTH = 16;

    public static boolean isValidVoucher(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != LENGTH) {
            return false;
        }
        if (!voucherCode.startsWith(PREFIX)) {
            return false;
        }
        long numericCount = voucherCode.chars()
                .filter(Character::isDigit)
                .count();
        return numericCount == 8;
    }
}