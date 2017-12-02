import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VendingMachineTest {

	@Test
	public void whenThereAreNoCoinsTheMachineDisplaysInsertCoin() {
		VendingMachine vendingMachine = new VendingMachine();
		assertEquals("INSERT COIN", vendingMachine.calculateDisplay());
	}

	@Test
	public void whenAValidCoinIsInsertedTheMachineDisplaysTheAmountOfTheCoin() {
		VendingMachine vendingMachine = new VendingMachine();
		vendingMachine.insertCoin(Coin.NICKEL);
		assertEquals("0.05", vendingMachine.calculateDisplay());
	}
}
