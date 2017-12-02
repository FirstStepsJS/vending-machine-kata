
public class VendingMachine {
	private double currentAmount;
	private double coinReturn;

	public VendingMachine() {
		currentAmount = 0;
		coinReturn = 0;
	}

	public String calculateDisplay() {
		if (currentAmount == 0) {
		return "INSERT COIN";
		} else {
			return String.format("%.2f", currentAmount);
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
		return String.format("%.2f", coinReturn);
	}

}
