import domain.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
            "PersistenceUnit");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void addCustomer(Customer customer) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(customer);
        System.out.println("\n--- Customer profile was successfully created. ---");
        transaction.commit();
    }

    public Customer getCustomer(long customerId) {
        Customer customer = entityManager.find(Customer.class, customerId);
        if (customer == null) {
            System.out.println("\n--- ID wasn't found in database. ---\n");
        }
        return customer;
    }

    public void showCustomerDetails(long customerId) {
        Customer customer = getCustomer(customerId);
        if (customer != null) {
            System.out.println("\n                        ACTUAL CUSTOMER DETAILS                       ");
            System.out.println(customer);
        }
    }

    public void addAccount(long customerId, AccountName accountName, BigDecimal depositAmount) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = getCustomer(customerId);
        List<Account> accounts = customer.getAccounts();
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        switch (accountName) {
            case STANDARD_CHECKING -> {
                Checking checking = new Checking(0, depositAmount, BigDecimal.valueOf(0.0));
                accounts.add(checking);
            }
            case SMART_SAVINGS -> {
                Savings savings = new Savings(0, depositAmount, BigDecimal.valueOf(0.05));
                accounts.add(savings);
            }
        }
        transaction.commit();
    }

    public void deposit(long customerId, BigDecimal depositAmount, long accountNumber) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class, customerId);
        List<Account> accounts = customer.getAccounts();
        boolean accountExist = false;
        for (Account account : accounts) {
            if (account.getNumber() == accountNumber) {
                accountExist = true;
                BigDecimal balance = account.getBalance();
                BigDecimal newBalance = balance.add(depositAmount);
                account.setBalance(newBalance);
            }
        }
        if (!accountExist) {
            System.out.println("\n--- Entered account is not owned by this customer. ---");
        }
        transaction.commit();
    }

    public void withdraw(long customerId, BigDecimal withdrawAmount, long accountNumber) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class, customerId);
        List<Account> accounts = customer.getAccounts();
        boolean accountExist = false;
        for (Account account : accounts) {
            if (account.getNumber() == accountNumber) {
                accountExist = true;
                if (isWithdrawPossible(account, withdrawAmount)) {
                    BigDecimal newBalance = account.getBalance().subtract(withdrawAmount);
                    account.setBalance(newBalance);
                }
            }
        }
        if (!accountExist) {
            System.out.println("\n--- Entered account is not owned by this customer. ---");
        }
        transaction.commit();
    }

    public boolean isWithdrawPossible(Account account, BigDecimal withdrawAmount) {
        BigDecimal balance = account.getBalance();
        if (withdrawAmount.compareTo(balance) <= 0) {
            return true;
        }
        System.out.println("\n--- Not enough funds on this account. ---");
        return false;
    }

    public List<Customer> getAllCustomers() {
        TypedQuery<Customer> query = entityManager.createQuery("select c from domain.Customer c", Customer.class);
        return query.getResultList();
    }

    public void changeSavingsInterest(BigDecimal newInterestRate) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("update Account a set a.interestRate = :newInterestRate")
                .setParameter("newInterestRate", newInterestRate)
                .executeUpdate();
        transaction.commit();
        System.out.println("Interest Rate of all existing savings accounts was changed");
    }

    public void closeEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
}