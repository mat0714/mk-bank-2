public class Savings extends Account {

    public Savings(double initialDeposit) {
        super();
        this.setType("savings");
        this.deposit(initialDeposit);
        this.setInterestRate(0.05);
    }

    @Override
    public String toString() {
        return "\n\t\t  " +
                "Number: " + this.getNumber() +
                ", Type: " + this.getType() +
                ", Balance: " + this.getBalance() + " $" +
                ", Interest: " + this.getInterestRate() + " %" ;
    }
}
