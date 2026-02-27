package pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class MenuCategoryPO {
    protected Page page;
    protected LoginPO loginPO;
    protected HomePO homePO;
    private final Locator loginLink;
    private final Locator homeMenuItem;
    private final Locator myAccountLink;
    private final Locator cartCompareWishlistPopup;
    private final Locator cartCompareWishlistPopupCloseButton;

    public MenuCategoryPO(Page page) {
        this.page = page;
        this.loginLink = page.locator("//ul[contains(@class,'dropdown-menu show')]//span[contains(text(),'Login')]");
        this.homeMenuItem = page.locator("//div[@id='main-navigation']//div/span[@class='title' and contains(text(),'Home')]");
        this.myAccountLink = page.locator("//div[@id='main-navigation']//div/span[@class='title' and contains(text(),'My account')]");
        this.cartCompareWishlistPopup = page.locator("//div[@id='notification-box-top']//div[@class='toast-body']/div//p");
        this.cartCompareWishlistPopupCloseButton = page.locator("//div[@id='notification-box-top']//div[@class='toast-header']/button[@aria-label='Close']");
    }


    //Locator
    //    String loginLink ="//a[@class='icon-left both dropdown-item active']//div[@class='info']";
//    String loginLink = "//ul[contains(@class,'dropdown-menu show')]//span[contains(text(),'Login')]";
//    String myAccountLink = "//div[@id='main-navigation']//div/span[@class='title' and contains(text(),'My account')]"; //
//    String homeMenuItem =  "//div[@id='main-navigation']//div/span[@class='title' and contains(text(),'Home')]";
    //Methods
    public LoginPO navigateToLoginPage() {
        myAccountLink.hover();
        loginLink.click();
        return new LoginPO(page);
    }

    public HomePO navigateToHomePage() {
        homeMenuItem.click();
        return new HomePO(page);

    }

    public String getCartCompareWishlistPopupText() {
        return cartCompareWishlistPopup.textContent().replaceAll("\\s+", " ").trim();
    }

    public void clickToCartCompareWishlistClosePopup() {
        cartCompareWishlistPopupCloseButton.click();
    }

    public MyAccountPO clickMyAccountMenuItem() {
        myAccountLink.click();
        return new MyAccountPO(page);
    }

    public void openHomePage() {
        homeMenuItem.click();
    }

    public void waitForHomePageLoaded() {
//        page.locator("//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]").waitFor();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
}
