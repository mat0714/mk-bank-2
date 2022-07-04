import javax.persistence.Entity;

@Entity
public class Savings extends Account {

    public Savings() {
    }

    public Savings(int number, double balance, double interestRate) {
        super(number, balance, interestRate);
        this.setName(AccountName.SMART_SAVINGS.name().toLowerCase());
    }
}
