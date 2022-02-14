import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuAction {

    private static final List<Customer> customers = new ArrayList<>();
    private static final Scanner keyboard = new Scanner(System.in);

    public static void createCustomerProfile() {
        System.out.println("\n--------------------- CREATING NEW CUSTOMER PROFILE ---------------------\n");
        System.out.print("Please enter customer name: ");
        String name = UserInput.verifyText();
        System.out.print("Please enter customer surname: ");
        String surName = UserInput.verifyText();
        System.out.print("Please enter customer PESEL number: ");
        int pesel = UserInput.verifyPesel();
        Customer customer = new Customer(name, surName, pesel);
        customers.add(customer);
        System.out.println("\n--- Customer profile was successfully created. ---\n");
        System.out.println("                 CUSTOMER DETAILS AFTER OPERATION                 ");
        System.out.println(customer);
        pressEnter();
    }

    public static void createAccount() {
        System.out.println("\n---------------------- CREATING ACCOUNT FOR CUSTOMER --------------------\n");;
        System.out.print("Please enter customer PESEL number: ");
        int pesel = UserInput.verifyPesel();
        Customer customer = findCustomerProfile(pesel);
        if (customer != null) {
            System.out.println("\n--- Customer was found. You can create account. ---\n");
            System.out.print("Please enter deposit amount[$]: ");
            double depositAmount = UserInput.verifyMoneyAmount();
            System.out.print("Please choose type of account: \n" +
                    "1) Create checking account. \n" +
                    "2) Create savings account. \n" +
                    "Enter number here: ");
            int accountType = UserInput.verifyAccountTypeChoice();
            switch (accountType) {
                case 1 -> {
                    customer.addAccount(new Checking(depositAmount));
                    System.out.println("\n--- New checking account was successfully created. ---\n");
                }
                case 2 -> {
                    customer.addAccount(new Savings(depositAmount));
                    System.out.println("\n--- New savings account was successfully created. ---\n");
                }
            }
        }
        pressEnter();
    }


    public static void showSingleProfile() {
        System.out.println("\n---------------------------- CUSTOMER DETAILS ---------------------------\n");
        System.out.print("Please enter customer PESEL number: ");
        int pesel = UserInput.verifyPesel();
        Customer customer = findCustomerProfile(pesel);
        if (customer != null) {
            System.out.println("\n" + customer);
        }
        pressEnter();
    }

    public static void depositMoney() {
        System.out.println("\n---------------------------- DEPOSIT MONEY ---------------------------\n ");
        System.out.print("Please enter customer PESEL number: ");
        int pesel = UserInput.verifyPesel();
        Customer customer = findCustomerProfile(pesel);
        if (customer != null) {
            System.out.println("\n                          CUSTOMERS DETAILS                           ");
            System.out.println(customer);
            System.out.print("Please enter deposit amount[$]: ");
            double depositAmount = UserInput.verifyMoneyAmount();
            System.out.print("Choose account and enter its number: ");
            int accountNumber = UserInput.verifyAccountNumberChoice();
            Account account = findCustomerAccount(customer, accountNumber);
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
        System.out.print("Please enter customer PESEL number: ");
        int pesel = UserInput.verifyPesel();
        Customer customer = findCustomerProfile(pesel);
        if (customer != null) {
            System.out.println("\n                          CUSTOMERS DETAILS                           ");
            System.out.println(customer);
            System.out.print("Please enter withdraw amount[$]: ");
            double withdrawAmount = UserInput.verifyMoneyAmount();
            System.out.print("Choose account and enter its number: ");
            int accountNumber = UserInput.verifyAccountNumberChoice();
            Account account = findCustomerAccount(customer, accountNumber);
            if (account != null) {
                account.withdraw(withdrawAmount);
                System.out.println("\n                 CUSTOMER DETAILS AFTER OPERATION                 ");
                System.out.println(customer);
            }
        }
        pressEnter();
    }


    public static void showAllProfiles() {
        System.out.println("\n-------------------- ALL CUSTOMERS AND THEIR ACCOUNTS -------------------\n");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        pressEnter();
    }

    public static void changeSavingsInterestRate() {
        System.out.println("\n--------------- CHANGING SAVINGS ACCOUNTS RATE OF INTEREST --------------\n");
        System.out.println("Example: enter 0.03 to set up rate on 3%.\n" +
                "This operation will change rate of interest for all existing savings accounts.\n" +
                "Please enter rate of interest: ");
        double interestValue = UserInput.verifyInterest();
        for (Customer customer : customers) {
            List<Account> accounts = customer.getAccountsList();
            for (Account account : accounts) {
                if (account.getType().equals("savings")) {
                    account.setInterestRate(interestValue);
                }
            }
        }
        System.out.println("\n--- Rate of interest for all savings accounts was changed ---\n");
        pressEnter();
    }

    public static void quitApp() {
        System.out.println("\nThank you for choosing MK Bank App.");
        System.out.println("We will be happy to see you soon :) \n");
    }

    public static Customer findCustomerProfile(int pesel) {
        Customer customerProfile = null;
        for (Customer customer : customers) {
            if (customer.getPesel() == pesel) {
                customerProfile = customer;
                break;
            }
        }
        if (customerProfile == null) {
            System.out.println("\n--- Person with this PESEL number is not a customer of MKBank. ---\n");
        }
        return customerProfile;
    }

    public static Account findCustomerAccount(Customer customer, int accountNumber) {
        Account customerAccount = null;
        for (Account account : customer.getAccountsList()) {
            if (account.getNumber() == accountNumber) {
                customerAccount = account;
                break;
            }
        }
        if (customerAccount == null) {
            System.out.println("\n--- This person doesn't own account with this number. ---\n");
        }
        return customerAccount;
    }

    public static void pressEnter() {
        System.out.print("Press Enter and go to Menu ");
        keyboard.nextLine();
        System.out.println();
    }
}
