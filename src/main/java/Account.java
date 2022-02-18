public class Account {

    private String type;
    private double balance;
    private double InterestRate;
    private final int number;
    private static int startingNumber = 500;

    public Account() {
        this.number = startingNumber++;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterestRate() {
        return InterestRate;
    }

    public void setInterestRate(double interestRate) {
        this.InterestRate = interestRate;
    }

    public int getNumber() {
        return number;
    }

    public void deposit(double DepositAmount) {
        this.balance += DepositAmount;
    }

    public void withdraw(double withdrawAmount) {
        if (withdrawAmount > balance) {
            System.out.println("\n--- There are not enough funds on that account. ---");
        }
        else {
            balance -= withdrawAmount;
        }
    }
}
