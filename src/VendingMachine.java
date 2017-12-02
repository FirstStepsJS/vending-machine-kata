
public class VendingMachine {
	private double currentAmount;
	private double coinReturn;

	public VendingMachine() {
		currentAmount = 0;
		coinReturn = 0;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	public double getCoinReturn() {
		return coinReturn;
	}

	public String calculateDisplay() {
		if (getCurrentAmount() == 0) {
		return "INSERT COIN";
		} else {
			return String.format("%.2f", getCurrentAmount());
		}
	}

	public void insertCoin(Coin coin) {
		if (coin.equals(Coin.PENNY)) {
			coinReturn += coin.getAmount();
		} else {
		currentAmount += coin.getAmount();
		}
	}

	public String calculateCoinReturn() {
		return String.format("%.2f", getCoinReturn());
	}

}
