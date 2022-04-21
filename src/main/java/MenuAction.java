public class MenuAction {

    ProfileManager profileManager = new ProfileManager();
    DBManager dBManager = new DBManager();

    public void createProfile() {
        System.out.println("\n--------------------- CREATING NEW CUSTOMER PROFILE ---------------------\n");
        System.out.print("Please enter customer name: ");
        String name = UserInput.validateText();
        System.out.print("Please enter customer surname: ");
        String surname = UserInput.validateText();
        System.out.print("Please enter customer PESEL number: ");
        int pesel = UserInput.validatePesel();
        Customer customer = new Customer(0, name, surname, pesel, null);
        profileManager.addCustomer(customer);
        profileManager.showCustomerDetails(customer.getId());
        Menu.goToMenu();
    }

    public void createAccount() {
        System.out.println("\n---------------------- CREATING ACCOUNT FOR CUSTOMER --------------------\n");
        System.out.print("Please enter customer ID number: ");
        int customerId = UserInput.validateCustomerId();
        Customer customer = profileManager.getCustomer(customerId);
        if (customer != null) {
            System.out.print("Please enter deposit amount[$]: ");
            double depositAmount = UserInput.validateMoneyAmount();
            System.out.print(
                    "Please choose type of account: \n" +
                    "1) Create checking account. \n" +
                    "2) Create savings account. \n" +
                    "Enter number here: ");
            AccountType accountType = UserInput.validateAccountType();
            dBManager.addAccount(customerId, accountType, depositAmount);
            profileManager.showCustomerDetails(customerId);
        }
        Menu.goToMenu();
    }
//
//    public static void showSingleProfile() {
//        System.out.println("\n---------------------------- CUSTOMER DETAILS ---------------------------\n");
//        System.out.print("Please enter customer ID number: ");
//        int customerID = UserInput.validateCustomerId();
//        ProfileManager.showCustomerDetails(customerID);
//        Menu.goToMenu();
//    }
//
//    public static void depositMoney() {
//        System.out.println("\n---------------------------- DEPOSIT MONEY ---------------------------\n ");
//        System.out.print("Please enter customer ID number: ");
//        int customerID = UserInput.validateCustomerId();
//        Customer customer = ProfileManager.getCustomer(customerID);
//        if (customer != null) {
//            ProfileManager.showCustomerDetails(customerID);
//            System.out.print("Please enter deposit amount[$]: ");
//            double depositAmount = UserInput.validateMoneyAmount();
//            System.out.print("Choose account and enter its number: ");
//            int accountNumber = UserInput.validateAccountNumber();
//            Account account = ProfileManager.getAccount(customerID, accountNumber);
//            if (account != null) {
//                ProfileManager.deposit(account, depositAmount);
//                ProfileManager.showCustomerDetails(customerID);
//            }
//        }
//        Menu.goToMenu();
//    }
//
//    public static void withdrawMoney() {
//        System.out.println("\n--------------------------- WITHDRAW MONEY ---------------------------\n");
//        System.out.print("Please enter customer ID number: ");
//        int customerID = UserInput.validateCustomerId();
//        Customer customer = ProfileManager.getCustomer(customerID);
//        if (customer != null) {
//            System.out.println("\n                          CUSTOMERS DETAILS                           ");
//            System.out.println(customer);
//            System.out.print("Please enter withdraw amount[$]: ");
//            double withdrawAmount = UserInput.validateMoneyAmount();
//            System.out.print("Choose account and enter its number: ");
//            int accountNumber = UserInput.validateAccountNumber();
//            Account account = ProfileManager.getAccount(customerID, accountNumber);
//            if (account != null) {
//                ProfileManager.withdraw(account, withdrawAmount);
//                ProfileManager.showCustomerDetails(customerID);
//            }
//        }
//        Menu.goToMenu();
//    }
//
//    public static void showAllProfiles() {
//        System.out.println("\n-------------------- ALL CUSTOMERS AND THEIR ACCOUNTS -------------------\n");
//        for (Customer customer : DBManager.getAllCustomers()) {
//            System.out.println(customer);
//        }
//        Menu.goToMenu();
//    }
//
//    public static void changeSavingsInterestRate() {
//        System.out.println("\n--------------- CHANGING SAVINGS ACCOUNTS RATE OF INTEREST --------------\n");
//        System.out.println(
//                "Example: enter 0.03 to set up rate on 3%.\n" +
//                "This operation will change rate of interest for all existing savings accounts.\n" +
//                "Please enter rate of interest: ");
//        double interestValue = UserInput.validateInterest();
//        boolean changeInterestSucceed = DBManager.changeSavingsInterest(interestValue);
//        if (changeInterestSucceed) {
//            System.out.println("\n--- Rate of interest for all savings accounts was changed ---\n");
//        }
//        else {
//            System.out.println("\n--- Rate of interest wasn't changed ---\n");
//        }
//        Menu.goToMenu();
//    }
//
//    public static void quitApp() {
//        System.out.println("\nThank you for choosing MK Bank App.");
//        System.out.println("We will be happy to see you soon :) \n");
//    }
}
