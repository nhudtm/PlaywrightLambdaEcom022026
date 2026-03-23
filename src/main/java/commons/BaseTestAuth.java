package commons;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import pageObjects.*;
import utils.PropertiesConfig;

import java.nio.file.Paths;

import static commons.PlaywrightFactory.getPage;

public class BaseTestAuth {
    protected PlaywrightFactory playwrightFactory;
    protected Page page;
    protected HomePO homePO;


    @Parameters({"browserName", "url"})
    @BeforeClass
    public void setup(String browserName, String url) {
        String appUrl = PropertiesConfig.getProp(url);

        // Load storage state file từ target/storage-states (match với AuthStateGenerator)
        String storageStateFile = "target/storage-states/applogin-" + browserName.toLowerCase() + ".json";

        playwrightFactory = new PlaywrightFactory();
        page = playwrightFactory.initBrowserWithAuth(browserName, appUrl, storageStateFile);

        homePO = new HomePO(page);
    }

    @AfterClass
    public void tearDown() {
        // Đóng đầy đủ theo thứ tự: Page -> Context -> Browser -> Playwright
        if (page != null) {
            BrowserContext context = page.context();
            Browser browser = context.browser();

            page.close();
            context.close();
            browser.close();

            // Lấy Playwright từ ThreadLocal và đóng
            Playwright playwright = PlaywrightFactory.getPlaywright();
            if (playwright != null) {
                playwright.close();
            }

            // Xóa ThreadLocal để tránh memory leak
            PlaywrightFactory.tPage.remove();
            PlaywrightFactory.tBrowserContext.remove();
            PlaywrightFactory.tBrowser.remove();
            PlaywrightFactory.tPlaywright.remove();
        }
    }

    public static String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        byte[] screenshot = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        return java.util.Base64.getEncoder().encodeToString(screenshot);
    }

}
