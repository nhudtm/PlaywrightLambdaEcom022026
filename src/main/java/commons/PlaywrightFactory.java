package commons;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

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

    //ThreadLocal - parrallel execution
    public Page initBrowser(String browserName, String url) {
        tPlaywright.set(Playwright.create());
        switch (browserName) {
            case "chrome":
                tBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "firefox":
                tBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "safari":
                tBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "chromium":
                tBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)));
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
        switch (browserName) {
            case "chrome":
                tBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "firefox":
                tBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "safari":
                tBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "chromium":
                tBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)));
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
//            case "chrome":
//                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//                break;
//            case "firefox":
//                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
//                break;
//            case "safari":
//                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
//                break;
//            case "chromium":
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
