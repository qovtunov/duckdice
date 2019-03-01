package Components;

import Driver.MainMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GameField extends MainMethods {

    @FindBy(id = "game-submit-button")
    public WebElement startButton;

    @FindBy(name = "bet-amount-input")
    public WebElement betAmountInput;

    @FindBy(name = "chance-input")
    public WebElement winChanceInput;

    @FindBy(name = "payout-input")
    public WebElement payoutInput;

    @FindBy(name = "profit-amount-input")
    public WebElement profitOnWinInput;

    @FindBy(name = "rolls")
    public WebElement numberOfRollsInput;

    @FindBy(name = "loss-percent-input")
    public WebElement increaseBetByOnLossInput;

    @FindBy(name = "win-percent-input")
    public WebElement increaseBetByOnWinInput;

    @FindBy(name = "max-balance-input")
    public WebElement balanceIsAboveInput;

    @FindBy(name = "min-balance-input")
    public WebElement balanceIsBelowInput;

    @FindBy(name = "radio-loss-percent")
    public WebElement radioLossPercent;

    @FindBy(name = "radio-loss-reset")
    public WebElement radioLossReset;

    @FindBy(name = "radio-loss-stop")
    public WebElement radioLossStop;

    @FindBy(name = "radio-win-percent")
    public WebElement radioWinPercent;

    @FindBy(name = "radio-win-reset")
    public WebElement radioWinReset;

    @FindBy(name = "radio-win-stop")
    public WebElement radioWinStop;

    @FindBy(name = "radio-group-on-loss")
    public WebElement radioGroupOnLoss;

    @FindBy(name = "radio-group-on-win")
    public WebElement radioGroupOnWin;

    @FindBy(name = "radio-group-each-bet")
    public WebElement radioGroupEachBet;



    public GameField() {
        PageFactory.initElements(driver, this);
    }

    public void clickStartButton(){
        clickElement(startButton);
    }

    public void inputBetAmount(String betAmountValue){
        inputDataFieldClear(betAmountInput, betAmountValue);
    }

    public void inputWinChance(String winChanceValue){
        inputDataFieldClear(winChanceInput, winChanceValue);
    }

    public void inputPayout(String payoutValue){
        inputDataFieldClear(payoutInput, payoutValue);
    }

    public void inputProfitOnWin(String profitOnWinValue){
        inputDataFieldClear(profitOnWinInput, profitOnWinValue);
    }

    public void inputNumberOfRolls(String numberOfRollsValue){
        inputDataFieldClear(numberOfRollsInput, numberOfRollsValue);
    }

    public void inputIncreaseBetByOnLoss(String increaseBetByOnLossValue){
        inputDataFieldClear(increaseBetByOnLossInput, increaseBetByOnLossValue);
    }

    public void inputIncreaseBetByOnWin(String increaseBetByOnWinValue){
        inputDataFieldClear(increaseBetByOnWinInput, increaseBetByOnWinValue);
    }

    public void inputBalanceIsAbove(String balanceIsAboveValue){
        inputDataFieldClear(balanceIsAboveInput, balanceIsAboveValue);
    }

    public void inputBalanceIsBelow(String balanceIsBelowValue){
        inputDataFieldClear(balanceIsBelowInput, balanceIsBelowValue);
    }

    public void clickRadioLossPercent(){
        clickElement(radioLossPercent);
    }

    public void clickRadioLossReset(){
        clickElement(radioLossReset);
    }

    public void clickRadioLossStop(){
        clickElement(radioLossStop);
    }

    public void clickRadioWinPercent(){
        clickElement(radioWinPercent);
    }

    public void clickRadioWinReset(){
        clickElement(radioWinReset);
    }

    public void clickRadioWinStop(){
        clickElement(radioWinStop);
    }

    public void clickRadioGroupOnLoss(){
        clickElement(radioGroupOnLoss);
    }

    public void clickRadioGroupOnWin(){
        clickElement(radioGroupOnWin);
    }

    public void clickRadioGroupEachBet(){
        clickElement(radioGroupEachBet);
    }



}
