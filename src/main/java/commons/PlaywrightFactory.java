package commons;

import java.awt.Dimension;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {


    public static ThreadLocal<Playwright> tPlaywright = new ThreadLocal<>();
    public static ThreadLocal<Browser> tBrowser = new ThreadLocal<>();
    public static ThreadLocal<BrowserContext> tBrowserContext = new ThreadLocal<>();
    public static ThreadLocal<Page> tPage = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return tPlaywright.get();
    }

    public static Browser getBrowser() {
        return tBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tBrowserContext.get();
    }

    public static Page getPage() {
        return tPage.get();
    }

    private boolean isHeadlessEnabled() {
        String headlessFromSystem = System.getProperty("headless");
        if (headlessFromSystem != null && !headlessFromSystem.isBlank()) {
            return Boolean.parseBoolean(headlessFromSystem);
        }
        return Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", "false"));
    }

    //ThreadLocal - parrallel execution
    public Page initBrowser(String browserName, String url) {
        tPlaywright.set(Playwright.create());
        boolean isHeadless = isHeadlessEnabled();
        switch (browserName) {
            case "chromium":
                tBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless)));
                break;
            case "firefox":
                tBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless)));
                break;
            case "safari":
                tBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless)));
                break;
            case "chrome":
                // Dùng chrome installed trên máy thay vì chromium mặc định của Playwright
                tBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(isHeadless)));

                break;
            default:
                System.out.println("Please pass the correct browser name: " + browserName);
                break;
        }
        // Set viewport size to match the screen size of main monitor
        Dimension screenSize = BaseTest.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        tBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(width, height)));
        tPage.set(getBrowserContext().newPage());
        getPage().navigate(url);
        return getPage();
    }

    //With Login state - ThreadLocal - parrallel execution
    public Page initBrowserWithAuth(String browserName, String url, String storagePath) {
        tPlaywright.set(Playwright.create());
        boolean isHeadless = isHeadlessEnabled();
        switch (browserName) {
            case "chromium":
                tBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless)));
                break;
            case "firefox":
                tBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless)));
                break;
            case "safari":
                tBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless)));
                break;
            case "chrome":
                tBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(isHeadless)));
                break;
            default:
                System.out.println("Please pass the correct browser name: " + browserName);
                break;
        }
        // Set viewport size to match the screen size of main monitor
        Dimension screenSize = BaseTest.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        tBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions()
                .setViewportSize(width, height)
                .setStorageStatePath(Paths.get(storagePath))));
        tPage.set(getBrowserContext().newPage());
        getPage().navigate(url);
        return getPage();
    }



    //Basic
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;


//    public Page initBrowser(String browserName, String url) {
//        playwright = Playwright.create();
//        switch (browserName){
//            case "chromium":
//                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//                break;
//            case "firefox":
//                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
//                break;
//            case "safari":
//                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
//                break;
//            case "chrome":
//                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
//                break;
//            default:
//                System.out.println("Please pass the correct browser name: " + browserName);
//                break;
//        }
//        // Set viewport size to match the screen size of main monitor
//        Dimension screenSize = BaseTest.getScreenSize();
//        int width = (int) screenSize.getWidth();
//        int height = (int) screenSize.getHeight();
//        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
//        page = browserContext.newPage();
//        page.navigate(url);
//
//        return page;
//    }


}
