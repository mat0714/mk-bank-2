import javax.persistence.Entity;

@Entity
public class Savings extends Account {

    public Savings(int number, double balance, double interestRate) {
        super(number, balance, interestRate);
        this.setType(AccountType.savings.name());
    }
}
