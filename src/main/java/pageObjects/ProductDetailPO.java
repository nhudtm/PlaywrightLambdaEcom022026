package pageObjects;

import com.microsoft.playwright.Page;

public class ProductDetailPO extends MenuCategoryPO{

    //Constructor
    public ProductDetailPO(Page page) {
        super(page);
    }

    //Locator
    String productDetailPageTitle = "//h1";

    //Methods
     public String getProductDetailPageTitle() {
        return page.title();
     }

    public String getProductPageTitleText() {
        return page.locator(productDetailPageTitle).textContent();
    }
}
