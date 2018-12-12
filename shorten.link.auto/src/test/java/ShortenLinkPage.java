import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class ShortenLinkPage {

    @FindBy(id = "shorten_url")
    private WebElement linkInputBox;
    @FindBy(id = "shorten_btn")
    private WebElement shortenButton;
    String shortedLinkTextBoxLocator =
            "#\\32 Pvo23O > div.unauth_capsule.clearfix > a.short-url";
    private WebElement shortedLinkTextBox;

    public String shortLink(String linkToShort, WebDriver driver) throws Exception{
        Reporter.log("typing link to short " + linkToShort);
        linkInputBox.sendKeys(linkToShort);
        //using sleep to sync the sync with the typed text
        Thread.sleep(500);
        Reporter.log("clicking shorted button ");
        shortenButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Reporter.log("waiting the shorted link to appear");
        shortedLinkTextBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(shortedLinkTextBoxLocator)));
        Reporter.log("getting shorted link");
        String shortedLink = shortedLinkTextBox.getText();
        return shortedLink;
    }
}
