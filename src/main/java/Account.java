public class Account {

    private double balance = 0.00;
    private String accountType;
    private double interest = 0.00;
    private static int startingNumber = 500;
    private final int number;

    public Account() {
        this.number = startingNumber++;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getAccountNumber() {
        return number;
    }

    public double getInterest() {
        return interest;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public void deposit(double DepositValue) {
        if (DepositValue < 0) {
            System.out.println("\n--- Value should be greater than 0. ---");
            return;
        }
        this.balance += DepositValue;
    }

    public void withdraw(double withdrawValue) {
        if (withdrawValue > balance) {
            System.out.println("\n--- Not enough money on account. ---");
            return;
        }
        balance -= withdrawValue;
    }
}
