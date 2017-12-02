
public class VendingMachine {
	private double currentAmount;
	private double coinReturn;
	private Display currentDisplay;

	public VendingMachine() {
		currentAmount = 0;
		coinReturn = 0;
		currentDisplay = Display.INSERT_COIN;
	}

	public Display getCurrentDisplay() {
		return currentDisplay;
	}

	public void setCurrentDisplay(Display newDisplay) {
		this.currentDisplay = newDisplay;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public double getCoinReturn() {
		return coinReturn;
	}

	public void setCoinReturn(double coinReturn) {
		this.coinReturn = coinReturn;
	}

	public String calculateDisplay() {
		Display display = getCurrentDisplay();

		if (display.equals(Display.INSERT_COIN)) {
			return "INSERT COIN";
		} else if (display.equals(Display.CURRENT_AMOUNT)) {
			return String.format("%.2f", getCurrentAmount());
		} else if (display.equals(Display.THANK_YOU)) {
			return "THANK YOU";
		} else {
			return String.format("%.2f", 1.00);
		}
	}

	public void insertCoin(Coin coin) {
		if (coin.equals(Coin.PENNY)) {
			setCoinReturn(getCoinReturn() + coin.getAmount());
		} else {
			setCurrentAmount(getCurrentAmount() + coin.getAmount());
			setCurrentDisplay(Display.CURRENT_AMOUNT);
		}
	}

	public String calculateCoinReturn() {
		return String.format("%.2f", getCoinReturn());
	}

	public void buy(Product product) {
		if (getCurrentAmount() < product.getPrice()) {
			setCurrentDisplay(Display.PRICE);
		} else
		setCurrentDisplay(Display.THANK_YOU);
	}
}
