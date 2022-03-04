public class MenuAction {

    public static void createCustomerProfile() {
        System.out.println("\n--------------------- CREATING NEW CUSTOMER PROFILE ---------------------\n");
        System.out.print("Please enter customer name: ");
        String name = UserInput.validateText();
        System.out.print("Please enter customer surname: ");
        String surName = UserInput.validateText();
        System.out.print("Please enter customer PESEL number: ");
        int pesel = UserInput.validatePesel();
        int customerId = CustomerManager.addCustomer(name, surName, pesel);
        CustomerManager.showCustomerDetails(customerId);
        Menu.goToMenu();
    }

    public static void createAccount() {
        System.out.println("\n---------------------- CREATING ACCOUNT FOR CUSTOMER --------------------\n");
        System.out.print("Please enter customer ID number: ");
        int customerId = UserInput.validateCustomerId();
        Customer customer = CustomerManager.getCustomer(customerId);
        if (customer != null) {
            System.out.print("Please enter deposit amount[$]: ");
            double depositAmount = UserInput.validateMoneyAmount();
            System.out.print(
                    "Please choose type of account: \n" +
                    "1) Create checking account. \n" +
                    "2) Create savings account. \n" +
                    "Enter number here: ");
            int accountType = UserInput.validateAccountType();
            switch (accountType) {
                case 1 -> {
                    DBConnector.addCheckingAccount(customerId, depositAmount);
                    CustomerManager.showCustomerDetails(customerId);
                }
                case 2 -> {
                    DBConnector.addSavingsAccount(customerId, depositAmount);
                    CustomerManager.showCustomerDetails(customerId);
                }
            }
        }
        Menu.goToMenu();
    }

    public static void showSingleProfile() {
        System.out.println("\n---------------------------- CUSTOMER DETAILS ---------------------------\n");
        System.out.print("Please enter customer ID number: ");
        int customerID = UserInput.validateCustomerId();
        CustomerManager.showCustomerDetails(customerID);
        Menu.goToMenu();
    }

    public static void depositMoney() {
        System.out.println("\n---------------------------- DEPOSIT MONEY ---------------------------\n ");
        System.out.print("Please enter customer ID number: ");
        int customerID = UserInput.validateCustomerId();
        Customer customer = CustomerManager.getCustomer(customerID);
        if (customer != null) {
            CustomerManager.showCustomerDetails(customerID);
            System.out.print("Please enter deposit amount[$]: ");
            double depositAmount = UserInput.validateMoneyAmount();
            System.out.print("Choose account and enter its number: ");
            int accountNumber = UserInput.validateAccountNumber();
            Account account = CustomerManager.getAccount(customerID, accountNumber);
            if (account != null) {
                CustomerManager.deposit(account, depositAmount);
                CustomerManager.showCustomerDetails(customerID);
            }
        }
        Menu.goToMenu();
    }

    public static void withdrawMoney() {
        System.out.println("\n--------------------------- WITHDRAW MONEY ---------------------------\n");
        System.out.print("Please enter customer ID number: ");
        int customerID = UserInput.validateCustomerId();
        Customer customer = CustomerManager.getCustomer(customerID);
        if (customer != null) {
            System.out.println("\n                          CUSTOMERS DETAILS                           ");
            System.out.println(customer);
            System.out.print("Please enter withdraw amount[$]: ");
            double withdrawAmount = UserInput.validateMoneyAmount();
            System.out.print("Choose account and enter its number: ");
            int accountNumber = UserInput.validateAccountNumber();
            Account account = CustomerManager.getAccount(customerID, accountNumber);
            if (account != null) {
                CustomerManager.withdraw(account, withdrawAmount);
                CustomerManager.showCustomerDetails(customerID);
            }
        }
        Menu.goToMenu();
    }

    public static void showAllProfiles() {
        System.out.println("\n-------------------- ALL CUSTOMERS AND THEIR ACCOUNTS -------------------\n");
        for (Customer customer : DBConnector.getAllCustomers()) {
            System.out.println(customer);
        }
        Menu.goToMenu();
    }

    public static void changeSavingsInterestRate() {
        System.out.println("\n--------------- CHANGING SAVINGS ACCOUNTS RATE OF INTEREST --------------\n");
        System.out.println(
                "Example: enter 0.03 to set up rate on 3%.\n" +
                "This operation will change rate of interest for all existing savings accounts.\n" +
                "Please enter rate of interest: ");
        double interestValue = UserInput.validateInterest();
        boolean changeInterestSucceed = DBConnector.changeSavingsInterest(interestValue);
        if (changeInterestSucceed) {
            System.out.println("\n--- Rate of interest for all savings accounts was changed ---\n");
        }
        else {
            System.out.println("\n--- Rate of interest wasn't changed ---\n");
        }
        Menu.goToMenu();
    }

    public static void quitApp() {
        System.out.println("\nThank you for choosing MK Bank App.");
        System.out.println("We will be happy to see you soon :) \n");
    }
}
