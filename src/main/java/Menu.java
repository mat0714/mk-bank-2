public class Menu {

    public static void printLogo() {
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                          WELCOME in MKBANK                           |");
        System.out.println("|                        WHERE DREAMS COME TRUE                        |");
        System.out.println("|                   CREATE AS MUCH MONEY AS YOU WANT                   |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println();
    }

    public static void printMenu() {
        System.out.println("Options available in our bank:");
        System.out.println("=========================================================================");
        System.out.println("1) Create new customer profile with new checking account.");
        System.out.println("2) Create account for existing customer(checking/savings).");
        System.out.println("3) Change accounts rate of interest.");
        System.out.println("4) Get customer accounts details.");
        System.out.println("5) Deposit money.");
        System.out.println("6) Withdraw money.");
        System.out.println("7) Show All Accounts");
        System.out.println("8) Quit this app.");
        System.out.println("=========================================================================");
        System.out.println();
    }
}
