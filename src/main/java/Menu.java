import java.util.Scanner;

public class Menu {

    public void printWelcomeMassage() {
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                          WELCOME in MKBANK                           |");
        System.out.println("|                     PLACE WHERE DREAMS COME TRUE                     |");
        System.out.println("|                   CREATE AS MUCH MONEY AS YOU WANT                   |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println();
    }

    public void printOptions() {
        System.out.println("PLEASE CHOOSE ONE OF THE FOLLOWING OPTIONS:");
        System.out.println("=========================================================================");
        System.out.println("1) Create new customer profile.");
        System.out.println("2) Create account for existing customer(checking/savings).");
        System.out.println("3) Show single customer profile(with accounts).");
        System.out.println("4) Deposit money.");
        System.out.println("5) Withdraw money.");
        System.out.println("6) Show all customers profiles( with their accounts).");
        System.out.println("7) Change all savings accounts rate of interest.");
        System.out.println("8) Quit this app.");
        System.out.println("=========================================================================");
        System.out.print("\nEnter number here: ");
    }

    public int getUserInput() {
        Scanner keyboard = new Scanner(System.in);
        int userInput = 0;
        while (userInput < 1) {
            try {
                userInput = Integer.parseInt(keyboard.nextLine());
                if (userInput < 1 || userInput > 8) {
                    userInput = 0;
                    System.out.println("\n--- To choose proper option type number between 1 to 8 ---\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n--- To choose proper option type number between 1 to 8 ---\n");
            }
        }
        return userInput;
    }

    public void chooseOption(int userInput) {
        switch (userInput) {
            case 1 -> MenuAction.createCustomerProfile();
            case 2 -> MenuAction.createAccount();
            case 3 -> MenuAction.showSingleProfile();
            case 4 -> MenuAction.depositMoney();
            case 5 -> MenuAction.withdrawMoney();
            case 6 -> MenuAction.showAllProfiles();
            case 7 -> MenuAction.changeSavingsInterestRate();
            case 8 -> MenuAction.quitApp();
        }
    }
}

