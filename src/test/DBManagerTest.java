import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DBManagerTest {

    DBManager dBManager;
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
            "PersistenceUnit");
    EntityManager entityManager;

    @BeforeAll
    void createExampleCustomerWithAccounts() {
        String name = "John";
        String surname = "Doe";
        int pesel = 800222077;
        Account checkingAccount = new Checking(0, 100.0, 0);
        Account savingsAccount = new Savings(0, 1000.0, 0);
        List<Account> accounts = new ArrayList<>();
        accounts.add(checkingAccount);
        accounts.add(savingsAccount);
        Customer exampleCustomer = new Customer(0, name, surname, pesel, accounts);
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(exampleCustomer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    int getExampleCustomerId() {
        Query query = entityManager.createQuery(
                "SELECT c.id FROM Customer c WHERE c.pesel = 800222077");
        return (int) query.getSingleResult();
    }

    Customer getExampleCustomer() {
        int id = getExampleCustomerId();
        return entityManager.find(Customer.class, id);
    }

    @BeforeEach
    void setUp() {
        dBManager = new DBManager();
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    void closeEM() {
        dBManager.closeEntityManager();
        entityManager.close();
    }

    @AfterAll
    void deleteExampleCustomerWithAccounts() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
                "PersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c WHERE c.pesel = 800222077", Customer.class);
        Customer customer = query.getSingleResult();
        entityManager.remove(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterAll
    void deleteNextExampleCustomerWithoutAccounts() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
                "PersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c WHERE c.pesel = 901125018", Customer.class);
        Customer customer = query.getSingleResult();
        entityManager.remove(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterAll
    void closeEMF() {
        entityManagerFactory.close();
    }

    @Test
    void shouldCreateExampleCustomerWithoutAccounts() {
        // Given
        String name = "Andrew";
        String surname = "Smith";
        int pesel = 901125018;
        List<Account> accounts = new ArrayList<>();
        Customer exampleCustomer = new Customer(0, name, surname, pesel, accounts);

        // When
        dBManager.addCustomer(exampleCustomer);

        // Then
        Query query = entityManager.createQuery(
                "SELECT c.id FROM Customer c WHERE c.pesel = 901125018");
        int id = (int) query.getSingleResult();
        Customer customerFromDatabase = entityManager.find(Customer.class, id);

        assertTrue(customerFromDatabase.getId() > 0);
        assertEquals("Andrew", customerFromDatabase.getName());
        assertEquals("Smith", customerFromDatabase.getSurname());
        assertEquals(901125018, customerFromDatabase.getPesel());
        assertEquals(0, customerFromDatabase.getAccounts().size());
    }

    @Test
    void shouldGetCustomer() {
        // Given
        int id = getExampleCustomerId();

        // When
        Customer customer = dBManager.getCustomer(id);

        // Then
        assertTrue(customer.getId() > 0);
        assertEquals("John", customer.getName());
        assertEquals("Doe", customer.getSurname());
        assertEquals(800222077, customer.getPesel());
        assertEquals(2, customer.getAccounts().size());
    }

    @Test
    void ShouldAddCheckingAccountWithDeposit() {
        // Given
        int id = getExampleCustomerId();

        // When
        dBManager.addAccount(id, AccountType.checking, 200);

        // Then
        Customer customer = getExampleCustomer();
        long numberOfAppropriateAccounts = customer.getAccounts().stream().
                filter(account -> account.getType().equals(AccountType.checking.name()) &&
                        account.getBalance() == 200.0 &&
                        account.getNumber() > 0)
                .count();
        assertEquals(1, numberOfAppropriateAccounts);
    }

    @Test
    void ShouldAddSavingsAccountWithDeposit() {
        // Given
        int id = getExampleCustomerId();

        // When
        dBManager.addAccount(id, AccountType.savings, 600.0);

        // Then
        Customer customer = getExampleCustomer();
        long numberOfAppropriateAccounts = customer.getAccounts().stream().
                filter(account -> account.getType().equals(AccountType.savings.name()) &&
                        account.getBalance() == 600.0  &&
                        account.getNumber() > 0)
                .count();
        assertEquals(1, numberOfAppropriateAccounts);
    }

    @Test
    void ShouldDepositMoney() {
        // Given
        Customer customer = getExampleCustomer();
        Account account = customer.getAccounts().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No account was found."));
        int accountNumber = account.getNumber();
        double accountInitialBalance = account.getBalance();

        // When
        dBManager.deposit(customer.getId(), 200.0, accountNumber);

        // Then
        Query query = entityManager.createQuery(
                "SELECT a.balance FROM Account a WHERE number = :accountNumber")
                .setParameter("accountNumber", accountNumber);
        double accountFinalBalance = (double) query.getSingleResult();
        assertEquals(accountInitialBalance + 200.0, accountFinalBalance);
    }

    @Test
    void ShouldWithdrawMoney() {
        // Given
        Customer customer = getExampleCustomer();
        Account account = customer.getAccounts().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No account was found."));
        int accountNumber = account.getNumber();
        double accountInitialBalance = account.getBalance();

        // When
        dBManager.withdraw(customer.getId(), 200.0, accountNumber);

        // Then
        Query query = entityManager.createQuery(
                        "SELECT a.balance FROM Account a WHERE number = :accountNumber")
                .setParameter("accountNumber", accountNumber);
        double accountFinalBalance = (double) query.getSingleResult();
        assertEquals(accountInitialBalance - 200.0, accountFinalBalance);
    }

    @Test
    void ShouldChangeSavingsAccountsInterestRate() {
        // Given
        int id = getExampleCustomerId();

        // When
        dBManager.changeSavingsInterest(0.45);

        // Then
        Customer customer = entityManager.find(Customer.class, id);
        Account savingsAccount = customer.getAccounts().stream()
                .filter(account -> account.getType().equals(AccountType.savings.name()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No account was found."));
        assertEquals(0.45, savingsAccount.getInterestRate());
    }
}