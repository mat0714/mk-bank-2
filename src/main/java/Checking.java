public class Checking extends Account {

    public Checking(double balance) {
        super();
        this.setAccountType("checking");
        this.deposit(balance);
    }

    @Override
    public String toString() {
        return "\n\t\t  " +
                "Number: " + this.getAccountNumber() +
                ", Type: " + this.getAccountType() +
                ", Balance: " + this.getBalance() + " $" +
                ", Interest: " + this.getInterest() + " %";
    }
}
