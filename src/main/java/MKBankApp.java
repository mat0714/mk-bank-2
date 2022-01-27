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

        System.out.println("Enter number below to make action:");

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

            case 1:
                MenuActions.createNewCustomerAndAccount();
                break;
            case 2:
                MenuActions.createAccount();
                break;
            case 3:
                MenuActions.changeInterest();
                break;
            case 4:
                MenuActions.customerDetails();
                break;
            case 5:
                MenuActions.depositMoney();
                break;
            case 6:
                MenuActions.withdrawMoney();
                break;
            case 7:
                MenuActions.showAllAccounts();
                break;
            case 8:
                System.out.println();
                System.out.println("Thank you for choosing MK Bank App.");
                System.out.println("We will be happy to see you soon :)");
                System.out.println();
                break;
        }

        if (userInput != 8) {

            Menu.printMenu();
            chooseOption();
        }


    }
}