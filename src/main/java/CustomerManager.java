import java.util.List;

public class CustomerManager {

    public static int addCustomer(String name, String surname, int pesel) {
        int customerId = DBConnector.addCustomer(name, surname, pesel);
        System.out.println("\n--- Customer profile was successfully created. ---\n");
        System.out.println("--- Your ID number is: " + customerId + " ---");
        return customerId;
    }

    public static Customer getCustomer(int customerId) {
        Customer customer = DBConnector.getCustomer(customerId);
        if (customer == null) {
            System.out.println("\n--- ID wasn't found in database. ---\n");
        }
        return customer;
    }

    public static void showCustomerDetails(int customerId) {
        Customer customer = DBConnector.getCustomer(customerId);
        if (customer != null) {
            System.out.println("\n                        ACTUAL CUSTOMER DETAILS                       ");
            System.out.println(customer);
        } else {
            System.out.println("\n--- ID wasn't found in database. ---\n");
        }
    }

    public static Account getAccount(int customerId, int enteredAccountNumber) {
        Customer customer = getCustomer(customerId);
        Account existingAccount = null;
        List<Account> accounts = customer.getAccountsList();
        for (Account account : accounts) {
            if (account.getNumber() == enteredAccountNumber) {
                existingAccount = account;
            }
        }
        return existingAccount;
    }

    public static void deposit(Account account, double depositAmount) {
        double balance = account.getBalance();
        double newBalance = balance + depositAmount;
        int accountNumber = account.getNumber();
        boolean depositSucceed = DBConnector.updateBalance(accountNumber, newBalance);
        if (depositSucceed) {
            System.out.println("\n--- Deposit was made. ---");
        }
    }

    public static void withdraw(Account account, double withdrawAmount) {
        double balance = account.getBalance();
        if (withdrawAmount <= balance) {
            double newBalance = balance - withdrawAmount;
            int accountNumber = account.getNumber();
            boolean withdrawSucceed = DBConnector.updateBalance(accountNumber, newBalance);
            if (withdrawSucceed) {
                System.out.println("\n--- Withdraw was made. ---");
            }
        }
        else {
            System.out.println("\n--- Not enough funds on this account. ---");
        }
    }
}
