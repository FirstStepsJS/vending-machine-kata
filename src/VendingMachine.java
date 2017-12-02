import java.util.HashMap;

public class VendingMachine {
	private double currentAmount;
	private double coinReturn;
	private Display display;
	private double selectedItemPrice;
	HashMap<String, Product> inventory;

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
		if (coin.equals(Coin.PENNY)) {
			setCoinReturn(getCoinReturn() + coin.getAmount());
		} else {
			setCurrentAmount(getCurrentAmount() + coin.getAmount());
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
}
