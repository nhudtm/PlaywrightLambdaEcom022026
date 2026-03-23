package auth;

import com.microsoft.playwright.*;
import commons.BaseTest;
import commons.PlaywrightFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import pageObjects.HomePO;
import pageObjects.MyAccountPO;
import utils.PropertiesConfig;

import java.nio.file.Paths;

public class Login extends BaseTest {


    PlaywrightFactory playwrightFactory;
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    HomePO homePO;
    MyAccountPO myAccountPO;
    String email = PropertiesConfig.getProp("email");
    String password = PropertiesConfig.getProp("password");

    @Parameters("url")
    @BeforeSuite
    public void beforeTest(String url) {
        String appUrl = PropertiesConfig.getProp(url);
        System.out.println("Login - Before Test");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browserContext = browser.newContext();
        page = browserContext.newPage();
        page.navigate(appUrl);

        homePO = new HomePO(page);
        myAccountPO = homePO.clickMyAccountMenuItem();
        myAccountPO.inputToEmailTextbox(email);
        myAccountPO.inputToPasswordTextbox(password);
        myAccountPO.clickLoginButton();

        browserContext.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("applogin.json")));
        browser.close();
        playwright.close();
    }


}
