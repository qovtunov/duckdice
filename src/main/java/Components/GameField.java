package Components;

import Driver.MainMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GameField extends MainMethods {

    @FindBy(name = "")
    public WebElement betAmountInput;

    public GameField() {
        PageFactory.initElements(driver, this);
    }

    public void inputBetAmount(String betAmountValue){
        inputDataFieldClear(betAmountInput, betAmountValue);
    }


}
