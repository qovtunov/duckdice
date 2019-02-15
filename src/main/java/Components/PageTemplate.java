package Components;

import Driver.MainMethods;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageTemplate extends MainMethods {

    @FindBy()
    public WebElement someElement;


    public PageTemplate() {
        PageFactory.initElements(driver, this);
    }

    public void someAction() {
        clickElement(someElement);
        inputDataField(someElement, "test");
    }

}
