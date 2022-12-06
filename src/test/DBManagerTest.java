import static org.junit.jupiter.api.Assertions.*;

import domain.*;
import org.junit.jupiter.api.*;
import jakarta.persistence.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
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
        long pesel = 80022207795L;
        Account checkingAccount = new Checking(0, BigDecimal.valueOf(100), BigDecimal.valueOf(0));
        Account savingsAccount = new Savings(0, BigDecimal.valueOf(1000), BigDecimal.valueOf(0));
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
    void cleanUp() {
        deleteExampleCustomerWithAccounts();
        deleteExampleCustomerWithoutAccounts();
        closeEMF();
    }

    void deleteExampleCustomerWithAccounts() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
                "PersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM domain.Customer c WHERE c.pesel = 80022207795", Customer.class);
        Customer customer = query.getSingleResult();
        entityManager.remove(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    void deleteExampleCustomerWithoutAccounts() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
                "PersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM domain.Customer c WHERE c.pesel = 90112501844", Customer.class);
        Customer customer = query.getSingleResult();
        entityManager.remove(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    void closeEMF() {
        entityManagerFactory.close();
    }

    long getExampleCustomerId() {
        Query query = entityManager.createQuery(
                "SELECT c.id FROM domain.Customer c WHERE c.pesel = 80022207795");
        return (long) query.getSingleResult();
    }

    Customer getExampleCustomer() {
        long id = getExampleCustomerId();
        return entityManager.find(Customer.class, id);
    }

    @Test
    void shouldCreateExampleCustomerWithoutAccounts() {
        // Given
        String name = "Andrew";
        String surname = "Smith";
        long pesel = 90112501844L;
        List<Account> accounts = new ArrayList<>();
        Customer exampleCustomer = new Customer(0, name, surname, pesel, accounts);

        // When
        dBManager.addCustomer(exampleCustomer);

        // Then
        Query query = entityManager.createQuery(
                "SELECT c.id FROM domain.Customer c WHERE c.pesel = 90112501844");
        long id = (long) query.getSingleResult();
        Customer customerFromDatabase = entityManager.find(Customer.class, id);

        assertTrue(customerFromDatabase.getId() > 0);
        assertEquals("Andrew", customerFromDatabase.getName());
        assertEquals("Smith", customerFromDatabase.getSurname());
        assertEquals(90112501844L, customerFromDatabase.getPesel());
        assertEquals(0, customerFromDatabase.getAccounts().size());
    }

    @Test
    void shouldGetCustomer() {
        // Given
        long id = getExampleCustomerId();

        // When
        Customer customer = dBManager.getCustomer(id);

        // Then
        assertTrue(customer.getId() > 0);
        assertEquals("John", customer.getName());
        assertEquals("Doe", customer.getSurname());
        assertEquals(80022207795L, customer.getPesel());
        assertEquals(2, customer.getAccounts().size());
    }

    @Test
    void shouldAddCheckingAccountWithDeposit() {
        // Given
        long id = getExampleCustomerId();

        // When
        dBManager.addAccount(id, AccountName.STANDARD_CHECKING, BigDecimal.valueOf(200));

        // Then
        Customer customer = getExampleCustomer();
        long numberOfAppropriateAccounts = customer.getAccounts().stream()
                .filter(account -> account.getName().equals(AccountName.STANDARD_CHECKING.name()) &&
                        account.getBalance().compareTo(BigDecimal.valueOf(200)) == 0 &&
                        account.getNumber() > 0)
                .count();
        assertEquals(1, numberOfAppropriateAccounts);
    }

    @Test
    void shouldAddSavingsAccountWithDeposit() {
        // Given
        long id = getExampleCustomerId();

        // When
        dBManager.addAccount(id, AccountName.SMART_SAVINGS, BigDecimal.valueOf(600));

        // Then
        Customer customer = getExampleCustomer();
        long numberOfAppropriateAccounts = customer.getAccounts().stream()
                .filter(account -> account.getName().equals(AccountName.SMART_SAVINGS.name()) &&
                        account.getBalance().compareTo(BigDecimal.valueOf(600)) == 0  &&
                        account.getNumber() > 0)
                .count();
        assertEquals(1, numberOfAppropriateAccounts);
    }

    @Test
    void shouldDepositMoney() {
        // Given
        Customer customer = getExampleCustomer();
        Account account = customer.getAccounts().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No account was found."));
        long accountNumber = account.getNumber();
        BigDecimal accountInitialBalance = account.getBalance();

        // When
        dBManager.deposit(customer.getId(), BigDecimal.valueOf(200), accountNumber);

        // Then
        Query query = entityManager.createQuery(
                "SELECT a.balance FROM Account a WHERE number = :accountNumber")
                .setParameter("accountNumber", accountNumber);
        BigDecimal accountFinalBalance = (BigDecimal) query.getSingleResult();
        assertEquals(accountInitialBalance.add(BigDecimal.valueOf(200)), accountFinalBalance);
    }

    @Test
    void shouldWithdrawMoney() {
        // Given
        Customer customer = getExampleCustomer();
        Account account = customer.getAccounts().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No account was found."));
        long accountNumber = account.getNumber();
        BigDecimal accountInitialBalance = account.getBalance();

        // When
        dBManager.withdraw(customer.getId(), BigDecimal.valueOf(200), accountNumber);

        // Then
        Query query = entityManager.createQuery(
                        "SELECT a.balance FROM Account a WHERE number = :accountNumber")
                .setParameter("accountNumber", accountNumber);
        BigDecimal accountFinalBalance = (BigDecimal) query.getSingleResult();
        assertEquals(accountInitialBalance.subtract(BigDecimal.valueOf(200)), accountFinalBalance);
    }

    @Test
    void shouldChangeSavingsAccountsInterestRate() {
        // Given
        long id = getExampleCustomerId();

        // When
        dBManager.changeSavingsInterest(BigDecimal.valueOf(0.45));

        // Then
        Customer customer = entityManager.find(Customer.class, id);
        Account savingsAccount = customer.getAccounts().stream()
                .filter(account -> account.getName().equals(AccountName.SMART_SAVINGS.name()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No account was found."));
        BigDecimal interestRate = savingsAccount.getInterestRate();
        assertEquals(0, interestRate.compareTo(BigDecimal.valueOf(0.45)));
    }

    @Nested
    class SomethingWentWrongMessage {

        ByteArrayOutputStream systemMessage;

        @AfterEach
        void restoreSystemOutput(){
            System.setOut(System.out);
        }

        ByteArrayOutputStream getSystemMessage() {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            return outContent;
        }

        @Test
        void shouldPrintIdWasNotFound() {
            // Given
            long id = Long.MAX_VALUE;
            systemMessage = getSystemMessage();

            // When
            dBManager.getCustomer(id);

            // Then
            assertEquals("--- ID wasn't found in database. ---", systemMessage.toString().trim());
        }

        @Test
        void shouldPrintAccountIsNotOwnedByThisCustomerWhenMakingDeposit() {
            // Given
            Customer customer = getExampleCustomer();
            long accountNumber = Long.MAX_VALUE;
            systemMessage = getSystemMessage();

            // When
            dBManager.deposit(customer.getId(), BigDecimal.valueOf(200), accountNumber);

            // Then
            assertEquals("--- Entered account is not owned by this customer. ---",
                    systemMessage.toString().trim());

        }

        @Test
        void shouldPrintAccountIsNotOwnedByThisCustomerWhenMakingWithdraw() {
            // Given
            Customer customer = getExampleCustomer();
            long accountNumber = Long.MAX_VALUE;
            systemMessage = getSystemMessage();

            // When
            dBManager.withdraw(customer.getId(), BigDecimal.valueOf(200), accountNumber);

            // Then
            assertEquals("--- Entered account is not owned by this customer. ---",
                    systemMessage.toString().trim());
        }

        @Test
        void shouldPrintNotEnoughFunds() {
            // Given
            Customer customer = getExampleCustomer();
            Account account = customer.getAccounts().stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No account was found."));
            long accountNumber = account.getNumber();
            systemMessage = getSystemMessage();

            // When
            dBManager.withdraw(customer.getId(), BigDecimal.valueOf(20000), accountNumber);

            // Then
            assertEquals("--- Not enough funds on this account. ---",
                    systemMessage.toString().trim());
        }
    }
}