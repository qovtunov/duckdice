package WebTests;

import Components.GreetingModal;
import org.testng.annotations.Test;

import Setup.SetupTestWeb;

public class SignUpTest extends SetupTestWeb {

    GreetingModal greetingModal;

    @Test
    public void signUpRandomTestPositive() {
        greetingModal = new GreetingModal();
        greetingModal.createAccountRandom();
    }



}
