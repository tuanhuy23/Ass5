package Main;

import java.util.regex.Pattern;

public class CheckEmailValid {
    private static final String EMAIL_REGEX =
            "^(?:\"[^\"]+\"|[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)@"
                    + "(?:\\[(\\d{1,3}(\\.\\d{1,3}){3})\\]|"
                    + "(\\d{1,3}(\\.\\d{1,3}){3})|"
                    + "((?!-)[a-zA-Z0-9-]+(?<!-)\\.)+[a-zA-Z]{2,})$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String[] INVALID_TLDS = {"web"};
    public static boolean isValidEmail(String email) {
        if (email == null || email.contains(" ") || email.matches(".*\\(.*\\).*") || email.matches(".*<.*>.*")) {
            return false;
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return false;
        }
        String domain = email.substring(email.lastIndexOf('@') + 1);
        String commercial = domain.substring(domain.lastIndexOf('.') + 1);
        for (String invalidTld : INVALID_TLDS) {
            if (invalidTld.equals(commercial)) {
                return false;
            }
        }
        return true;
    }
}
