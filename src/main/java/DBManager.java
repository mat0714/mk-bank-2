import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
            "PersistenceUnit");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void addCustomer(Customer customer) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(customer);
        System.out.println("\n--- Customer profile was successfully created. ---");
        transaction.commit();
    }

    public Customer getCustomer(int customerId) {
        Customer customer = entityManager.find(Customer.class, customerId);
        if (customer == null) {
            System.out.println("\n--- ID wasn't found in database. ---\n");
        }
        return customer;
    }

    public void showCustomerDetails(int customerId) {
        Customer customer = getCustomer(customerId);
        if (customer != null) {
            System.out.println("\n                        ACTUAL CUSTOMER DETAILS                       ");
            System.out.println(customer);
        }
    }

    public void addAccount(int customerId, AccountType accountType, double depositAmount) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = getCustomer(customerId);
        List<Account> accounts = customer.getAccounts();
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        switch (accountType) {
            case checking -> {
                Checking checking = new Checking(0, depositAmount, 0.0);
                accounts.add(checking);
            }
            case savings -> {
                Savings savings = new Savings(0, depositAmount, 0.05);
                accounts.add(savings);
            }
        }
        transaction.commit();
    }
    public void deposit(int customerId, double depositAmount, int accountNumber) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class, customerId);
        List<Account> accounts = customer.getAccounts();
        boolean accountExist = false;
        for (Account account : accounts) {
            if (account.getNumber() == accountNumber) {
                accountExist = true;
                double balance = account.getBalance();
                double newBalance = balance + depositAmount;
                account.setBalance(newBalance);
            }
        }
        if (!accountExist) {
            System.out.println("\n--- Entered account is not owned by this customer. ---");
        }
        transaction.commit();
    }

    public void withdraw(int customerId, double withdrawAmount, int accountNumber) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class, customerId);
        List<Account> accounts = customer.getAccounts();
        boolean accountExist = false;
        for (Account account : accounts) {
            if (account.getNumber() == accountNumber) {
                accountExist = true;
                if (isWithdrawPossible(account, withdrawAmount)) {
                    double newBalance = account.getBalance() - withdrawAmount;
                    account.setBalance(newBalance);
                }
            }
        }
        if (!accountExist) {
            System.out.println("\n--- Entered account is not owned by this customer. ---");
        }
        transaction.commit();
    }

    public boolean isWithdrawPossible(Account account, double withdrawAmount) {
        double balance = account.getBalance();
        if (withdrawAmount <= balance) {
            return true;
        }
        System.out.println("\n--- Not enough funds on this account. ---");
        return false;
    }

    public List<Customer> getAllCustomers() {
//        List<Customer> customers = entityManager.createNativeQuery("SELECT * FROM customers").getResultList();

        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c", Customer.class);
        List<Customer> customers = query.getResultList();
        return customers;
    }

    public void changeSavingsInterest(double newInterestRate) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("update Account a set a.interestRate = :newInterestRate")
                .setParameter("newInterestRate", newInterestRate)
                .executeUpdate();
        transaction.commit();
    }
}