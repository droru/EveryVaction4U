package View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    private static final Pattern DOUBLE_PATTERN = Pattern.compile(
            "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
                    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
                    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
                    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

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

    public static boolean isWord(String string){
        char [] a=string.toCharArray();
        for (char ch : a)
            if(Character.isDigit(ch))
                return false;

        return true;
    }

    public static boolean isNumber(String string){
        char [] a=string.toCharArray();
        for (char ch : a)
            if(!Character.isDigit(ch))
                return false;

        return true;
    }

    public static boolean isValidDate(LocalDate localDate)//String string)
    {
        //Date date=new Date (Integer.parseInt(string.split("/")[2]),Integer.parseInt(string.split("/")[1]),Integer.parseInt(string.split("/")[0]));
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        //LocalDateTime now=LocalDateTime.now();
        Date now = new Date();
        if( date.before(now))//new Date(now.getYear(),now.getMonth().getValue(),now.getDayOfWeek().getValue())))
            return false;
        return true;
    }

    public static boolean isDouble(String text) {
        return DOUBLE_PATTERN.matcher(text).matches();

    }
}
