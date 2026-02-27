package pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MyAccountPO extends MenuCategoryPO {

    private final Locator emailTextbox;
    private final Locator passwordTextbox;
    private final Locator loginButton;
    private final Locator warningMessage;
//    private final Locator myAccountTittle;
    private final Locator forgotPasswordLink;
    private final Locator forgotPasswordPageTitle;
    private final Locator emailTextboxInForgotPassword;
    private final Locator continueButtonInForgotPasswordPage;
    private final Locator successMessageInForgotPasswordPage;
    private final Locator warningMessageInForgotPasswordPage;
    private final Locator backButtonInForgotPasswordPage;
    private final Locator loginForm;
    private final Locator logoutLink;


    public MyAccountPO(Page page) {
        super(page);
        this.emailTextbox = page.locator("#input-email");
        this.passwordTextbox = page.locator("#input-password");
        this.loginButton = page.locator("//input[@type='submit']");
        this.warningMessage = page.locator("#account-login div.alert-danger");
//        this.myAccountTittle = page.locator("//h2[text()='My Account']");
        this.forgotPasswordLink = page.locator("//div[@class='form-group']/a[contains(text(),'Forgotten Password')]");
        this.forgotPasswordPageTitle = page.locator("#content h1");
        this.emailTextboxInForgotPassword = page.locator("#input-email");
        this.continueButtonInForgotPasswordPage = page.locator("//button[text()='Continue']");
        this.successMessageInForgotPasswordPage = page.locator("div.alert-success");
        this.warningMessageInForgotPasswordPage = page.locator("#account-forgotten div.alert-danger");
        this.backButtonInForgotPasswordPage = page.locator("//a[text()=' Back']");
        this.loginForm = page.locator("//h2[text()='Returning Customer']");
        this.logoutLink = page.locator("//a[contains(text(),'Logout')]");
    }

    protected String myAccountTitle = "//h2[text()='My Account']";

    public void inputToEmailTextbox(String email) {
        emailTextbox.fill(email);
    }

    public void inputToPasswordTextbox(String password) {
        passwordTextbox.fill(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public String getWarningMessageText() {
        return warningMessage.textContent().trim();
    }

    public boolean isMyAccountTitleDisplayed() {
        page.waitForSelector(myAccountTitle, new Page.WaitForSelectorOptions());
        return page.locator(myAccountTitle).isVisible();
    }



    public void clickAccountRightMenuItem(String rightMenuItem) {
        page.locator("a").filter(new Locator.FilterOptions().setHasText(rightMenuItem)).last().click();
//        System.out.println("Clicked to " + "Logout" + " in Account right menu");
//   logoutLink.click();
//        System.out.printf("Clicked to %s in Account right menu%n", "Logout");
    }

    public void clickForgottenPassword() {
        forgotPasswordLink.click();
    }

    public String getForgotPasswordPageTitleText() {
        return forgotPasswordPageTitle.textContent().trim();
    }

    public void enterEmailToResetPassword(String email) {
        emailTextboxInForgotPassword.fill(email);
    }

    public void clickContinueButtonInForgotPasswordPage() {
        continueButtonInForgotPasswordPage.click();
    }

    public String getSuccessMessageTextInForgotPasswordPage() {
        return successMessageInForgotPasswordPage.textContent().trim();
    }

    public String getWarningMessageInForgotPasswordPage() {
        return warningMessageInForgotPasswordPage.textContent().trim();
    }

    public void clickBackButtonInForgotPasswordPage() {
        backButtonInForgotPasswordPage.click();
    }

    public boolean isLoginFormDisplayed() {
        return loginForm.isVisible();
    }
}
