package tests;

import commons.BaseTest;
import commons.GlobalConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPO;

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
        loginPage = homePO.navigateToLoginPage();
        String loginPageTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(loginPageTitle, GlobalConstants.LOGIN_PAGE_TITLE);
    }


}
