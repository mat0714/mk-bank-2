public class Savings extends Account {

    public Savings(double balance) {
        super();
        this.setAccountType("savings");
        this.deposit(balance);
        this.setInterest(0.05);
    }

    @Override
    public String toString() {
        return "\n\t\t  " +
                "Number: " + this.getAccountNumber() +
                ", Type: " + this.getAccountType() +
                ", Balance: " + this.getBalance() + " $" +
                ", Interest: " + this.getInterest() + " %" ;
    }
}
