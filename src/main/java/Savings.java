public class Savings extends Account {

    public Savings(double balance) {
        super();
        this.setAccountType("savings");
        this.setBalance(balance);
        this.setInterest(0.05);
    }

    @Override
    public String toString() {
        return "\n\t\t  " +
                "number: " + this.getNumber() +
                ", type: " + this.getAccountType() +
                ", balance: " + this.getBalance() +
                ", interest: " + this.getInterest();
    }
}
