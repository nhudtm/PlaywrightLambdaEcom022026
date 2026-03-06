package commons;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.testng.annotations.*;
import pageObjects.*;
import utils.PropertiesConfig;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static commons.PlaywrightFactory.getPage;

public class BaseTest {
    protected PlaywrightFactory playwrightFactory;
    protected Page page;
    protected HomePO homePO;
    protected LoginPO loginPO;
    protected MenuCategoryPO menuCategoryPO;
    protected ProductDetailPO productDetailPO;
    protected MyAccountPO myAccountPO;

    protected FileInputStream fis;



    @Parameters({"browserName", "url"})
    @BeforeClass
    public void setup(String browserName, String url) {
        // TH1 - Get Browser name and url from testng.xml file
        // (xml file có parameter name="url" value="https://ecommerce-playground.lambdatest.io")
//        page = new PlaywrightFactory().initBrowser(browserName,url);
//        homePO = new HomePO(page);
//
        // TH2 - Get Url from properties file
        // (xml file có parameter name="url" value="devUrl"
        // và properties file có key devUrl = https://ecommerce-playground.lambdatest.io)

        String appUrl = PropertiesConfig.getProp(url);
        playwrightFactory = new PlaywrightFactory();
        page = playwrightFactory.initBrowser(browserName, appUrl);

        homePO = new HomePO(page);

    }

    @AfterClass
    public void tearDown() {
        // Implement teardown logic to close Playwright browser
        page.context().browser().close();
    }

    //Read properties file
//    public static Properties initProperties() {
//        Properties prop = new Properties();
//        String filePath = System.getProperty("user.dir") + "/src/test/resources/config/config.properties";
//        try {
//            java.io.FileInputStream fis = new FileInputStream(filePath);
//            prop.load(fis);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return prop;
//    }

    // Get screen size
    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public int getRandomNumber() {
        return (int) (Math.random() * 1000);
    }

    public static String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        byte[] screenshot = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        String base64String = java.util.Base64.getEncoder().encodeToString(screenshot);
        return base64String;
    }

}
