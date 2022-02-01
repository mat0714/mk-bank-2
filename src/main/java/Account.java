public class Account {

    private double balance;
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

    public int getNumber() {
        return number;
    }

    public double getInterest() {
        return interest;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            System.out.println();
            System.out.println("--- Quote should be 0 or bigger. ---");
            System.out.println();
            return;
        }
        this.balance = balance;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println();
            System.out.println("--- Deposit should be bigger than 0. ---");
            System.out.println();
            return false;
        }

        balance += amount;
        System.out.println("");
        System.out.println("--- Money was successfully deposited. ---");
        System.out.println();
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("--- Not enough money on account. ---");
            System.out.println();
            return false;
        }

        balance -= amount;

        System.out.println("");
        System.out.println("--- Money was successfully withdrawn. ---");
        System.out.println();
        return true;

    }

}
