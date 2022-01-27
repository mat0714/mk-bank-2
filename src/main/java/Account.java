public class Account {

    private String type;
    private double balance;
    private double interest = 0.05;
    private static int startingNumber = 5000000;
    private int number;

    public Account(String type, double balance) {
        this.number = startingNumber++;
        this.type = type;
        this.balance = balance;
        this.interest = interest;
    }

    public int getNumber() {
        return number;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    @Override
    public String toString() {
        return "\n\t\t" +
                "number: " + number +
                ", type: " + type +
                ", balance: " + balance +
                ", interest: " + interest;
    }
}
