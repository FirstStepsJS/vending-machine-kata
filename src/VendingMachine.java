
public class VendingMachine {
	private double currentAmount;
	private double coinReturn;
	private Display currentDisplay;

	public Display getCurrentDisplay() {
		return currentDisplay;
	}

	public void setCurrentDisplay(Display newDisplay) {
		this.currentDisplay = newDisplay;
	}

	public VendingMachine() {
		currentAmount = 0;
		coinReturn = 0;
		currentDisplay = Display.INSERT_COIN;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	public double getCoinReturn() {
		return coinReturn;
	}

	public String calculateDisplay() {
		if (getCurrentDisplay().equals(Display.INSERT_COIN)) {
			return "INSERT COIN";
		} else if (getCurrentDisplay().equals(Display.CURRENT_AMOUNT)) {
			return String.format("%.2f", getCurrentAmount());
		} else {
			return "THANK YOU";
		}

	}

	public void insertCoin(Coin coin) {
		if (coin.equals(Coin.PENNY)) {
			coinReturn += coin.getAmount();
		} else {
		currentAmount += coin.getAmount();
			currentDisplay = Display.CURRENT_AMOUNT;
		}
	}

	public String calculateCoinReturn() {
		return String.format("%.2f", getCoinReturn());
	}

	public void buy(Product product) {
		currentDisplay = Display.THANK_YOU;
	}

}
