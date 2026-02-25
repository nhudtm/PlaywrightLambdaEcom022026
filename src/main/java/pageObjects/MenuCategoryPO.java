package pageObjects;

import com.microsoft.playwright.Page;
import commons.PlaywrightFactory;

public class MenuCategoryPO {
    protected Page page;
    protected LoginPO loginPO;
    protected HomePO homePO;

    public MenuCategoryPO(Page page) {
        this.page = page;
    }
    //Locator
    String loginLink = "//ul[contains(@class,'dropdown-menu show')]//span[contains(text(),'Login')]"; //
    String myAccountLink = "//div[@id='main-navigation']//div/span[@class='title' and contains(text(),'My account')]"; //

    //Methods
    public LoginPO navigateToLoginPage() {
        page.hover(myAccountLink);
        page.click(loginLink);
        return new LoginPO(page);
    }
}
