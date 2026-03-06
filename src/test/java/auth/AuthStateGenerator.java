package auth;

import com.microsoft.playwright.*;
import pageObjects.HomePO;
import pageObjects.MyAccountPO;
import utils.PropertiesConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AuthStateGenerator {

    /**
     * Generate authentication state and save to file.
     * File được đặt tên theo browser để tránh conflict khi chạy parallel.
     *
     * @param browserName chrome, firefox, safari, chromium
     * @return đường dẫn tới file storage state đã tạo
     */
    public static String generateState(String browserName) {
        String appUrl = PropertiesConfig.getProp("devUrl");
        String email = PropertiesConfig.getProp("email");
        String password = PropertiesConfig.getProp("password");

        System.out.println("Generating auth state for browser: " + browserName);

        Playwright playwright = Playwright.create();
        Browser browser;
        switch (browserName.toLowerCase()) {
            case "chrome":
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "safari":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate(appUrl);

        //Login steps
        HomePO homePO = new HomePO(page);
        MyAccountPO myAccountPO = homePO.clickMyAccountMenuItem();
        myAccountPO.inputToEmailTextbox(email);
        myAccountPO.inputToPasswordTextbox(password);
        myAccountPO.clickLoginButton();

        // Tạo file state trong target/storage-states để giữ project root sạch
        String stateFileName = "target/storage-states/applogin-" + browserName.toLowerCase() + ".json";
        Path statePath = Paths.get(stateFileName);

        try {
            // Tạo thư mục target/storage-states nếu chưa có
            Files.createDirectories(statePath.getParent());
        } catch (Exception e) {
            throw new RuntimeException("Cannot create directory for storage state", e);
        }

        browserContext.storageState(new BrowserContext.StorageStateOptions().setPath(statePath));

        // Đóng đầy đủ để tránh leak
        page.close();
        browserContext.close();
        browser.close();
        playwright.close();

        System.out.println("Auth state saved to: " + stateFileName);
        return stateFileName;
    }
}


