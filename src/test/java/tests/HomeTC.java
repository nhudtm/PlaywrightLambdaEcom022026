package tests;

import commons.BaseTest;
import commons.GlobalConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTC extends BaseTest {

    @Test
    public void TC01_VerifyHomePageTitle() {
        // Implement test logic to verify home page title
        String homePageTitle = homePO.getHomePageTitle();
        Assert.assertEquals(homePageTitle, GlobalConstants.HOME_PAGE_TITLE);
    }

    @Test
    public void TC02_VerifyLoginLink(){
        // Implement test logic to verify login link presence
        loginPO = homePO.navigateToLoginPage();
        String loginPageTitle = loginPO.getLoginPageTitle();
        Assert.assertEquals(loginPageTitle, GlobalConstants.LOGIN_PAGE_TITLE);
        homePO = loginPO.navigateToHomePage();
    }


    @Test
    public void TC03_ViewProductDetails() throws InterruptedException {
        // Implement test logic to view product details
        String productName = homePO.getFirstProductNameInTopProducts();
        productDetailPO = homePO.clickOnFirstProductInTopProducts();
        Assert.assertEquals(productDetailPO.getProductPageTitleText(), productName);
        homePO = productDetailPO.navigateToHomePage();
        Thread.sleep(3000);
    }

    @Test
    public void TC04_AddProductToCart() throws InterruptedException {
        // Implement test logic to add product to cart
        String productName = homePO.getFirstProductNameInTopProducts();
        homePO.hoverFirstProductInTopProducts();
        homePO.clickAddToCartButtonInProductAction();
        Assert.assertTrue(homePO.getCartCompareWishlistPopupText().replaceAll("\\s+", " ").trim().contains("Success: You have added " + productName + " to your shopping cart"));
        homePO.clickToCartCompareWishlistClosePopup();
        Thread.sleep(3000);
    }

    @Test
    public void TC05_ActionQuickView() throws InterruptedException {
        // Implement test logic for quick view action
        String productName = homePO.getFirstProductNameInTopProducts();
        homePO.hoverFirstProductInTopProducts();
        homePO.clickToQuickViewButtonInProductAction();
        Assert.assertEquals(homePO.getProductPopupTitleText(), productName);
        homePO.clickCloseIconInProductPopup();
        Thread.sleep(3000);
    }

    @Test
    public void TC06_ActionAddProductToWishlistWithoutLogin() throws InterruptedException {
        // Implement test logic to add product to wishlist
        String productName = homePO.getFirstProductNameInTopProducts();
        homePO.hoverFirstProductInTopProducts();
        homePO.clickToAddToWishlistButtonInProductAction();
        Assert.assertTrue(homePO.getLoginAlertMessageText().replaceAll("\\s+", " ").trim().contains("You must login or create an account to save " + productName + " to your wish list"));
        homePO.clickLoginAlertClosePopup();
        Thread.sleep(3000);
    }

    @Test
    public void TC07_ActionAddProductToCompare() throws InterruptedException {
        // Implement test logic to add product to compare
        String productName = homePO.getFirstProductNameInTopProducts();
        homePO.hoverFirstProductInTopProducts();
        homePO.clickToAddToCompareButtonInProductAction();
        Assert.assertTrue(homePO.getCartCompareWishlistPopupText().replaceAll("\\s+", " ").trim().contains("Success: You have added " + productName + " to your product comparison"));
        homePO.clickToCartCompareWishlistClosePopup();
        Thread.sleep(3000);
    }

}
