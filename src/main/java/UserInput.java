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

    public static double validateMoneyAmount() {
        double moneyAmount = 0;
        while (moneyAmount == 0) {
            try {
                moneyAmount = Double.parseDouble(keyboard.nextLine());
                if (moneyAmount <= 0) {
                    System.out.println("\n--- Value should be greater than 0. ---\n");
                    moneyAmount = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter correct value. ---\n");
            }
        }
        return moneyAmount;
    }

    public static double validateInterest() {
        double interest = -1.0;
        while (interest == -1.0) {
            try {
                interest = Double.parseDouble(keyboard.nextLine());
                if (interest < 0.0) {
                    System.out.println("\n--- Value should be 0 or greater. ---\n");
                    interest = -1.0;
                }
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter correct value. ---\n");
            }
        }
        return interest;
    }

    public static int validateAccountType() {
        int accountType = 0;
        int checkingAccount = 1;
        int savingsAccount = 2;
        while (accountType == 0) {
            try {
                accountType = Integer.parseInt(keyboard.nextLine());
                if (!(accountType == checkingAccount  || accountType == savingsAccount)) {
                    accountType = 0;
                    System.out.println("\n--- Please enter 1 for checking account or 2 for savings account. ---\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter 1 for checking account or 2 for savings account. ---\n");
            }
        }
        return accountType;
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
