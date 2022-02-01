public class Checking extends Account {

    public Checking(double balance) {
        super();
        this.setAccountType("checking");
        this.setBalance(balance);
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
