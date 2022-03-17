import java.util.Scanner;

public class Menu {

    public void printWelcomeMassage() {
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("|                          WELCOME in MKBANK                           |");
        System.out.println("|                    THE BEST PLACE FOR YOUR MONEY                     |");
        System.out.println("|                                                                      |");
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
        System.out.println("6) Show all customer profiles(with accounts).");
        System.out.println("7) Change all savings accounts rate of interest.");
        System.out.println("8) Quit this app.");
        System.out.println("=========================================================================");
        System.out.print("\nEnter number here: ");
    }

    public void chooseOption(int userChoice) {
        switch (userChoice) {
            case 1 -> MenuAction.createProfile();
            case 2 -> MenuAction.createAccount();
            case 3 -> MenuAction.showSingleProfile();
            case 4 -> MenuAction.depositMoney();
            case 5 -> MenuAction.withdrawMoney();
            case 6 -> MenuAction.showAllProfiles();
            case 7 -> MenuAction.changeSavingsInterestRate();
            case 8 -> MenuAction.quitApp();
        }
    }

    public int getUserChoice() {
        Scanner keyboard = new Scanner(System.in);
        int userChoice = 0;
        while (userChoice < 1) {
            try {
                userChoice = Integer.parseInt(keyboard.nextLine());
                if (userChoice < 1 || userChoice > 8) {
                    userChoice = 0;
                    System.out.println("\n--- To choose proper option enter number between 1 to 8 ---\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n--- To choose proper option enter number between 1 to 8 ---\n");
            }
        }
        return userChoice;
    }

    public static void goToMenu() {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Press Enter and go to Menu ");
        keyboard.nextLine();
        System.out.println();
    }
}

