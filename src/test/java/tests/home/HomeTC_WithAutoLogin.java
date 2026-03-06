package tests.home;

import commons.BaseTestAuth;
import jiraConfig.JiraCreateIssue;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomeTC_WithAutoLogin extends BaseTestAuth {
    String productName;

    @BeforeClass
    public void beforeClass() {
        productName = homePO.getFirstProductNameInTopProducts();
    }

    @JiraCreateIssue(isCreateIssue = true)
    @Test
    public void TC06_ActionAddProductToWishlistWithoutLogin() {
        homePO.hoverFirstProductInTopProducts();
        homePO.clickToAddToWishlistButtonInProductAction();

        System.out.println("-------------------");
        System.out.println(homePO.getLoginAlertMessageText());
        Assert.assertTrue(homePO.getLoginAlertMessageText().contains("your wish list!"));
    }


}
