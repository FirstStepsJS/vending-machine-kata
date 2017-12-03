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
	}

	public Display getDisplay() {
		return display;
	}

	private void setDisplay(Display newDisplay) {
		this.display = newDisplay;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	private void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public double getCoinReturn() {
		return coinReturn;
	}

	private void setCoinReturn(double coinReturn) {
		this.coinReturn = coinReturn;
	}

	private double getSelectedItemPrice() {
		return selectedItemPrice;
	}

	private void setSelectedItemPrice(double selectedItemPrice) {
		this.selectedItemPrice = selectedItemPrice;
	}

	public void setInventory(HashMap<String, Product> inventory) {
		this.inventory = inventory;
	}

	public String getDisplayText() {
		String displayText = "";
		Display newDisplay;

		switch (getDisplay()) {
		case INSERT_COIN:
			displayText = "INSERT COIN";
			break;
		case CURRENT_AMOUNT:
			displayText = String.format("$%.2f", getCurrentAmount());
			break;
		case THANK_YOU:
			displayText = "THANK YOU";
			setDisplay(Display.INSERT_COIN);
			break;
		case PRICE:
			newDisplay = currentAmount > 0 ? Display.CURRENT_AMOUNT : Display.INSERT_COIN;
			setDisplay(newDisplay);
			displayText = String.format("PRICE: $%.2f", getSelectedItemPrice());
			break;
		case SOLD_OUT:
			newDisplay = currentAmount > 0 ? Display.CURRENT_AMOUNT : Display.INSERT_COIN;
			setDisplay(newDisplay);
			displayText = "SOLD OUT";
			break;
		}
		return displayText;
	}

	public void insertCoin(Coin coin) {
		double coinValue = calculateCoinValue(coin);

		// Pennies (and non recognized coins which are assigned 0 value) are not
		// accepted
		if (coinValue <= 0.01) {
			setCoinReturn(getCoinReturn() + coinValue);
		} else {
			setCurrentAmount(getCurrentAmount() + coinValue);
			setDisplay(Display.CURRENT_AMOUNT);
		}
	}

	public String calculateCoinReturn() {
		return String.format("$%.2f", getCoinReturn());
	}

	public void buy(String productName) {
		Product product = find(productName);
		double amount = getCurrentAmount();

		if (product != null) {
			double price = product.getPrice();
			double quantity = product.getQuantity();
			if (quantity == 0) {
				setDisplay(Display.SOLD_OUT);
			} else {
				if (amount < price) {
					setSelectedItemPrice(product.getPrice());
					setDisplay(Display.PRICE);
				} else if (amount > price) {
					decreaseInventory(product);
					setCoinReturn(getCoinReturn() + (amount - price));
					setCurrentAmount(0);
					setDisplay(Display.THANK_YOU);

				} else {
					decreaseInventory(product);
					setCurrentAmount(0);
					setDisplay(Display.THANK_YOU);
				}

			}
		}
	}

	private void decreaseInventory(Product product) {
		product.setQuantity(product.getQuantity() - 1);
		inventory.put(product.getName(), product);
	}

	private Product find(String productName) {
		return inventory.get(productName);
	}

	public double returnCoins() {
		double amountToReturn = getCoinReturn() + getCurrentAmount();
		setCoinReturn(0);
		setCurrentAmount(0);
		setDisplay(Display.INSERT_COIN);
		return amountToReturn;
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
