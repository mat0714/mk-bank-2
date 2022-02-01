import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuActions {

    private static List<Customer> customers = new ArrayList<>();
    private static Scanner keyboard = new Scanner(System.in);

    public static void createNewCustomerAndAccount() {
        System.out.println("-------- CREATING NEW CUSTOMER PROFILE WITH NEW CHECKING ACCOUNT --------");
        System.out.println();
        System.out.println("Please enter client name");
        String name = keyboard.nextLine();
        System.out.println("Please enter client surname:");
        String surName = keyboard.nextLine();
        System.out.println("Please enter client PESEL number:");
        String pesel = keyboard.nextLine();
        System.out.println("Please enter deposit amount:");

        double deposit = 0.00;
        try {
            deposit = Double.parseDouble(keyboard.nextLine());

            if (deposit < 0) {
                System.out.println();
                System.out.println("--- Deposit should be 0 or bigger. ---");
                pressEnter();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("--- Incorrect deposit value. ---");
            System.out.println();
            pressEnter();

            return;
        }

        Customer customer = new Customer(name, surName, pesel);
        customers.add(customer);
        customer.addAccount(new Checking(deposit));

        System.out.println();
        System.out.println("--- Customer profile and checking account was successfully created. ---");
        System.out.println();

        pressEnter();
    }

    public static void createAccount() {
        System.out.println("------------------- CREATING NEXT ACCOUNT FOR CUSTOMER ------------------");
        System.out.println();
        System.out.println("Please enter PESEL number of existing customer:");
        String pesel = keyboard.nextLine();

        if (checkPesel(pesel) != null) {
            System.out.println("--- Customer was found. You can create account. ---");
            System.out.println();
            System.out.println("Please enter type of account (checking/savings):");
            String type = keyboard.nextLine();
            System.out.println("Please enter deposit amount:");
            double deposit = Double.parseDouble(keyboard.nextLine());
            Customer customer = checkPesel(pesel);

            if (type.equals("checking")) {
                customer.addAccount(new Checking(deposit));
            }
            else {
                customer.addAccount(new Savings(deposit));
            }

            System.out.println();
            System.out.println("--- New account was successfully created. ---");
            System.out.println();
        }

        pressEnter();
    }

    public static void changeInterest() {
        System.out.println("--------------- CHANGING SAVINGS ACCOUNTS RATE OF INTEREST --------------");
        System.out.println();
        System.out.println("Example: enter 0.03 to set up rate on 3%.");
        System.out.println("This operation will change rate of interest for all savings accounts.");
        System.out.println("Please enter rate of interest:");

        double interest = 0.00;
        try {
            interest = Double.parseDouble(keyboard.nextLine());

            if (interest < 0) {
                System.out.println();
                System.out.println("--- Deposit should be 0 or bigger. ---");
                pressEnter();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("--- Incorrect deposit value. ---");
            System.out.println();
            pressEnter();

            return;
        }

        for (Customer customer : customers) {
            List<Account> accounts = customer.getAccounts();
            for (Account account : accounts) {
                if (account.getAccountType().equals("savings")) {
                    account.setInterest(interest);
                }
            }
        }

        System.out.println();
        System.out.println("--- Rate of interest for all savings accounts was changed ---");
        System.out.println();

        pressEnter();
    }

    public static void customerDetails() {

        System.out.println("---------------------------- CUSTOMER DETAILS ---------------------------");
        System.out.println();
        System.out.println("Please enter customer PESEL number:");
        String pesel = keyboard.nextLine();

        if (checkPesel(pesel) != null) {
            Customer customer = checkPesel(pesel);
            System.out.println(customer);
        }
        pressEnter();
    }

    public static void depositMoney() {

        System.out.println("---------------------------- DEPOSIT MONEY ---------------------------");
        System.out.println();
        System.out.println("Please enter customer PESEL number:");
        String pesel = keyboard.nextLine();

        System.out.println("                          CUSTOMERS DETAILS                           ");
        Customer customer = checkPesel(pesel);
        if (!customer.equals(null)) {
            System.out.println(customer);
            System.out.println("Choose account and enter proper number below:");
            int number = Integer.parseInt(keyboard.nextLine());
            System.out.println("Please enter how much do you want deposit:");
            Double deposit = Double.parseDouble(keyboard.nextLine());
            Account account = checkAccount(customer, number);

            if (account.deposit(deposit)) {
                System.out.println("                 CUSTOMER DETAILS AFTER OPERATION                 ");
                System.out.println(customer);
            }

        }
        pressEnter();
    }

    public static void withdrawMoney() {
        System.out.println("--------------------------- WITHDRAW MONEY ---------------------------");
        System.out.println();
        System.out.println("Please enter customer PESEL number:");
        String pesel = keyboard.nextLine();

        System.out.println("                          CUSTOMERS DETAILS                           ");
        Customer customer = checkPesel(pesel);
        if (!customer.equals(null)) {
            System.out.println(customer);
            System.out.println("To withdraw money choose account and enter number below:");
            int number = Integer.parseInt(keyboard.nextLine());
            System.out.println("Please enter how much do you want withdraw:");
            Double withdrawAmount = Double.parseDouble(keyboard.nextLine());
            Account account = checkAccount(customer, number);

            if (account.withdraw(withdrawAmount)) {
                System.out.println("                 CUSTOMER DETAILS AFTER OPERATION                 ");
                System.out.println(customer);
            }
        }
        pressEnter();
    }

    public static void showAllAccounts() {
        System.out.println("-------------------- ALL CUSTOMERS AND THEIR ACCOUNTS -------------------");
        System.out.println();

        for (Customer c : customers) {
            System.out.println(c);
        }

        pressEnter();

    }

    public static Customer checkPesel(String pesel) {

        Customer customer = null;

        for (Customer c : customers) {
            if (c.getPesel().equals(pesel)) {
                customer = c;
                break;
            }
        }

        if (customer == null) {
            System.out.println();
            System.out.println("--- Person with this PESEL number is not a customer of MKBank. ---");
            System.out.println();
        }

        return customer;
    }

    public static Account checkAccount(Customer customer, int number) {

        Account account = null;

        for (Account a : customer.getAccounts()) {
            if (a.getNumber() == number) {
                account = a;
                break;
            }
        }
        return account;
    }


    public static void pressEnter() {
        System.out.println("Press Enter and go to Menu");
        String enter = keyboard.nextLine();
    }
}
