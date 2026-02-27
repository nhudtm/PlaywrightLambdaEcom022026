package tests.account;

import commons.BaseTest;
import commons.GlobalConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ForgotPasswordTC extends BaseTest {

    String email = initProperties().getProperty("email");


    @BeforeMethod
    public void accessForgotPasswordPage() {
        myAccountPO = homePO.clickMyAccountMenuItem();
        myAccountPO.clickForgottenPassword();
    }

    @Test
    public void TC11_ForgotPasswordSuccessfully() {
        Assert.assertEquals(myAccountPO.getForgotPasswordPageTitleText(), GlobalConstants.FORGOT_PASSWORD_PAGE_TITLE);
        myAccountPO.enterEmailToResetPassword(email);
        myAccountPO.clickContinueButtonInForgotPasswordPage();
        Assert.assertEquals(myAccountPO.getSuccessMessageTextInForgotPasswordPage(), "An email with a confirmation link has been sent your email address.");

        // Verify email is sent to the user (This step is optional and can be implemented based on the email service used in the application)

        //Verify login with new password (This step is optional and can be implemented if the application allows resetting password through email)

    }

    @Test
    public void TC12_ForgotPasswordBlankEmail() {
        Assert.assertEquals(myAccountPO.getForgotPasswordPageTitleText(), GlobalConstants.FORGOT_PASSWORD_PAGE_TITLE);
        myAccountPO.enterEmailToResetPassword("");
        myAccountPO.clickContinueButtonInForgotPasswordPage();
        Assert.assertEquals(myAccountPO.getWarningMessageInForgotPasswordPage(), GlobalConstants.FORGOT_PASSWORD_NOT_EXISTED_EMAIL);
    }

    @Test
    public void TC13_ForgotPasswordWithEmailHasSpace() {
        Assert.assertEquals(myAccountPO.getForgotPasswordPageTitleText(), GlobalConstants.FORGOT_PASSWORD_PAGE_TITLE);
        myAccountPO.enterEmailToResetPassword(" " + email + " ");
        myAccountPO.clickContinueButtonInForgotPasswordPage();
        Assert.assertEquals(myAccountPO.getWarningMessageInForgotPasswordPage(), GlobalConstants.FORGOT_PASSWORD_PAGE_TITLE);
    }

    @Test
    public void TC14_ForgotPasswordWithEmailCaseSensitive() {
        Assert.assertEquals(myAccountPO.getForgotPasswordPageTitleText(), GlobalConstants.FORGOT_PASSWORD_PAGE_TITLE);
        myAccountPO.enterEmailToResetPassword(email.toUpperCase());
        myAccountPO.clickContinueButtonInForgotPasswordPage();
        Assert.assertEquals(myAccountPO.getSuccessMessageTextInForgotPasswordPage(), "An email with a confirmation link has been sent your email address.");
    }

    @Test
    public void TC15_ForgotPasswordInvalidEmailFormat() {
        String invalidEmail = email + getRandomNumber() ;
        Assert.assertEquals(myAccountPO.getForgotPasswordPageTitleText(), GlobalConstants.FORGOT_PASSWORD_PAGE_TITLE);
        myAccountPO.enterEmailToResetPassword(invalidEmail);
        myAccountPO.clickContinueButtonInForgotPasswordPage();
        Assert.assertEquals(myAccountPO.getWarningMessageInForgotPasswordPage(), GlobalConstants.FORGOT_PASSWORD_NOT_EXISTED_EMAIL);
    }

    @Test
    public void TC16_ForgotPasswordWithNotExistedEmail() {
        String notExistedEmail = getRandomNumber() + email;
        Assert.assertEquals(myAccountPO.getForgotPasswordPageTitleText(), GlobalConstants.FORGOT_PASSWORD_PAGE_TITLE);
        myAccountPO.enterEmailToResetPassword(notExistedEmail);
        myAccountPO.clickContinueButtonInForgotPasswordPage();
        Assert.assertEquals(myAccountPO.getWarningMessageInForgotPasswordPage(), GlobalConstants.FORGOT_PASSWORD_NOT_EXISTED_EMAIL);
    }

    @Test
    public void TC17_ForgotPasswordCancel() {
        Assert.assertEquals(myAccountPO.getForgotPasswordPageTitleText(), GlobalConstants.FORGOT_PASSWORD_PAGE_TITLE);
        myAccountPO.enterEmailToResetPassword(email);
        myAccountPO.clickBackButtonInForgotPasswordPage();
        Assert.assertTrue(myAccountPO.isLoginFormDisplayed());
    }
}
