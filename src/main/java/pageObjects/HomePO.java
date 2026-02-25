package pageObjects;

import com.microsoft.playwright.Page;

public class HomePO extends MenuCategoryPO {

    //Constructor
    public HomePO(Page page) {
        super(page);
    }

    //Methods
     public String getHomePageTitle() {
        return page.title();
    }
}
