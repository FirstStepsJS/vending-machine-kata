
public class VendingMachine {
	private double currentAmount;

	public VendingMachine() {
		currentAmount = 0;
	}

	public String calculateDisplay() {
		if (currentAmount == 0) {
		return "INSERT COIN";
		} else {
			return Double.toString(currentAmount);
		}
	}

	public void insertCoin(Coin coin) {
		currentAmount = coin.getAmount();
	}
}
