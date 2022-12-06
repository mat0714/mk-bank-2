package domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Checking extends Account {

    public Checking() {
    }

    public Checking(int number, BigDecimal balance, BigDecimal interestRate) {
        super(number, balance, interestRate);
        this.setName(AccountName.STANDARD_CHECKING.name());
    }
}
