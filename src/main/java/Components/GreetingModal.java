package Components;

import Driver.MainMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GreetingModal extends MainMethods {

    @FindBy (name = "username")
    public WebElement usernameInput;

    @FindBy (xpath = "//button[contains(text(),'START WINNING')]")
    public WebElement signUpButton;

    @FindBy (id = "agreements-checkbox")
    public WebElement agreementCheckbox;

    public GreetingModal() {
        PageFactory.initElements(driver, this);
    }

    public void createAccountRandom(){
        checkCheckbox(agreementCheckbox);
        clickElement(signUpButton);
    }
}
