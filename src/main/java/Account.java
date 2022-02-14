public class Account {

    private String type;
    private double balance;
    private double InterestRate;
    private final int number;
    private static int startingNumber = 500;

    public Account() {
        this.number = startingNumber++;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public double getInterestRate() {
        return InterestRate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInterestRate(double interestRate) {
        this.InterestRate = interestRate;
    }

    public void deposit(double DepositAmount) {
        this.balance += DepositAmount;
    }

    public void withdraw(double withdrawAmount) {
        balance -= withdrawAmount;
    }
}
