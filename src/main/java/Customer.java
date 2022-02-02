import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String name;
    private String surName;
    private final String pesel;
    private final List<Account> accounts;

    public Customer(String name, String surName, String pesel) {
        this.name = name;
        this.surName = surName;
        this.pesel = pesel;
        this.accounts = new ArrayList<>();
    }

    public String getPesel() {
        return pesel;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return "Customer: " +
                "Name: " + name +
                ", Surname: " + surName +
                ", PESEL: " + pesel +
                ", Accounts: "+ accounts + "\n";
    }
}
