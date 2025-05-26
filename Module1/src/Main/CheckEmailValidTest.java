package Main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CheckEmailValidTest {

    private final String email;
    private final boolean expectedResult;

    public CheckEmailValidTest(String email, boolean expectedResult) {
        this.email = email;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters(name = "{index}: isValidEmail({0})={1}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                // Valid cases
                {"email@domain.com", true},
                {"firstname.lastname@domain.com", true},
                {"email@subdomain.domain.com", true},
                {"firstname+lastname@domain.com", true},
                {"email@123.123.123.123", true},
                {"email@[123.123.123.123]", true},
                {"\"email\"@domain.com", true},
                {"1234567890@domain.com", true},
                {"email@domain-one.com", true},
                {"______@domain.com", true},
                {"email@domain.name", true},
                {"email@domain.co.jp", true},
                {"firstname-lastname@domain.com", true},

                // Invalid cases
                {"plainaddress", false},
                {"#@%^%#$@#$@#.com", false},
                {"@domain.com", false},
                {"Joe Smith <email@domain.com>", false},
                {"email.domain.com", false},
                {"email@domain@domain.com", false},
                {".email@domain.com", false},
                {"email.@domain.com", false},
                {"email..email@domain.com", false},
                {"あいうえお@domain.com", false},
                {"email@domain.com (Joe Smith)", false},
                {"email@domain", false},
                {"email@-domain.com", false},
                {"email@domain.web", false},
                {"email@111.222.333.44444", false},
                {"email@domain..com", false}
        });
    }

    @Test
    public void testIsValidEmail() {
        assertEquals("Testing email: " + email, expectedResult, CheckEmailValid.isValidEmail(email));
    }
}
