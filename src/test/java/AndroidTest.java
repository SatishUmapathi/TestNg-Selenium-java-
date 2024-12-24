import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Parser;

import java.net.URL;
import java.util.HashMap;

public class AndroidTest {
    public String username = "satishumapathi";  // Replace with your username
    public String accesskey = "ndeW5tPC3b22DwpTcleeT44Xcdk58kRA8yF1rfA8atgbxMFm0l";  // Replace with your access key
    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    boolean status = false;
    public Parser parser;

    @Parameters({"browserName", "platformName", "deviceName"})
    @BeforeClass
    @Step("Setting up browser: {browser}, version: {version}, platform: {platform}")
    public void setUp(String browser, String platform, String deviceName) throws Exception {
        ChromeOptions chromeOptions = null;
        FirefoxOptions firefoxOptions = null;
        EdgeOptions edgeOptions = null;
        SafariOptions safariOptions = null;
        switch (browser) {
            case "chrome":
                chromeOptions = new ChromeOptions();
                break;
            case "firefox":
                firefoxOptions = new FirefoxOptions();
                break;
            case "safari":
                safariOptions = new SafariOptions();
                break;
            case "edge":
                edgeOptions = new EdgeOptions();
                break;
            default:
                System.out.println("no such options");
                break;
        }

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", accesskey);
        ltOptions.put("build", "testNg build running on " + platform);
        ltOptions.put("name", platform + " AndroidTestSample " + browser);
        ltOptions.put("platformName", platform);
        ltOptions.put("deviceName", deviceName);  // Use a real Android device
        ltOptions.put("browserName", browser);
        ltOptions.put("browserVersion", "latest");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);

        switch (browser) {
            case "chrome":
                chromeOptions.setCapability("LT:Options", ltOptions);
                driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), chromeOptions);
                break;
            case "firefox":
                firefoxOptions.setCapability("LT:Options", ltOptions);
                driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), firefoxOptions);
                break;
            case "safari":
                safariOptions.setCapability("LT:Options", ltOptions);
                driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), safariOptions);
                break;
            case "edge":
                edgeOptions.setCapability("LT:Options", ltOptions);
                driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), edgeOptions);
                break;
            default:
                System.out.println("no such options");
                break;
        }

        // Initialize the Parser class with the driver to use its methods
        parser = new Parser(driver);
    }

    @Test
    public void testSimple() throws Exception {
        try {
            driver.get("https://www.lambdatest.com/");

//            // Let's mark done the first two items in the list.
//            driver.findElement(By.name("li1")).click();
//            driver.findElement(By.name("li2")).click();
//
//            // Let's add an item to the list.
//            driver.findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
//            driver.findElement(By.id("addbutton")).click();
//
//            // Let's check that the item we added is in the list.
//            String enteredText = driver.findElement(By.xpath("/html/body/div/div/div/ul/li[6]/span")).getText();
//            if (enteredText.equals("Yey, Let's add it to list")) {
//                status = true;
//            }

            // Using Parser class to perform additional actions, like scroll or swipe
            parser.scroll("down"); // Scroll down the page

            // Optional: Use parser to swipe (example: swipe left)
            parser.customSwipe(100, 200, 300, 400, 2);  // Swipe from (100,200) to (300,400)

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}
