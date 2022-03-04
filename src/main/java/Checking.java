public class Checking extends Account {

    public Checking(int number, double balance, double interestRate) {
        super(number, balance, interestRate);
        this.setType("checking");
    }
}
