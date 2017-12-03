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
		inventory.put("cola", new Product("cola", 1.00));
		inventory.put("chips", new Product("chips", .50));
		inventory.put("candy", new Product("candy", .65));
		vendingMachine.setInventory(inventory);
	}

	@Test
	public void whenThereAreNoCoinsTheMachineDisplaysInsertCoin() {
		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void whenAValidCoinIsInsertedTheMachineDisplaysTheAmountOfTheCoin() {
		vendingMachine.insertCoin(NICKEL);
		assertEquals("$0.05", vendingMachine.getDisplayText());
	}

	@Test
	public void whenMultipleValidCoinsAreInsertedTheMachineDisplaysTheSumOfTheAmountOfTheCoins() {
		vendingMachine.insertCoin(NICKEL);
		vendingMachine.insertCoin(DIME);
		vendingMachine.insertCoin(QUARTER);
		assertEquals("$0.40", vendingMachine.getDisplayText());
	}

	@Test
	public void whenAnInvalidCoinIsInsertedItIsPlacedInTheCoinReturn() {
		vendingMachine.insertCoin(PENNY);
		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
		assertEquals("$0.01", vendingMachine.calculateCoinReturn());
	}

	@Test
	public void whenMultipleInvalidCoinsAreInsertedTheSumOfTheirAmountIsPlacedInTheCoinReturn() {
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);
		assertEquals("$0.03", vendingMachine.calculateCoinReturn());
	}

	@Test
	public void whenAProductIsSelectedAndEnoughMoneyIsInsertedTheProductIsDispensedAndTheMachineDisplaysThankYou() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);

		vendingMachine.buy("cola");

		assertEquals("THANK YOU", vendingMachine.getDisplayText());
	}

	@Test
	public void whenAProductIsSelectedAndNotEnoughMoneyIsInsertedTheMachineDisplaysPriceAndTheProductPrice() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.buy("cola");
		assertEquals("PRICE: $1.00", vendingMachine.getDisplayText());
	}

	@Test
	public void whenChipsIsSelectedAndNotEnoughMoneyIsInsertedTheMachineDisplaysPriceAndFiftyCents() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.buy("chips");
		assertEquals("PRICE: $0.50", vendingMachine.getDisplayText());
	}

	@Test
	public void whenAnInvalidProductIsSelectedAndThereIsMoneyInsertedTheMachineDisplaysCurrentAmount() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.buy("chocolate");
		assertEquals("$0.25", vendingMachine.getDisplayText());
	}

	@Test
	public void whenAnInvalidProductIsSelectedAndThereIsNoMoneyInsertedTheMachineDisplaysInsertCoin() {
		vendingMachine.buy("chocolate");
		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void whenTheDisplayIsCheckedAgainAfterAProductIsDispensedItDisplaysInsertCoin() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(DIME);
		vendingMachine.insertCoin(NICKEL);

		vendingMachine.buy("candy");
		vendingMachine.getDisplayText();

		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void whenTheDisplayIsCheckedAgainAfterPriceOfProductWasDisplayedAndThereIsMoneyInsertedItDisplaysCurrentAmount() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);

		vendingMachine.buy("candy");
		vendingMachine.getDisplayText();

		assertEquals("$0.50", vendingMachine.getDisplayText());
	}

	@Test
	public void whenTheDisplayIsCheckedAgainAfterPriceOfProductWasDisplayedAndThereIsNoMoneyInsertedItDisplaysInsertCoin() {
		vendingMachine.buy("candy");
		vendingMachine.getDisplayText();

		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}

	@Test
	public void whenCurrentAmountIsCheckedAfterAProductIsDispensedItIsZero() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(DIME);
		vendingMachine.insertCoin(NICKEL);

		vendingMachine.buy("candy");

		assertEquals(0, vendingMachine.getCurrentAmount(), 0);
	}

	@Test
	public void whenAProductIsSelectedThatCostsLessThanCurrentAmountTheDifferenceGoesToCoinReturn() {
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);
		vendingMachine.insertCoin(QUARTER);

		vendingMachine.buy("cola");

		assertEquals(0.25, vendingMachine.getCoinReturn(), 0);
	}

	@Test
	public void getCoinReturn_ProductSelectedCostsLessThanMoneyInsertedAndThereIsExistingCoinReturn_DifferenceIsAddedToCoinReturn() {
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
	public void whenReturnCoinsButtonIsPressedTheCoinReturnBecomesZeroAndDisplayShowsInsertCoin() {
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);
		vendingMachine.insertCoin(PENNY);

		vendingMachine.returnCoins();
		assertEquals(0, vendingMachine.getCoinReturn(), 0);
		assertEquals("INSERT COIN", vendingMachine.getDisplayText());
	}
}
