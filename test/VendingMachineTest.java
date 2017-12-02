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
}
