package domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long number;
    private String name;
    private BigDecimal balance;
    private BigDecimal interestRate;

    public Account() {
    }

    public Account(long number, BigDecimal balance, BigDecimal interestRate) {
        this.number = number;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "\n\t\t  " +
                "Number: " + this.getNumber() +
                ", Name: " + this.getName() +
                ", Balance: " + this.getBalance() + " $" +
                ", Interest: " + this.getInterestRate() + " %";
    }
}
