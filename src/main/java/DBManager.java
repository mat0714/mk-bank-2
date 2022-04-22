import javax.persistence.*;
import java.util.List;

public class DBManager {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
            "PersistenceUnit");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void addCustomer(Customer customer) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();
    }

    public Customer getCustomer(int customerId) {
        Customer customer = entityManager.find(Customer.class, customerId);
        return customer;
    }

    public void addAccount(int customerId, AccountType accountType, double depositAmount) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = getCustomer(customerId);
        List<Account> accounts = customer.getAccounts();
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
        double newBalance = depositAmount;
        for (Account account : accounts) {
            if (account.getNumber() == accountNumber) {
                double balance = account.getBalance();
                newBalance = balance + depositAmount;
                account.setBalance(newBalance);
            }
        }
        if (newBalance == depositAmount) {
            System.out.println("\n--- Entered account is not owned by this customer. ---\n");
        }
        transaction.commit();
    }

    public void withdraw(int customerId, double withdrawAmount, int accountNumber) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class, customerId);
        List<Account> accounts = customer.getAccounts();
        double newBalance = withdrawAmount;
        for (Account account : accounts) {
            if (account.getNumber() == accountNumber) {
                if (isWithdrawPossible(account, withdrawAmount)) {
                    newBalance = account.getBalance() - withdrawAmount;
                    account.setBalance(newBalance);
                }
            }
        }
        if (newBalance == withdrawAmount) {
            System.out.println("\n--- Entered account is not owned by this customer. ---\n");
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


//    public static int addCustomer(String name, String surname, int pesel) {
//        int customerId = 0;
//        try (Connection connection = connect();
//             PreparedStatement addCustomer = connection.prepareStatement(
//                     "INSERT INTO customers(name, surname, pesel) VALUES(?, ?, ?)",
//                     Statement.RETURN_GENERATED_KEYS)) {
//            addCustomer.setString(1, name);
//            addCustomer.setString(2, surname);
//            addCustomer.setInt(3, pesel);
//            addCustomer.executeUpdate();
//            try (ResultSet addCustomerResults = addCustomer.getGeneratedKeys()) {
//                if (addCustomerResults.next()) {
//                    customerId = addCustomerResults.getInt(1);
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("--- Problem with adding a customer. Please contact with administrator. ---");
//            e.printStackTrace();
//        }
//        return customerId;
//    }
//
//    public static Customer getCustomer(int customerId) {
//        Customer customer = null;
//        try (Connection connection = connect();
//             PreparedStatement getCustomer = connection.prepareStatement(
//                     "SELECT * FROM customers WHERE customer_id = ?")) {
//            getCustomer.setInt(1, customerId);
//            try (ResultSet CustomerResults = getCustomer.executeQuery()) {
//                if (CustomerResults.next()) {
//                    String name = CustomerResults.getString("name");
//                    String surname = CustomerResults.getString("surname");
//                    int pesel = CustomerResults.getInt("pesel");
//                    List<Account> accounts = getCustomerAccounts(connection, customerId);
//                    customer = new Customer(customerId, name, surname, pesel, accounts);
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("--- Problem with database. Please contact with administrator. ---");
//            e.printStackTrace();
//        }
//        return customer;
//    }
//
//    public static List<Account> getCustomerAccounts(Connection connection, int customerId) {
//        List<Account> accounts = new ArrayList<>();
//        try (PreparedStatement getAccounts = connection.prepareStatement(
//                "SELECT accounts.number, accounts.type, accounts.balance, interest_rate.interest FROM mappings " +
//                        "JOIN accounts ON mappings.account_number = accounts.number " +
//                        "JOIN interest_rate ON accounts.type = interest_rate.type " +
//                        "WHERE mappings.customer_id = ?")) {
//            getAccounts.setInt(1, customerId);
//            ResultSet accountsResults = getAccounts.executeQuery();
//            while (accountsResults.next()) {
//                int number = accountsResults.getInt("accounts.number");
//                String type = accountsResults.getString("accounts.type");
//                double balance = accountsResults.getDouble("accounts.balance");
//                double interestRate = accountsResults.getDouble("interest_rate.interest");
//                if (type.equals(AccountType.checking.name())) {
//                    Account checkingAccount = new Checking(number, balance, interestRate);
//                    accounts.add(checkingAccount);
//                } else {
//                    Account savingsAccount = new Savings(number, balance, interestRate);
//                    accounts.add(savingsAccount);
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("--- Problem with database. Please contact with administrator. ---");
//            e.printStackTrace();
//        }
//        return accounts;
//    }
//
//    public static void addAccount(int customerId, AccountType accountType, double depositAmount) {
//        int accountNumber = 0;
//        try (Connection connection = connect()) {
//            connection.setAutoCommit(false);
//            switch (accountType) {
//                case checking -> accountNumber = insertCheckingIntoAccounts(connection, depositAmount);
//                case savings -> accountNumber = insertSavingsIntoAccounts(connection, depositAmount);
//            }
//            int rowsAffectedInMappings = insertIntoMappings(connection, accountNumber, customerId);
//            boolean operationSucceed = accountNumber > 0 && rowsAffectedInMappings > 0;
//            if (operationSucceed) {
//                connection.commit();
//                System.out.println("\n--- Account was successfully created. ---");
//            } else {
//                connection.rollback();
//                System.out.println("--- Impossible to create account. Please contact with administrator. ---");
//            }
//        } catch (SQLException e) {
//            System.out.println("--- Problem with database. Please contact with administrator. ---");
//            e.printStackTrace();
//        }
//    }
//
//    public static int insertCheckingIntoAccounts(Connection connection, double depositAmount) {
//        int accountNumber = 0;
//        try (PreparedStatement addAccount = connection.prepareStatement(
//                "INSERT INTO accounts(type, balance) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
//            addAccount.setString(1, AccountType.checking.name());
//            addAccount.setDouble(2, depositAmount);
//            addAccount.executeUpdate();
//            ResultSet resultSet = addAccount.getGeneratedKeys();
//            if (resultSet.next()) {
//                accountNumber = resultSet.getInt(1);
//            }
//        } catch (SQLException e) {
//            System.out.println("--- Problem with database. Please contact with administrator. ---");
//            e.printStackTrace();
//        }
//        return accountNumber;
//    }
//
//    public static int insertSavingsIntoAccounts(Connection connection, double depositAmount) {
//        int accountNumber = 0;
//        try (PreparedStatement addAccount = connection.prepareStatement(
//                "INSERT INTO accounts(type, balance) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
//            addAccount.setString(1, AccountType.savings.name());
//            addAccount.setDouble(2, depositAmount);
//            addAccount.executeUpdate();
//            ResultSet resultSet = addAccount.getGeneratedKeys();
//            if (resultSet.next()) {
//                accountNumber = resultSet.getInt(1);
//            }
//        } catch (SQLException e) {
//            System.out.println("--- Problem with database. Please contact with administrator. ---");
//            e.printStackTrace();
//        }
//        return accountNumber;
//    }
//
//    private static int insertIntoMappings(Connection connection, int accountNumber, int customerId) throws
//            SQLException {
//        int rowsAffected;
//        try (PreparedStatement addAccountToMappings = connection.prepareStatement(
//                "INSERT INTO mappings(customer_id, account_number) VALUES(?, ?)")) {
//            addAccountToMappings.setInt(1, customerId);
//            addAccountToMappings.setInt(2, accountNumber);
//            rowsAffected = addAccountToMappings.executeUpdate();
//        }
//        return rowsAffected;
//    }
//
//    public static boolean updateBalance(int accountNumber, double newBalance) {
//        boolean updateSucceed = false;
//        try (Connection connection = connect();
//             PreparedStatement updateBalance = connection.prepareStatement(
//                     "UPDATE accounts SET balance = ? WHERE number = ?")) {
//            updateBalance.setDouble(1, newBalance);
//            updateBalance.setInt(2, accountNumber);
//            updateBalance.executeUpdate();
//            updateSucceed = true;
//        } catch (SQLException e) {
//            System.out.println("--- Problem with database. Please contact with administrator. ---");
//            e.printStackTrace();
//        }
//        return updateSucceed;
//    }
//
//    public static List<Customer> getAllCustomers() {
//        List<Customer> customers = new ArrayList<>();
//        try (Connection connection = connect();
//             PreparedStatement getAllCustomers = connection.prepareStatement(
//                     "SELECT * FROM customers")) {
//            ResultSet customerResults = getAllCustomers.executeQuery();
//            while (customerResults.next()) {
//                int customerId = customerResults.getInt("customer_id");
//                String name = customerResults.getString("name");
//                String surname = customerResults.getString("surname");
//                int pesel = customerResults.getInt("pesel");
//                List<Account> accounts = getCustomerAccounts(connection, customerId);
//                Customer customer = new Customer(customerId, name, surname, pesel, accounts);
//                customers.add(customer);
//            }
//        } catch (SQLException e) {
//            System.out.println("--- Problem with database. Please contact with administrator. ---");
//            e.printStackTrace();
//        }
//        return customers;
//    }
//
//    public static boolean changeSavingsInterest(double interestRate) {
//        boolean interestChangeSucceed = false;
//        try (Connection connection = connect();
//             PreparedStatement changeSavingsInterest = connection.prepareStatement(
//                     "UPDATE interest_rate SET interest = ? WHERE type = 'savings'")) {
//            changeSavingsInterest.setDouble(1, interestRate);
//            changeSavingsInterest.executeUpdate();
//            interestChangeSucceed = true;
//        } catch (SQLException e) {
//            System.out.println("--- Problem with database. Please contact with administrator. ---");
//            e.printStackTrace();
//        }
//        return interestChangeSucceed;
//    }
}