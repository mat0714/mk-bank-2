import java.util.ArrayList;
import java.util.List;

public class MenuAction {

    DBManager dBManager = new DBManager();

    public void createProfile() {
        System.out.println("\n--------------------- CREATING NEW CUSTOMER PROFILE ---------------------\n");
        System.out.print("Please enter customer name: ");
        String name = UserInput.validateText();
        System.out.print("Please enter customer surname: ");
        String surname = UserInput.validateText();
        System.out.print("Please enter customer PESEL number: ");
        int pesel = UserInput.validatePesel();
        List<Account> accounts = new ArrayList<>();
        Customer customer = new Customer(0, name, surname, pesel, accounts);
        dBManager.addCustomer(customer);
        dBManager.showCustomerDetails(customer.getId());
        Menu.goToMenu();
    }

    public void createAccount() {
        System.out.println("\n---------------------- CREATING ACCOUNT FOR CUSTOMER --------------------\n");
        System.out.print("Please enter customer ID number: ");
        int customerId = UserInput.validateCustomerId();
        Customer customer = dBManager.getCustomer(customerId);
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
            dBManager.showCustomerDetails(customerId);
        }
        Menu.goToMenu();
    }

    public void showSingleProfile() {
        System.out.println("\n---------------------------- CUSTOMER DETAILS ---------------------------\n");
        System.out.print("Please enter customer ID number: ");
        int customerID = UserInput.validateCustomerId();
        dBManager.showCustomerDetails(customerID);
        Menu.goToMenu();
    }

    public void depositMoney() {
        System.out.println("\n---------------------------- DEPOSIT MONEY ---------------------------\n ");
        System.out.print("Please enter customer ID number: ");
        int customerId = UserInput.validateCustomerId();
        Customer customer = dBManager.getCustomer(customerId);
        if (customer != null) {
            dBManager.showCustomerDetails(customerId);
            System.out.print("Please enter deposit amount[$]: ");
            double depositAmount = UserInput.validateMoneyAmount();
            System.out.print("Choose account and enter its number: ");
            int accountNumber = UserInput.validateAccountNumber();
            dBManager.deposit(customerId, depositAmount, accountNumber);
            dBManager.showCustomerDetails(customerId);
            Menu.goToMenu();
        }
    }

    public void withdrawMoney() {
        System.out.println("\n--------------------------- WITHDRAW MONEY ---------------------------\n");
        System.out.print("Please enter customer ID number: ");
        int customerId = UserInput.validateCustomerId();
        Customer customer = dBManager.getCustomer(customerId);
        if (customer != null) {
            System.out.println("\n                          CUSTOMERS DETAILS                           ");
            System.out.println(customer);
            System.out.print("Please enter withdraw amount[$]: ");
            double withdrawAmount = UserInput.validateMoneyAmount();
            System.out.print("Choose account and enter its number: ");
            int accountNumber = UserInput.validateAccountNumber();
            dBManager.withdraw(customerId, withdrawAmount, accountNumber);
            dBManager.showCustomerDetails(customerId);
        }
        Menu.goToMenu();
    }

    public void showAllProfiles() {
        System.out.println("\n-------------------- ALL CUSTOMERS AND THEIR ACCOUNTS -------------------\n");
        for (Customer customer : dBManager.getAllCustomers()) {
            System.out.println(customer);
        }
        Menu.goToMenu();
    }

    public void changeSavingsInterestRate() {
        System.out.println("\n--------------- CHANGING SAVINGS ACCOUNTS RATE OF INTEREST --------------\n");
        System.out.println(
                "Example: enter 0.03 to set up rate on 3%.\n" +
                "This operation will change rate of interest for all existing savings accounts.\n" +
                "Please enter rate of interest: ");
        double interestValue = UserInput.validateInterest();
        dBManager.changeSavingsInterest(interestValue);
        Menu.goToMenu();
    }

    public void quitApp() {
        System.out.println("\nThank you for choosing MK Bank App.");
        System.out.println("We will be happy to see you soon :) \n");
        dBManager.closeEntityManager();
    }
}
