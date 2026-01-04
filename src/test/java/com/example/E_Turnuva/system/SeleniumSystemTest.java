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
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class SeleniumSystemTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Jenkins için arayüzsüz mod
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void testNavigationMenu() {
        // Senaryo 10: Navigasyon Menüsü Testi
        // 1. Ana sayfaya git
        driver.get("http://localhost:" + port);
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Sistem Özeti"), "Dashboard sayfası açılmadı!");

        // 2. Oyunlar sayfasına git
        driver.get("http://localhost:" + port + "/games");
        assertTrue(driver.getPageSource().contains("Oyun Yönetimi"), "Oyunlar sayfası açılmadı!");

        // 3. Takımlar sayfasına git
        driver.get("http://localhost:" + port + "/teams");
        assertTrue(driver.getPageSource().contains("Takım Yönetimi"), "Takımlar sayfası açılmadı!");

        // 4. Turnuvalar sayfasına git
        driver.get("http://localhost:" + port + "/tournaments");
        assertTrue(driver.getPageSource().contains("Turnuva Yönetimi"), "Turnuvalar sayfası açılmadı!");

        // 5. Başvurular sayfasına git
        driver.get("http://localhost:" + port + "/applications");
        assertTrue(driver.getPageSource().contains("Turnuva Başvurusu"), "Başvurular sayfası açılmadı!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}