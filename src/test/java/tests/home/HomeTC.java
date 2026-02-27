package tests.home;

import commons.BaseTest;
import commons.GlobalConstants;
import jiraConfig.JiraCreateIssue;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomeTC extends BaseTest {
    String productName;
    String homePageTitle;

    @BeforeClass
    public void beforeClass() {
        productName = homePO.getFirstProductNameInTopProducts();
    }


    @Test
    public void TC01_VerifyHomePageTitle() {
        // Implement test logic to verify home page title
        homePageTitle = homePO.getHomePageTitle();
        Assert.assertEquals(homePageTitle, GlobalConstants.HOME_PAGE_TITLE);
        String homePageTitle = homePO.getHomePageTitle();
    }

    @Test
    public void TC02_VerifyLoginLink() {
        // Implement test logic to verify login link presence
        loginPO = homePO.navigateToLoginPage();
        String loginPageTitle = loginPO.getLoginPageTitle();
        Assert.assertEquals(loginPageTitle, GlobalConstants.LOGIN_PAGE_TITLE);
        homePO = loginPO.navigateToHomePage();
    }


    @JiraCreateIssue(isCreateIssue = true)
    @Test
    public void TC03_ViewProductDetails() throws InterruptedException {
        // Implement test logic to view product details

        productDetailPO = homePO.clickOnFirstProductInTopProducts();
        Assert.assertEquals(productDetailPO.getProductPageTitleText(), productName);
        homePO = productDetailPO.navigateToHomePage();
        Thread.sleep(3000);
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Test
    public void TC04_AddProductToCart() throws InterruptedException {
        // Implement test logic to add product to cart

        homePO.hoverFirstProductInTopProducts();

        homePO.clickAddToCartButtonInProductAction();
        Assert.assertTrue(homePO.getCartCompareWishlistPopupText().replaceAll("\\s+", " ").trim().contains("Success: You have added " + productName + " to your shopping cart"));
        homePO.clickToCartCompareWishlistClosePopup();
//        Thread.sleep(3000);
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Test
    public void TC05_ActionQuickView() throws InterruptedException {
        // Implement test logic for quick view action

        homePO.hoverFirstProductInTopProducts();
        homePO.clickToQuickViewButtonInProductAction();
        Assert.assertEquals(homePO.getProductPopupTitleText(), productName);
        homePO.clickCloseIconInProductPopup();
//        Thread.sleep(3000);
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Test
    public void TC06_ActionAddProductToWishlistWithoutLogin() throws InterruptedException {
        // Implement test logic to add product to wishlist
        homePO.hoverFirstProductInTopProducts();
        homePO.clickToAddToWishlistButtonInProductAction();
        Assert.assertTrue(homePO.getLoginAlertMessageText().contains("You must login or create an account to save " + productName + " to your wish list"));
        homePO.clickLoginAlertClosePopup();
        Thread.sleep(3000);
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Test
    public void TC07_ActionAddProductToCompare() throws InterruptedException {
        // Implement test logic to add product to compare

        homePO.hoverFirstProductInTopProducts();
        homePO.clickToAddToCompareButtonInProductAction();
        System.out.println(homePO.getCartCompareWishlistPopupText());
        Assert.assertTrue(homePO.getCartCompareWishlistPopupText().contains("Success: You have added " + productName + " to your product comparison"));
        homePO.clickToCartCompareWishlistClosePopup();

    }

}
