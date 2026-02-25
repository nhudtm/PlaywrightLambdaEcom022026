package pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePO extends MenuCategoryPO {

    private final Locator firstProductNameInTopProducts;
    private final Locator firstProductInTopProducts;
    private final Locator actionAddToCart;
    private final Locator actionQuickView;
    private final Locator productPopupTitle;
    private final Locator closeIconInProductPopup;
    private final Locator loginAlertMessage;
    private final Locator loginAlertClosePopup;
    private final Locator actionAddToCompare;
    private final Locator actionAddToWishlist;

    //Constructor
    public HomePO(Page page) {
        super(page);
        this.firstProductInTopProducts = page.locator("//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]");
        this.firstProductNameInTopProducts = page.locator("//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]//h4/a");
        this.actionAddToCart = page.locator("//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]//button[@title='Add to Cart']");
        this.actionQuickView = page.locator("//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]//button[@title='Quick view']");
        this.productPopupTitle = page.locator("//div[@id='entry_212948']/h1");
        this.closeIconInProductPopup = page.locator("//div[@id='quick-view']/div//button[@aria-label='close']");
        this.loginAlertMessage = page.locator("//div[@id='notification-box-top']//div[@class='toast-body']/div/p");
        this.loginAlertClosePopup = page.locator("//div[@id='notification-box-top']//div[contains(@class,'toast-header')]/button[@aria-label='Close']");
        this.actionAddToCompare = page.locator("//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]//button[@title='Compare this Product']");
        this.actionAddToWishlist = page.locator("//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]//button[@title='Add to Wish List']");
    }

    //String Locator
//    String firstProductNameInTopProducts = "//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]//h4/a";
//    String firstProductInTopProducts = "//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]";
//    String actionAddToCart = "//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]//button[@title='Add to Cart']";
//    String actionQuickView = "//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]//button[@title='Quick view']";
//    String productPopupTitle = "//div[@id='entry_212948']/h1";
//    String closeIconInProductPopup = "//div[@id='quick-view']/div//button[@aria-label='close']";
//    String loginAlertMessage = "//div[@id='notification-box-top']//div[@class='toast-body']/div/p";
//    String loginAlertClosePopup = "//div[@id='notification-box-top']//div[contains(@class,'toast-header')]/button[@aria-label='Close']";
//    String actionAddToCompare = "//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]//button[@title='Compare this Product']";
//    String actionAddToWishlist = "//div[@id='entry_218399']//div[contains(@class,'swiper-slide')][1]//button[@title='Add to Wish List']";

    //Methods
    public String getHomePageTitle() {
        return page.title();
    }

    public ProductDetailPO clickOnFirstProductInTopProducts() {
        firstProductNameInTopProducts.click();
        return new ProductDetailPO(page);
    }

    public String getFirstProductNameInTopProducts() {
        return firstProductNameInTopProducts.textContent();
    }

    public void hoverFirstProductInTopProducts() {
        firstProductInTopProducts.hover();
    }

    public void clickAddToCartButtonInProductAction() {
        actionAddToCart.click();
    }

    public void clickToQuickViewButtonInProductAction() {
        actionQuickView.click();
    }

    public String getProductPopupTitleText() {
        return productPopupTitle.textContent();
    }

    public void clickCloseIconInProductPopup() {
        closeIconInProductPopup.click();
    }

    public void clickToAddToWishlistButtonInProductAction() {
        actionAddToWishlist.click();
    }

    public String getLoginAlertMessageText() {
        return loginAlertMessage.textContent();
    }

    public void clickLoginAlertClosePopup() {
        loginAlertClosePopup.click();
    }

    public void clickToAddToCompareButtonInProductAction() {
        actionAddToCompare.click();
    }
}
