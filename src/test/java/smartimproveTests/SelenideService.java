package smartimproveTests;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by inspiron on 01.08.2017.
 */
public class SelenideService {

    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "G:/Smart_Improve/src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverRunner.setWebDriver(driver);
    }

}
