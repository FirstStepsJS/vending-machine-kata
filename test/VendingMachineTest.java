import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VendingMachineTest {

	@Test
	public void whenThereAreNoCoinsTheMachineDisplaysInsertCoin() {
		VendingMachine vendingMachine = new VendingMachine();
		assertEquals("INSERT COIN", vendingMachine.calculateDisplay());
	}
}
