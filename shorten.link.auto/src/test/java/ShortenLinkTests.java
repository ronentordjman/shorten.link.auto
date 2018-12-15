import org.apache.commons.exec.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ShortenLinkTests {
    private String linkToShort;
    private static WebDriver driver;
    private String homePageUrl = "https://bitly.com";

    @BeforeTest
    public void beforeTest() throws Exception{
        Reporter.log("invoking chrome driver");

        String driverFileName = String.join(File.separator,"src","main",
                "resources","drivers","chromedriver");
        if (System.getProperty("os.name").startsWith("Windows")) {
            //windows os
            System.setProperty("webdriver.chrome.driver",
                    driverFileName + ".exe");
        }
        else {
            //linux os
            System.setProperty("webdriver.chrome.driver",
                    driverFileName);
        }
        driver = new ChromeDriver();
        navigateToHomePage();
    }

    public void loadParams(){
        InputStream input = null;
        String fileName = String.join(File.separator,"src","main",
                "resources","params.properties");
        try {
//            fileName =  System.getProperty("params");
            Reporter.log("params file name " + fileName);
            Properties prop = new Properties();
            input = new FileInputStream(fileName);
            prop.load(input);
            linkToShort = prop.getProperty("linkToShort");
        }
        catch (Exception e){
            Reporter.log("error loading params from file " + fileName);
        }
    }

    @Test
    public void shortLink(){
        loadParams();
        try {
            ShortenLinkPage shortenLinkPage = PageFactory.initElements(driver, ShortenLinkPage.class);
            Reporter.log("shorting link start");
            String shortedLink = shortenLinkPage.shortLink(linkToShort, driver);
            Reporter.log("navigating to the shorted link");
            driver.get(shortedLink);
        }
        catch (Exception e){
            Reporter.log("error while shorting link: " + e.getMessage(),false);
        }
        Reporter.log("verifying that the shorted link redirect to original link");
        String currentUrl = driver.getCurrentUrl();
        boolean linksEquals = currentUrl.equals(linkToShort);
        Assert.assertTrue(linksEquals,"Current Url " + currentUrl +
                " is not equals to expected Url: " + linkToShort);
    }

    @AfterTest
    public void afterTest() {
        System.out.print("quiting test");
        driver.quit();
    }

    protected void navigateToHomePage(){
        driver.get(homePageUrl);
    }
}
