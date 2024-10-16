import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LambdaTestParallelTest{
    private WebDriver driver;

    @Parameters({"browser", "version", "platform"})
    @BeforeClass
    @Step("Setting up browser: {browser}, version: {version}, platform: {platform}")
    public void setUp(String browser, String version, String platform) throws MalformedURLException {
        String username = "satishumapathi";
        String accessKey = "ndeW5tPC3b22DwpTcleeT44Xcdk58kRA8yF1rfA8atgbxMFm0l";


            ChromeOptions options = new ChromeOptions();
            options.setCapability(CapabilityType.BROWSER_NAME, browser);
            options.setCapability(CapabilityType.BROWSER_VERSION, version);
            options.setCapability("platformName", platform);
            options.setCapability("LT:Options", new HashMap<String, Object>() {{
                put("build", "LambdaTest Parallel Test");
                put("name", "Parallel Test on " + browser);
                put("console", true);
                put("performance", true);
            }});
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub"), options);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Navigate to the LambdaTest Dashboard")
    @Epic("EP001")
    @Feature("Feature1: Dashboard")
    @Story("Story: LambdaTest Dashboard Login")
    public void navigateToDashboard() {
        driver.get("https://www.lambdatest.com");
        driver.findElement(By.xpath("/html/body/div[1]/header/nav/div/div/div[2]/div/div/div[2]/a[1]")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[1]/div/form/div[1]/div/input")).sendKeys("satishumapathi@lambdatest.com", Keys.ENTER);
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[1]/div/form/div[2]/div/input")).sendKeys("Sathish@16", Keys.ENTER);
        System.out.println("Navigated to LambdaTest Dashboard on " + ((RemoteWebDriver) driver).getCapabilities().getBrowserName());
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
