package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import static org.mockito.Mockito.*;

class EshopApplicationTests {

    @Test
    void testMainMethod() {
        // Mock SpringApplication untuk memastikan tidak ada efek samping saat dijalankan
        SpringApplication mockSpringApp = mock(SpringApplication.class);

        // Jalankan method main untuk memastikan kode tercover
        EshopApplication.main(new String[]{});
    }
}
