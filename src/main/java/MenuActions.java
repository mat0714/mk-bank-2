import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuActions {

    private static final List<Customer> customers = new ArrayList<>();
    private static Scanner keyboard = new Scanner(System.in);
    public static void createNewCustomerAndAccount() {
        System.out.println("\n-------- CREATING NEW CUSTOMER PROFILE WITH NEW CHECKING ACCOUNT --------\n");
        System.out.print("Please enter client name: ");
        String name = keyboard.nextLine();
        System.out.print("Please enter client surname: ");
        String surName = keyboard.nextLine();
        System.out.print("Please enter client PESEL number: ");
        String pesel = keyboard.nextLine();
        System.out.print("Please enter deposit amount[$]: ");
        double deposit;
        try {
            deposit = Double.parseDouble(keyboard.nextLine());

        } catch (NumberFormatException e) {
            System.out.println("\n--- Incorrect deposit value. ---\n");
            pressEnter();
            return;
        }

        Customer customer = new Customer(name, surName, pesel);
        customers.add(customer);
        customer.addAccount(new Checking(deposit));

        System.out.println("\n--- Customer profile and checking account was successfully created. ---\n");
        System.out.println("                 CUSTOMER DETAILS AFTER OPERATION                 ");
        System.out.println(customer);

        pressEnter();
    }

    public static void createAccount() {
        System.out.println("\n------------------- CREATING NEXT ACCOUNT FOR CUSTOMER ------------------\n");
        System.out.print("Please enter PESEL number of existing customer: ");
        String pesel = keyboard.nextLine();

        if (checkPesel(pesel) != null) {
            System.out.println("\n--- Customer was found. You can create account. ---\n");
            System.out.print("Please enter type of account (checking/savings):");
            String type = keyboard.nextLine();
            System.out.print("Please enter deposit amount[$]: ");

            double deposit;
            try {
                deposit = Double.parseDouble(keyboard.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("\n--- Incorrect deposit amount. ---\n");
                pressEnter();
                return;
            }

            Customer customer = checkPesel(pesel);

            if (type.equals("checking")) {
                customer.addAccount(new Checking(deposit));
            }
            else {
                customer.addAccount(new Savings(deposit));
            }

            System.out.println("\n--- New account was successfully created. ---\n");
        }

        pressEnter();
    }

    public static void customerDetails() {

        System.out.println("\n---------------------------- CUSTOMER DETAILS ---------------------------\n");
        System.out.print("Please enter customer PESEL number: ");
        String pesel = keyboard.nextLine();

        if (checkPesel(pesel) != null) {
            Customer customer = checkPesel(pesel);
            System.out.println("\n" + customer);
        }
        pressEnter();
    }

    public static void depositMoney() {

        System.out.println("\n---------------------------- DEPOSIT MONEY ---------------------------\n ");
        System.out.print("Please enter customer PESEL number: ");
        String pesel = keyboard.nextLine();
        Customer customer = checkPesel(pesel);

        if (customer != null) {
            System.out.println("\n                          CUSTOMERS DETAILS                           ");
            System.out.println(customer);
            System.out.print("Please enter how much do you want deposit[$]: ");
            double depositAmount;
            try {
                depositAmount = Double.parseDouble(keyboard.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("\n--- Incorrect deposit amount. ---\n");
                pressEnter();
                return;
            }
            System.out.print("Choose account and enter its number: ");
            int number = Integer.parseInt(keyboard.nextLine());
            Account account = checkAccount(customer, number);
            if (account != null) {
                account.deposit(depositAmount);
                System.out.println("\n                 CUSTOMER DETAILS AFTER OPERATION                 ");
                System.out.println(customer);
            }
        }
        pressEnter();
    }

    public static void withdrawMoney() {
        System.out.println("\n--------------------------- WITHDRAW MONEY ---------------------------\n");
        System.out.print ("Please enter customer PESEL number: ");
        String pesel = keyboard.nextLine();
        Customer customer = checkPesel(pesel);

        if (customer != null) {
            System.out.println("\n                          CUSTOMERS DETAILS                           ");
            System.out.println(customer);
            System.out.print("Please enter how much do you want withdraw[$]: ");
            double withdrawAmount;
            try {
                withdrawAmount = Double.parseDouble(keyboard.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("\n--- Incorrect withdraw amount. ---\n");
                pressEnter();
                return;
            }
            System.out.print("Choose account and enter its number: ");
            int number = Integer.parseInt(keyboard.nextLine());
            Account account = checkAccount(customer, number);
            if (account != null) {
                account.withdraw(withdrawAmount);
                System.out.println("\n                 CUSTOMER DETAILS AFTER OPERATION                 ");
                System.out.println(customer);
            }
        }
        pressEnter();
    }

    public static void showAllAccounts() {
        System.out.println("\n-------------------- ALL CUSTOMERS AND THEIR ACCOUNTS -------------------\n");

        for (Customer c : customers) {
            System.out.println(c);
        }
        pressEnter();
    }

    public static void changeInterest() {
        System.out.println("\n--------------- CHANGING SAVINGS ACCOUNTS RATE OF INTEREST --------------\n");
        System.out.println("Example: enter 0.03 to set up rate on 3%.");
        System.out.println("This operation will change rate of interest for all savings accounts.");
        System.out.print("Please enter rate of interest: ");

        double interest;
        try {
            interest = Double.parseDouble(keyboard.nextLine());

            if (interest < 0) {
                System.out.println();
                System.out.println("--- Interest should be 0 or bigger. ---");
                pressEnter();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("--- Incorrect Interest amount. ---");
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

        System.out.println("\n--- Rate of interest for all savings accounts was changed ---\n");

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
            System.out.println("\n--- Person with this PESEL number is not a customer of MKBank. ---\n");
        }
        return customer;
    }

    public static Account checkAccount(Customer customer, int AccountNumberToCheck) {

        Account account = null;
        for (Account acc : customer.getAccounts()) {
            if (acc.getAccountNumber() == AccountNumberToCheck) {
                account = acc;
                break;
            }
        }
        if (account == null) {
            System.out.println("\n--- This person doesn't own account with this number. ---\n");
        }
        return account;
    }



    public static void pressEnter() {
        System.out.print("Press Enter and go to Menu ");
        keyboard.nextLine();
        System.out.println();
    }
}
