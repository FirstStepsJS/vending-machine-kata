import java.util.HashMap;

public class VendingMachine {
	private double currentAmount;
	private double coinReturn;
	private Display display;
	private double selectedItemPrice;
	private HashMap<String, Product> inventory;

	// Coin specifications obtained from
	// https://www.usmint.gov/learn/coin-and-medal-programs/coin-specifications
	private final Coin NICKEL = new Coin(5.0, 0.750, 1.52, 0);
	private final Coin DIME = new Coin(2.268, 0.705, 1.35, 118);
	private final Coin QUARTER = new Coin(5.670, 0.955, 1.75, 119);
	private final Coin PENNY = new Coin(2.5, 0.750, 1.52, 0);

	public VendingMachine() {
		currentAmount = 0;
		coinReturn = 0;
		display = Display.INSERT_COIN;
		selectedItemPrice = 0;
		inventory = new HashMap<>();
		inventory.put("cola", new Product("cola", 1.00));
		inventory.put("chips", new Product("chips", .50));
		inventory.put("candy", new Product("candy", .65));
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display newDisplay) {
		this.display = newDisplay;
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

	public double getSelectedItemPrice() {
		return selectedItemPrice;
	}

	public void setSelectedItemPrice(double selectedItemPrice) {
		this.selectedItemPrice = selectedItemPrice;
	}

	public String calculateDisplay() {
		Display display = getDisplay();

		if (display.equals(Display.INSERT_COIN)) {
			return "INSERT COIN";
		} else if (display.equals(Display.CURRENT_AMOUNT)) {
			return String.format("%.2f", getCurrentAmount());
		} else if (display.equals(Display.THANK_YOU)) {
			setDisplay(Display.INSERT_COIN);
			return "THANK YOU";
		} else {
			if (currentAmount > 0) {
				setDisplay(Display.CURRENT_AMOUNT);
			} else {
				setDisplay(Display.INSERT_COIN);
			}
			return String.format("%.2f", getSelectedItemPrice());
		}
	}

	public void insertCoin(Coin coin) {
		double coinValue = calculateCoinValue(coin);

		// pennies are not accepted, so they're added to coin return
		if (coinValue <= 0.01) {
			setCoinReturn(getCoinReturn() + coinValue);
		} else {
			setCurrentAmount(getCurrentAmount() + coinValue);
			setDisplay(Display.CURRENT_AMOUNT);
		}
	}

	public String calculateCoinReturn() {
		return String.format("%.2f", getCoinReturn());
	}

	public void buy(String productName) {
		Product product = find(productName);
		double amount = getCurrentAmount();

		if (product != null) {
			double price = product.getPrice();
			if (amount < price) {
				setDisplay(Display.PRICE);
				setSelectedItemPrice(product.getPrice());
			} else if (amount > price) {
				setDisplay(Display.THANK_YOU);
				setCoinReturn(amount - price);
				setCurrentAmount(0);
			} else {
				setDisplay(Display.THANK_YOU);
				setCurrentAmount(0);
			}
		}
	}

	private Product find(String productName) {
		return inventory.get(productName);
	}

	public void returnCoins() {
		setCoinReturn(0);
		setDisplay(Display.INSERT_COIN);
	}

	private double calculateCoinValue(Coin coin) {
		double coinValue = 0;
		if (coin.equals(NICKEL)) {
			coinValue = 0.05;
		} else if (coin.equals(DIME)) {
			coinValue = 0.10;
		} else if (coin.equals(QUARTER)) {
			coinValue = 0.25;
		} else if (coin.equals(PENNY)) {
			coinValue = 0.01;
		} else {
			coinValue = 0;
		}
		return coinValue;
	}
}
