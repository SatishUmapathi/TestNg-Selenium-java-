package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

public class Parser {

    private RemoteWebDriver driver;

    // Constructor
    public Parser(RemoteWebDriver driver) {
        this.driver = driver;
    }

    // Method to perform a scroll (up or down)
    public void scroll(String direction) {
        if (direction.equalsIgnoreCase("down")) {
            driver.executeScript("window.scrollBy(0,250)", "");
            driver.executeScript("window.scrollBy(0,250)", "");
        } else if (direction.equalsIgnoreCase("up")) {
            driver.executeScript("window.scrollBy(0,-250)", "");
        }
    }

    // Custom swipe method using PointerActions
    public void customSwipe(int startX, int startY, int endX, int endY, int duration) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

        Sequence swipe = new Sequence(finger, 0);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(duration), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        List<Sequence> actions = new ArrayList<>();
        actions.add(swipe);

        Actions actionBuilder = new Actions(driver);
        actionBuilder.perform();
    }

    // Swipe left method (can be modified as needed)
    public void swipeLeft() {
        WebElement element = driver.findElement(By.xpath("//div[@class='swipeElement']"));
        int startX = element.getLocation().getX() + element.getSize().getWidth() - 1;
        int endX = element.getLocation().getX() + 1;
        int startY = element.getLocation().getY() + element.getSize().getHeight() / 2;
        int endY = startY;

        customSwipe(startX, startY, endX, endY, 200);  // Swipe left
    }

    // Swipe right method (can be modified as needed)
    public void swipeRight() {
        WebElement element = driver.findElement(By.xpath("//div[@class='swipeElement']"));
        int startX = element.getLocation().getX() + 1;
        int endX = element.getLocation().getX() + element.getSize().getWidth() - 1;
        int startY = element.getLocation().getY() + element.getSize().getHeight() / 2;
        int endY = startY;

        customSwipe(startX, startY, endX, endY, 200);  // Swipe right
    }

    // Method to swipe to a specific element (scrolling)
    public void swipeToElement(By by) {
        WebElement element = driver.findElement(by);
        while (!element.isDisplayed()) {
            scroll("down");
        }
    }

    // Method to get all elements by XPath and perform a swipe on them
    public void swipeThroughElements(String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        for (WebElement element : elements) {
            element.click();
            swipeLeft();  // Swipe left to next element
        }
    }

    // Method to zoom in by pinching
    public void zoomIn() {
        // Implement zoom functionality using multi-touch actions (this can be extended)
        System.out.println("Zooming In (Pinch gesture)");
    }

    // Method to zoom out by pinching
    public void zoomOut() {
        // Implement zoom functionality using multi-touch actions (this can be extended)
        System.out.println("Zooming Out (Pinch gesture)");
    }
}


