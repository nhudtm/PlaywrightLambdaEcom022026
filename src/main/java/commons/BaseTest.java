package commons;

import static commons.PlaywrightFactory.getPage;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.nio.file.Paths;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.microsoft.playwright.Page;

import pageObjects.HomePO;
import pageObjects.LoginPO;
import pageObjects.MenuCategoryPO;
import pageObjects.MyAccountPO;
import pageObjects.ProductDetailPO;
import utils.PropertiesConfig;

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

    // Get screen size - with fallback for headless CI environments
    public static Dimension getScreenSize() {
        try {
            return Toolkit.getDefaultToolkit().getScreenSize();
        } catch (HeadlessException e) {
            // Running on headless CI (no display). Return standard viewport size.
            // Can be overridden via environment variable: VIEWPORT_WIDTH, VIEWPORT_HEIGHT
            int width = Integer.parseInt(System.getenv().getOrDefault("VIEWPORT_WIDTH", "1920"));
            int height = Integer.parseInt(System.getenv().getOrDefault("VIEWPORT_HEIGHT", "1080"));
            return new Dimension(width, height);
        }
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
