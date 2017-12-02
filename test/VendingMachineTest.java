import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {
	VendingMachine vendingMachine;

	@Before
	public void setup() {
		vendingMachine = new VendingMachine();
	}

	@Test
	public void whenThereAreNoCoinsTheMachineDisplaysInsertCoin() {
		assertEquals("INSERT COIN", vendingMachine.calculateDisplay());
	}

	@Test
	public void whenAValidCoinIsInsertedTheMachineDisplaysTheAmountOfTheCoin() {
		vendingMachine.insertCoin(Coin.NICKEL);
		assertEquals("0.05", vendingMachine.calculateDisplay());
	}

	@Test
	public void whenMultipleValidCoinsAreInsertedTheMachineDisplaysTheSumOfTheAmountOfTheCoins() {
		vendingMachine.insertCoin(Coin.NICKEL);
		vendingMachine.insertCoin(Coin.DIME);
		vendingMachine.insertCoin(Coin.QUARTER);
		assertEquals("0.40", vendingMachine.calculateDisplay());
	}

	@Test
	public void whenAnInvalidCoinIsInsertedItIsPlacedInTheCoinReturn() {
		vendingMachine.insertCoin(Coin.PENNY);
		assertEquals("INSERT COIN", vendingMachine.calculateDisplay());
		assertEquals("0.01", vendingMachine.calculateCoinReturn());
	}

	@Test
	public void whenMultipleInvalidCoinsAreInsertedTheSumOfTheirAmountIsPlacedInTheCoinReturn() {
		vendingMachine.insertCoin(Coin.PENNY);
		vendingMachine.insertCoin(Coin.PENNY);
		vendingMachine.insertCoin(Coin.PENNY);
		assertEquals("0.03", vendingMachine.calculateCoinReturn());
	}

	@Test
	public void whenAProductIsSelectedAndEnoughMoneyIsInsertedTheProductIsDispensedAndTheMachineDisplaysThankYou() {
		vendingMachine.insertCoin(Coin.QUARTER);
		vendingMachine.insertCoin(Coin.QUARTER);
		vendingMachine.insertCoin(Coin.QUARTER);
		vendingMachine.insertCoin(Coin.QUARTER);
		Product selectedProduct = new Product("cola", 1.00);

		vendingMachine.buy(selectedProduct);

		assertEquals("THANK YOU", vendingMachine.calculateDisplay());
	}

	@Test
	public void whenAProductIsSelectedAndNotEnoughMoneyIsInsertedTheMachineDisplaysTheProductPrice() {
		vendingMachine.insertCoin(Coin.QUARTER);
		Product selectedProduct = new Product("cola", 1.00);

		vendingMachine.buy(selectedProduct);

		assertEquals("1.00", vendingMachine.calculateDisplay());
	}
}
