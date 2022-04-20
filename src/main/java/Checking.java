import javax.persistence.Entity;

@Entity
public class Checking extends Account {

    public Checking() {
    }

    public Checking(int number, double balance, double interestRate) {
        super(number, balance, interestRate);
        this.setType(AccountType.checking.name());
    }

}
