import java.util.List;

public class Customer {

    private final int id;
    private final String name;
    private final String surname;
    private final int pesel;
    private final List<Account> accounts;

    public Customer(int id, String name, String surname, int pesel, List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.accounts = accounts;
    }

    public List<Account> getAccountsList() {
        return accounts;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Surname: " + surname +
                ", PESEL: " + pesel +
                ", Accounts: "+ accounts + "\n";
    }
}
