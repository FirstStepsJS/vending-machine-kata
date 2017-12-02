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
}
