import java.util.HashMap;

public class VendingMachine {
	private double currentAmount;
	private double coinReturn;
	private Display currentDisplay;
	private double selectedItemPrice;
	HashMap<String, Product> inventory;

	public VendingMachine() {
		currentAmount = 0;
		coinReturn = 0;
		currentDisplay = Display.INSERT_COIN;
		selectedItemPrice = 0;
		inventory = new HashMap<>();
		inventory.put("cola", new Product("cola", 1.00));
		inventory.put("chips", new Product("chips", .50));
		inventory.put("candy", new Product("candy", .65));
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

	public double getSelectedItemPrice() {
		return selectedItemPrice;
	}

	public void setSelectedItemPrice(double selectedItemPrice) {
		this.selectedItemPrice = selectedItemPrice;
	}

	public String calculateDisplay() {
		Display display = getCurrentDisplay();

		if (display.equals(Display.INSERT_COIN)) {
			return "INSERT COIN";
		} else if (display.equals(Display.CURRENT_AMOUNT)) {
			return String.format("%.2f", getCurrentAmount());
		} else if (display.equals(Display.THANK_YOU)) {
			setCurrentDisplay(Display.INSERT_COIN);
			return "THANK YOU";
		} else {
			if (currentAmount > 0) {
				setCurrentDisplay(Display.CURRENT_AMOUNT);
			} else {
				setCurrentDisplay(Display.INSERT_COIN);
			}
			return String.format("%.2f", getSelectedItemPrice());
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

	public void buy(String productName) {
		Product product = find(productName);
		if (product != null) {
			if (getCurrentAmount() < product.getPrice()) {
				setCurrentDisplay(Display.PRICE);
				setSelectedItemPrice(product.getPrice());
			} else {
				setCurrentDisplay(Display.THANK_YOU);
				currentAmount = 0;
			}
		}
	}

	private Product find(String productName) {
		return inventory.get(productName);
	}
}
