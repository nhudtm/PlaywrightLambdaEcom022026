package commons;

import com.microsoft.playwright.Page;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import pageObjects.HomePO;
import pageObjects.LoginPO;
import pageObjects.MenuCategoryPO;
import pageObjects.ProductDetailPO;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected Page page;
    protected HomePO homePO;
    protected LoginPO loginPO;
    protected MenuCategoryPO menuCategoryPO;
    protected ProductDetailPO productDetailPO;
    Properties properties;
    FileInputStream fis;

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
        properties = initProperties();
        String appUrl = properties.getProperty(url);
        page = new PlaywrightFactory().initBrowser(browserName, appUrl);
        homePO = new HomePO(page);

    }

    @AfterClass
    public void tearDown() {
        // Implement teardown logic to close Playwright browser
        page.context().browser().close();
    }

    //Read properties file
    public Properties initProperties() {
        properties = new Properties();
        String filePath = System.getProperty("user.dir") + "/src/test/resources/config/config.properties";
        try {
            fis = new FileInputStream(filePath);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    // Get screen size
    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }





}
