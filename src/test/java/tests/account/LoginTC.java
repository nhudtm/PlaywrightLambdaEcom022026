package tests.account;

import commons.BaseTest;
import commons.GlobalConstants;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTC extends BaseTest {

    String email = initProperties().getProperty("email");
    String password = initProperties().getProperty("password");


    @BeforeMethod
    public void openLoginPage() {
        myAccountPO = homePO.clickMyAccountMenuItem();
    }

    // Gom TC01, TC04, TC07 vào 1 test case sử dụng DataProvider để kiểm tra các trường hợp email không hợp lệ
    @Description("Verify that user cannot login with invalid email format, blank email, and email with leading/trailing spaces")
    @Test(dataProvider = "invalidEmail")
    public void TC01_LoginWithInvalidEmailFormat(String invalidEmail) {
        myAccountPO.inputToEmailTextbox(invalidEmail);
        myAccountPO.inputToPasswordTextbox(password);
        myAccountPO.clickLoginButton();
        Assert.assertEquals(myAccountPO.getWarningMessageText(), GlobalConstants.LOGIN_WARNING_MESSAGE);
    }

    @Description("Verify that user cannot login with blank email and password")
    @Test
    public void TC02_LoginWithBlankInfo() {
        myAccountPO.clickLoginButton();
        Assert.assertEquals(myAccountPO.getWarningMessageText(), GlobalConstants.LOGIN_WARNING_MESSAGE);
    }

    @Description("Verify that user can login successfully with email in different case")
    @Test
    public void TC03_LoginWithEmailCaseSensitive() {
        myAccountPO.inputToEmailTextbox(email.toUpperCase());
        myAccountPO.inputToPasswordTextbox(password);
        myAccountPO.clickLoginButton();
        Assert.assertTrue(myAccountPO.isMyAccountTitleDisplayed());
        System.out.println("Done TC03");
        myAccountPO.clickAccountRightMenuItem("Logout");

    }

//    @Test
//    public void TC04_LoginWithInValidEmail(String invalidEmail) {
//        myAccountPO.inputToEmailTextbox(invalidEmail);
//        myAccountPO.inputToPasswordTextbox(password);
//        myAccountPO.clickLoginButton();
//        Assert.assertEquals(myAccountPO.getWarningMessageText(), GlobalConstants.LOGIN_WARNING_MESSAGE);
//    }

    @Description("Verify that user can login successfully with valid email and password")
    @Test
    public void TC05_LoginWithValidInfo() {
        myAccountPO.inputToEmailTextbox(email);
        myAccountPO.inputToPasswordTextbox(password);
        myAccountPO.clickLoginButton();
        Assert.assertTrue(myAccountPO.isMyAccountTitleDisplayed());
        myAccountPO.clickAccountRightMenuItem("Logout");
    }

    @Description("Verify that user cannot login with blank password")
    @Test
    public void TC06_LoginWithBlankPassword() {
        myAccountPO.inputToEmailTextbox(email);
        myAccountPO.inputToPasswordTextbox("");
        myAccountPO.clickLoginButton();
        Assert.assertEquals(myAccountPO.getWarningMessageText(), GlobalConstants.LOGIN_WARNING_MESSAGE);
    }

//    @Test
//    public void TC07_LoginWithInvalidEmailFormat() {
//        String invalidEmail = email + getRandomNumber() ;
//        myAccountPO.inputToEmailTextbox(invalidEmail);
//        myAccountPO.inputToPasswordTextbox(password);
//        myAccountPO.clickLoginButton();
//        Assert.assertEquals(myAccountPO.getWarningMessageText(), GlobalConstants.LOGIN_WARNING_MESSAGE);
//    }

    @Description("Verify that user cannot login with not existed email")
    @Test
    public void TC08_LoginWithNotExistedEmail() {
        String notExistedEmail = getRandomNumber() + email;
        myAccountPO.inputToEmailTextbox(notExistedEmail);
        myAccountPO.inputToPasswordTextbox(password);
        myAccountPO.clickLoginButton();
        Assert.assertEquals(myAccountPO.getWarningMessageText(), GlobalConstants.LOGIN_WARNING_MESSAGE);
    }

    @Description("Verify that user cannot login with invalid password")
    @Test
    public void TC09_LoginWithInvalidPassword() {
        String invalidPassword = password + getRandomNumber() ;
        myAccountPO.inputToEmailTextbox(email);
        myAccountPO.inputToPasswordTextbox(invalidPassword);
        myAccountPO.clickLoginButton();
        Assert.assertEquals(myAccountPO.getWarningMessageText(), GlobalConstants.LOGIN_WARNING_MESSAGE);
    }

    @Description("Verify that user cannot login after exceeding maximum login attempts with invalid credentials")
    @Test
    public void TC10_LoginWithExceedAttempts() {
        // Login with invalid password multiple times to exceed the maximum login attempts
        for (int i = 0; i < 2; i++) {
            String invalidPassword = password + getRandomNumber() ;
            myAccountPO.inputToEmailTextbox(email);
            myAccountPO.inputToPasswordTextbox(invalidPassword);
            myAccountPO.clickLoginButton();
        }
        Assert.assertEquals(myAccountPO.getWarningMessageText(), GlobalConstants.EXCEED_ATTEMPTS_WARNING_MESSAGE);
    }

    @DataProvider
    public Object[][] invalidEmail() {
        return new Object[][]{
                // Test data with leading and trailing spaces
                {" " + email + " "},
                // Blank email
                {""},
                // Invalid email format
                {email + getRandomNumber()}
        };
    }


}
