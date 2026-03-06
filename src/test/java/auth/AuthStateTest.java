package auth;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class AuthStateTest {

    @Parameters("browserName")
    @BeforeTest
    public void generateAuthState(String browserName) {
        AuthStateGenerator.generateState(browserName);
    }
}
