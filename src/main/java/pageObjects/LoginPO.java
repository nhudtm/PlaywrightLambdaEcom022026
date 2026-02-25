package pageObjects;

import com.microsoft.playwright.Page;

public class LoginPO extends MenuCategoryPO {
    //    Page page; page ở đây đã được kế thừa từ MenuCategoryPO nên không cần khai báo lại, sẽ bị lỗi
    public LoginPO(Page page) {
        super(page);
    }

    public String getLoginPageTitle() {
        return page.title();
    }
}
