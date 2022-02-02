import java.util.Scanner;

public class MKBankApp {
    public static void main(String[] args) {

        Menu.printLogo();
        Menu.printMenu();
        chooseOption();
    }

    public static void chooseOption() {

        Scanner keyboard = new Scanner(System.in);
        int userInput = 0;

        System.out.print("Enter number here: ");
        while (userInput == 0) {
            try {
                userInput = Integer.parseInt(keyboard.nextLine());
                if (userInput < 1 || userInput > 8) {
                    userInput = 0;
                    System.err.println("To choose proper option type number between 1 to 7:");
                }
            } catch (NumberFormatException e) {
                System.err.println("To choose proper option type number between 1 to 7:");
            }
        }

        switch (userInput) {
            case 1 -> MenuActions.createNewCustomerAndAccount();
            case 2 -> MenuActions.createAccount();
            case 3 -> MenuActions.customerDetails();
            case 4 -> MenuActions.depositMoney();
            case 5 -> MenuActions.withdrawMoney();
            case 6 -> MenuActions.showAllAccounts();
            case 7 -> MenuActions.changeInterest();
            case 8 -> {
                System.out.println("\nThank you for choosing MK Bank App.");
                System.out.println("We will be happy to see you soon :) \n");
            }
        }
        if (userInput != 8) {
            Menu.printMenu();
            chooseOption();
        }
    }
}