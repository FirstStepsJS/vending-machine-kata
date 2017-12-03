import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {
	VendingMachine vendingMachine;
	private final Coin NICKEL = new Coin(5.0, 0.750, 1.52, 0);
	private final Coin DIME = new Coin(2.268, 0.705, 1.35, 118);
	private final Coin QUARTER = new Coin(5.670, 0.955, 1.75, 119);
	private final Coin PENNY = new Coin(2.5, 0.750, 1.52, 0);

	@Before
	public void setup() {
		vendingMachine = new VendingMachine();

		// Setting the vending machine inventory to the specified products
		HashMap<String, Product> inventory = new HashMap<>();
		inventory.put("cola", new Product("cola", 1.00, 100));
		inventory.put("chips", new Product("chips", .50, 10));
		inventory.put("candy", new Product("candy", .65, 5));
		vendingMachine.setInventory(inventory);
	}

	@Test
	public void getDisplayText_WhenThereAreNoCoins_DisplaysInsertCoin() {
		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenAValidCoinIsInserted_DisplaysAmountOfTheCoin() {
		vendingMachine.insertCoin(NICKEL);
		assertEquals("$0.05", vendingMachine.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenMultipleValidCoinsAreInserted_DisplaysSumOfTheCoinsValue() {
		vendingMachine.insertCoin(NICKEL);
		vendingMachine.insertCoin(DIME);
		vendingMachine.insertCoin(QUARTER);
		assertEquals("$0.40", vendingMachine.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenAPennyIsInserted_DisplaysInsertCoin() {
		vendingMachine.insertCoin(PENNY);
		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void calculateCoinReturn_WhenAPennyIsInserted_CoinReturnIsOneCent() {
		vendingMachine.insertCoin(PENNY);
		assertEquals("$0.01", vendingMachine.calculateCoinReturn());
	}

	@Test
	public void calculateCoinReturn_WhenMultiplePenniesAreInserted_CoinReturnIsTheSumOfThePennies() {
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);
		assertEquals("$0.03", vendingMachine.calculateCoinReturn());
	}

	@Test
	public void getDisplayText_WhenAProductIsSelectedAndEnoughMoneyIsInserted_DisplaysThankYouAndProductIsDispensed() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);

		vendingMachine.buy("cola");

		assertEquals("THANK YOU", vendingMachine.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenAProductIsSelectedAndNotEnoughMoneyIsInserted_DisplaysPriceProductPrice() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.buy("cola");
		assertEquals("PRICE: $1.00", vendingMachine.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenChipsIsSelectedAndNotEnoughMoneyIsInserted_DisplaysPriceFiftyCents() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.buy("chips");
		assertEquals("PRICE: $0.50", vendingMachine.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenAnInvalidProductIsSelectedAndThereIsMoneyInserted_DisplaysCurrentAmount() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.buy("chocolate");
		assertEquals("$0.25", vendingMachine.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenAnInvalidProductIsSelectedAndThereIsNoMoneyInserted_DisplaysInsertCoin() {
		vendingMachine.buy("chocolate");
		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenTheDisplayIsCheckedAgainAfterAProductIsDispensed_DisplaysInsertCoin() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(DIME);
		vendingMachine.insertCoin(NICKEL);

		vendingMachine.buy("candy");
		vendingMachine.getDisplayText();

		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenTheDisplayIsCheckedAgainAfterPriceOfProductWasDisplayedAndThereIsMoneyInserted_DisplaysCurrentAmount() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);

		vendingMachine.buy("candy");
		vendingMachine.getDisplayText();

		assertEquals("$0.50", vendingMachine.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenTheDisplayIsCheckedAgainAfterPriceOfProductWasDisplayedAndThereIsNoMoneyInserted_DisplaysInsertCoin() {
		vendingMachine.buy("candy");
		vendingMachine.getDisplayText();

		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void getCurrentAmount_WhenCurrentAmountIsCheckedAfterAProductIsDispensed_ReturnsZero() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(DIME);
		vendingMachine.insertCoin(NICKEL);

		vendingMachine.buy("candy");

		assertEquals(0, vendingMachine.getCurrentAmount(), 0);
	}

	@Test
	public void getCoinReturn_WhenAProductIsSelectedThatCostsLessThanCurrentAmount_DifferenceIsReturned() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);

		vendingMachine.buy("cola");

		assertEquals(0.25, vendingMachine.getCoinReturn(), 0);
	}

	@Test
	public void getCoinReturn_WhenProductSelectedCostsLessThanMoneyInsertedAndThereIsExistingCoinReturn_DifferenceIsAddedToCoinReturn() {
		// Penny should go to coin return
		vendingMachine.insertCoin(PENNY);

		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);

		// This quarter should go to coin return because it's in excess of $1.00
		vendingMachine.insertCoin(QUARTER);

		vendingMachine.buy("cola");

		assertEquals(0.26, vendingMachine.getCoinReturn(), 0);
	}

	@Test
	public void getCoinReturn_WhenReturnCoinsButtonIsPressed_CoinReturnBecomesZero() {
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);

		vendingMachine.returnCoins();
		assertEquals(0, vendingMachine.getCoinReturn(), 0);
	}

	public void getDisplayText_WhenReturnCoinsButtonIsPressed_DisplayShowsInsertCoin() {
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);

		vendingMachine.returnCoins();
		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void returnCoins_WhenValidCoinsAreInserted_AmountOfCoinsInsertedIsReturned() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);

		assertEquals(0.50, vendingMachine.returnCoins(), 0);
	}

	@Test
	public void returnCoins_WhenCustomerHasInsertedPenniesAndValidCoinsInTheMachine_AmountOfPenniesAndValidCoinsInsertedIsReturned() {
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);

		assertEquals(0.52, vendingMachine.returnCoins(), 0);
	}

	@Test
	public void getDisplayText_WhenProductSelectedIsOutOfStock_DisplaysSoldOut() {
		// This vending machine is setup to have only one candy in inventory
		VendingMachine vendingMachine2 = new VendingMachine();
		HashMap<String, Product> lowInventory = new HashMap<>();
		lowInventory.put("candy", new Product("candy", .65, 1));
		vendingMachine2.setInventory(lowInventory);

		// A customer buys the only candy in inventory
		vendingMachine2.insertCoin(QUARTER);
		vendingMachine2.insertCoin(QUARTER);
		vendingMachine2.insertCoin(DIME);
		vendingMachine2.insertCoin(NICKEL);
		vendingMachine2.buy("candy");

		// Another customer tries to buy candy (when candy quantity is 0)
		vendingMachine2.insertCoin(QUARTER);
		vendingMachine2.insertCoin(QUARTER);
		vendingMachine2.insertCoin(DIME);
		vendingMachine2.insertCoin(NICKEL);

		vendingMachine2.buy("candy");

		assertEquals("SOLD OUT", vendingMachine2.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenTheDisplayIsCheckedAgainAfterSoldOutWasDisplayedAndThereIsMoneyInserted_DisplaysAmountRemaining() {
		// This vending machine is setup to have only one candy in inventory
		VendingMachine vendingMachine2 = new VendingMachine();
		HashMap<String, Product> lowInventory = new HashMap<>();
		lowInventory.put("candy", new Product("candy", .65, 1));
		vendingMachine2.setInventory(lowInventory);

		// A customer buys the only candy in inventory
		vendingMachine2.insertCoin(QUARTER);
		vendingMachine2.insertCoin(QUARTER);
		vendingMachine2.insertCoin(DIME);
		vendingMachine2.insertCoin(NICKEL);
		vendingMachine2.buy("candy");

		// Another customer tries to buy candy (when candy quantity is 0)
		vendingMachine2.insertCoin(QUARTER);
		vendingMachine2.insertCoin(QUARTER);
		vendingMachine2.insertCoin(DIME);
		vendingMachine2.insertCoin(NICKEL);

		vendingMachine2.buy("candy");
		vendingMachine2.getDisplayText();

		assertEquals("$0.65", vendingMachine2.getDisplayText());
	}


	@Test
	public void getDisplayText_WhentheDisplayIsCheckedAgainAfterSoldOutWasDisplayedAndThereIsNoMoneyInserted_DisplaysInsertCoin() {
		// This vending machine is setup to have only one candy in inventory
		VendingMachine vendingMachine2 = new VendingMachine();
		HashMap<String, Product> lowInventory = new HashMap<>();
		lowInventory.put("candy", new Product("candy", .65, 1));
		vendingMachine2.setInventory(lowInventory);

		// A customer buys the only candy in inventory
		vendingMachine2.insertCoin(QUARTER);
		vendingMachine2.insertCoin(QUARTER);
		vendingMachine2.insertCoin(DIME);
		vendingMachine2.insertCoin(NICKEL);
		vendingMachine2.buy("candy");

		// Another customer tries to buy candy (when candy quantity is 0)
		vendingMachine2.buy("candy");
		vendingMachine2.getDisplayText();

		assertEquals("INSERT COIN", vendingMachine2.getDisplayText());
	}

	@Test
	public void getDisplayText_WhenInvalidCoinIsInserted_DisplaysInsertCoin() {
		Coin invalidCoin = new Coin(5, 1.0, 2.0, 200);
		vendingMachine.insertCoin(invalidCoin);
		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void getCurrentAmount_WhenInvalidCoinIsInserted_CurrentAmountIsZero() {
		Coin invalidCoin = new Coin(5, 1.0, 2.0, 200);
		vendingMachine.insertCoin(invalidCoin);
		assertEquals(0, vendingMachine.getCurrentAmount(), 0);
	}
}
