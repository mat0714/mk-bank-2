import domain.AccountName;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserInput {

    private static final Scanner keyboard = new Scanner(System.in);

    public static String validateText() {
        String text = "";
        while (text.equals("")) {
            text = keyboard.nextLine();
            if (!text.matches("[A-Za-z]{3,25}")) {
                System.out.println("\n--- Please enter a text(at least 3 letters). ---\n");
                text = "";
            }
        }
        return text;
    }

    public static int validatePesel() {
        int pesel = 0;
        while (pesel == 0) {
            try {
                pesel = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter correct PESEL number. ---\n");
            }
        }
        return pesel;
    }

    public static int validateCustomerId() {
        int customerId = 0;
        while (customerId == 0) {
            try {
                customerId = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter correct ID number. ---\n");
            }
        }
        return customerId;
    }

    public static BigDecimal validateMoneyAmount() {
        String moneyAmountText;
        BigDecimal moneyAmount = BigDecimal.valueOf(0);
        while (moneyAmount.equals(BigDecimal.valueOf(0))) {
            try {
                moneyAmountText = keyboard.nextLine();
                moneyAmount = new BigDecimal(moneyAmountText);
                if (moneyAmount.compareTo(BigDecimal.valueOf(0)) <= 0) {
                    System.out.println("\n--- Value should be greater than 0. ---\n");
                    moneyAmount = BigDecimal.valueOf(0);
                }
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter correct value. ---\n");
            }
        }
        return moneyAmount;
    }

    public static BigDecimal validateInterest() {
        String interestString;
        BigDecimal interest = BigDecimal.valueOf(-1);
        while (interest.equals(BigDecimal.valueOf(-1))) {
            try {
                interestString = keyboard.nextLine();
                interest = new BigDecimal(interestString);
                if (interest.compareTo(BigDecimal.valueOf(0)) < 0) {
                    System.out.println("\n--- Value should be 0 or greater. ---\n");
                    interest = BigDecimal.valueOf(-1);
                }
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter correct value. ---\n");
            }
        }
        return interest;
    }

    public static AccountName validateAccountType() {
        AccountName accountName = AccountName.UNDEFINED;
        int userChoice = 0;
        while (userChoice == 0) {
            try {
                userChoice = Integer.parseInt(keyboard.nextLine());
                if (!(userChoice == 1  || userChoice == 2)) {
                    userChoice = 0;
                    System.out.println("\n--- Please enter 1 for checking account or 2 for savings account. ---\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter 1 for checking account or 2 for savings account. ---\n");
            }
        }
        switch (userChoice) {
            case 1 -> accountName = AccountName.STANDARD_CHECKING;
            case 2 -> accountName = AccountName.SMART_SAVINGS;
        }
        return accountName;
    }

    public static int validateAccountNumber() {
        int AccountNumber = 0;
        while (AccountNumber == 0) {
            try {
                AccountNumber = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter correct account number. ---\n");
            }
        }
        return AccountNumber;
    }
}
