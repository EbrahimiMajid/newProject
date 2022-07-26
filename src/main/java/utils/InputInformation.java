package utils;

import utils.input.Input;

import java.sql.Timestamp;

public class InputInformation {
    public static final String FIRSTNAME_REGEX = "(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,25}$";
    public static final String LASTNAME_REGEX = "(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,25}$";
    public static final String BIRTH_DAY_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
    public static final String CODE_REGEX = "^\\d{6}$";
    public static final String PHONE_NUMBER_REGEX = "(0/91)?[7-9][0-9]{9}";
    public static final String FIRSTNAME_WARNING =
            "The first name is between 1 and 25 characters.\n" +
                    "The first name can only start with an a-z (ignore case) character.\n" +
                    "After that the first name can contain a-z (ignore case) and [ '-,.].\n" +
               "The first name can only end with an a-z (ignore case) character.";
    public static final String LASTNAME_WARNING =
            "The last name is between 1 and 25 characters.\n" +
                    "The last name can only start with an a-z (ignore case) character.\n" +
                    "After that the last name can contain a-z (ignore case) and [ '-,.].\n" +
                    "The last name can only end with an a-z (ignore case) character.";
    public static final String BIRTH_DAY_WARNING = "Your BirthDay is Wrong.";
    public static final String CODE_WARNING =
            "Your national code must be 10 digit, at least one letter and one number.";
    public static final String PHONE_NUMBER_WARNING =
            "Your mobile number must be country specific.";
    public static final String FIRSTNAME_MESSAGE = "Enter your firstName :";
    public static final String LASTNAME_MESSAGE = "Enter your lastName :";
    public static final String BIRTH_DAY_MESSAGE = "Enter Your BirthDay in This format(2010-3-24) :";
    public static final String CODE_MESSAGE = "Enter your national code :";
    public static final String SALARY_MESSAGE = "Enter your salary :";
    public static final String PHONE_NUMBER_MESSAGE = "Enter your phone number :";
    public static final String STATE_MESSAGE = "Enter your state :";
    public static final String CITY_MESSAGE = "Enter your city :";
    public static final String POSTAL_ADDRESS_MESSAGE = "Enter your postal address :";
    public static final String POSTAL_CODE_MESSAGE = "Enter your postal code :";


    public static String getFirstName() {
        return new Input(
                FIRSTNAME_MESSAGE,
                FIRSTNAME_WARNING,
                FIRSTNAME_REGEX,
                null).getInputString();
    }

    public static String getLastName() {
        return new Input(
                LASTNAME_MESSAGE,
                LASTNAME_WARNING,
                LASTNAME_REGEX,
                null).getInputString();
    }

    public static Timestamp getBirthDay() {
        String birthday = new Input(
                BIRTH_DAY_MESSAGE,
                BIRTH_DAY_WARNING,
                BIRTH_DAY_REGEX,
                null).getInputString();
        return Timestamp.valueOf(birthday);
    }

    public static Double getSalary() {
        return new Input(
                SALARY_MESSAGE,
                Double.MAX_VALUE,
                0.0,
                null).getInputDouble();
    }

    public static String getPhoneNumber() {
        return new Input(
                PHONE_NUMBER_MESSAGE,
                PHONE_NUMBER_WARNING,
                PHONE_NUMBER_REGEX,
                null
        ).getInputString();
    }

    public static String getState() {
        return new Input(
                STATE_MESSAGE
        ).getInputString();
    }

    public static String getCity() {
        return new Input(
                CITY_MESSAGE
        ).getInputString();
    }

    public static String getPostalAddress() {
        return new Input(
                POSTAL_ADDRESS_MESSAGE
        ).getInputString();
    }

    public static String getPostalCode() {
        return new Input(
                POSTAL_CODE_MESSAGE
        ).getInputString();
    }
}
