public class Account {

    private final int number;
    private String type;
    private final double balance;
    private final double InterestRate;

    public Account(int number, double balance, double interestRate) {
        this.number = number;
        this.balance = balance;
        this.InterestRate = interestRate;
    }

    public int getNumber() {
        return number;
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

    @Override
    public String toString() {
        return "\n\t\t  " +
                "Number: " + this.getNumber() +
                ", Type: " + this.getType() +
                ", Balance: " + this.getBalance() + " $" +
                ", Interest: " + this.getInterestRate() + " %";
    }
}
