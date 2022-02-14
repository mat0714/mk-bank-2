public class Checking extends Account {

    public Checking(double initialDeposit) {
        super();
        this.setType("checking");
        this.deposit(initialDeposit);
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
