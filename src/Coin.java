
public enum Coin {
	NICKEL(.05), DIME(.10), QUARTER(.25), PENNY(.01);

	double amount;

	Coin(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}
}
