//Patcharin Khangwicha 6410406797
package th.ac.ku.book;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.h2.mvstore.Page;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddBookTest {

    @LocalServerPort
    private Integer port; //เอา ramdom part มาใส่
    private static WebDriver driver;
    private static WebDriverWait wait;

    @FindBy(id = "nameInput") //WebElement นี้หาด้วย id ที่ชื่อ "nameInput"
    private WebElement nameField;

    @FindBy(id = "authorInput")
    private WebElement authorField;

    @FindBy(id = "priceInput")
    private WebElement priceField;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    @Test
    void testAddBook() { //wait.until = รอจนกว่าจะหาเจอ + โหลดเสร็จ

        //senKeys = ใส่ข้อมูล
        nameField.sendKeys("Clean Code");
        authorField.sendKeys("Robert Martin");
        priceField.sendKeys("1000"); //ใส่เป็น string ไปเพราะืุกอย่างใน html มันเป็น string

        submitButton.click(); //จาก addBook พอกดปุ๊บมันจะไปหน้าใหม่ซึ่งจะเก็บค่าที่เราใส่เข้าไปไว้ในตารางตาม html เลย


        //เอา xpath มาใช้แทน การหาจาก id
        WebElement name = wait.until(webDriver -> webDriver
                .findElement(By.xpath("//table/tbody/tr[1]/td[1]")));
        WebElement author = driver
                .findElement(By.xpath("//table/tbody/tr[1]/td[2]"));
        WebElement price = driver
                .findElement(By.xpath("//table/tbody/tr[1]/td[3]"));

        assertEquals("Clean Code", name.getText());
        assertEquals("Robert Martin", author.getText());
        assertEquals("600.00", price.getText()); //เวลามันแสดงในหน้าเว็บมันใช้ .00

    }


    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/book/add"); //ไป url นี้
        PageFactory.initElements(driver,this); //ประมาณว่าสร้าง element ของ page
    }

    @AfterEach
    public void afterEach() throws InterruptedException {
        Thread.sleep(3000); //รอให้เราดูก่อนมันจะปิด
    }

    @AfterAll
    public static void afterAll() {
        if (driver != null)
            driver.quit(); //ให้มันปิด
    }

}

