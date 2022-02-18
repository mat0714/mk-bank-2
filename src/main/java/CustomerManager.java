import java.util.ArrayList;
import java.util.List;

public class CustomerManager {

    private static final List<Customer> customers = new ArrayList<>();

    public static void add(Customer customer) {
        customers.add(customer);
    }

    public static Customer getCustomer(int pesel) {
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

    public static Account getCustomerAccount(Customer customer, int accountNumber) {
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

    public static List<Customer> getAllCustomers() {
        return customers;
    }
}
