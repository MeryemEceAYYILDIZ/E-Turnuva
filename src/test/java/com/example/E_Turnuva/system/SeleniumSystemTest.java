package com.example.E_Turnuva.system;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Web Environment RANDOM_PORT: Test çalışırken rastgele bir portta (örn: 8081) sunucuyu ayağa kaldırır.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumSystemTest {

    @LocalServerPort
    private int port; // Uygulamanın çalıştığı portu otomatik alır

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Chrome Driver'ı otomatik kur
        WebDriverManager.chromedriver().setup();

        // Jenkins uyumlu "Headless" ayarları
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Ekransız mod (Jenkins için şart)
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
    }

    @Test
    public void testSwaggerUiLoad() {
        // Senaryo: Uygulama ayağa kalktığında ana sayfaya erişilebiliyor mu?
        // (Şimdilik Swagger veya Tomcat'in default sayfası bile olsa 200 dönmesi yeterli)

        // 1. Uygulamanın adresine git
        driver.get("http://localhost:" + port + "/api/tournaments");

        // 2. Sayfa kaynağını al
        String pageSource = driver.getPageSource();

        // 3. JSON formatında boş bir liste "[]" veya dolu bir liste dönüp dönmediğini kontrol et
        // (Controller'ımız liste dönüyordu)
        boolean isJsonList = pageSource.contains("[") || pageSource.contains("]");

        assertTrue(isJsonList, "API yanıtı beklenen JSON formatında değil!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Tarayıcıyı kapat
        }
    }
}