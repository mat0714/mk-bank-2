import java.util.Scanner;

public class UserInput {
    private static final Scanner keyboard = new Scanner(System.in);

    public static String verifyText() {
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

    public static int verifyPesel() {
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

    public static double verifyMoneyAmount() {
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

    public static double verifyInterest() {
        double interest = -1;
        while (interest == -1) {
            try {
                interest = Double.parseDouble(keyboard.nextLine());
                if (interest < 0) {
                    System.out.println("\n--- Value should be 0 or greater. ---\n");
                    interest = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter correct value. ---\n");
            }
        }
        return interest;
    }

    public static int verifyAccountTypeChoice() {
        int userChoice = 0;
        int checkingAccount = 1;
        int savingsAccount = 2;
        while (userChoice == 0) {
            try {
                userChoice = Integer.parseInt(keyboard.nextLine());
                if (!(userChoice == checkingAccount  || userChoice == savingsAccount)) {
                    userChoice = 0;
                    System.out.println("\n--- Please enter 1 for checking account or 2 for savings account. ---\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter 1 for checking account or 2 for savings account. ---\n");
            }
        }
        return userChoice;
    }

    public static int verifyAccountNumberChoice() {
        int pesel = 0;
        while (pesel == 0) {
            try {
                pesel = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\n--- Please enter correct account number. ---\n");
            }
        }
        return pesel;
    }
}
