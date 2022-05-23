import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private long pesel;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Account> accounts;

    public Customer() {
    }

    public Customer(int id, String name, String surname, long pesel, List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.accounts = accounts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
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
