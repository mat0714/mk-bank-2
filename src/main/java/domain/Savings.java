package domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Savings extends Account {

    public Savings() {
    }

    public Savings(int number, BigDecimal balance, BigDecimal interestRate) {
        super(number, balance, interestRate);
        this.setName(AccountName.SMART_SAVINGS.name());
    }
}
