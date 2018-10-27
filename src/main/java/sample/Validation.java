package sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    // regular expression for validation
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_USER_NAME_LAST_REGEX =
            Pattern.compile("(?<=\\s|^)[a-zA-Z][a-zA-Z]*(?=[.,;:]?\\s|$)",Pattern.UNICODE_CASE);

    public static final Pattern VALID_Date =
            Pattern.compile("^([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/([0-9][0-9][0-9][0-9])$",Pattern.CASE_INSENSITIVE);


    public static boolean validateDate(String emailStr) {
        Matcher matcher = VALID_Date .matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateMail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateName(String value) {
        Matcher matcher = VALID_USER_NAME_LAST_REGEX .matcher(value);
        return matcher.find();
    }
    public static boolean validatePassword(String text) {
        return text.length() > 3 && text.length() < 9 && !text.contains(" ");
    }
    public static boolean validatecity(String text) {
        return !text.equals("בחר");
    }

    public static int validateAge(String text) {
        Date user_date = new Date();           //the date that the user enter.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            user_date = formatter.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDate start = LocalDate.of(user_date.getYear() + 1900, user_date.getMonth() + 1, user_date.getDate());
        LocalDate end = LocalDate.now();
        long years = ChronoUnit.YEARS.between(start, end);

        return (int) years;
    }
}
