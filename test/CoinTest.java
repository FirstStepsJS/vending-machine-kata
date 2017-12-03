import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CoinTest {
	@Test
	public void equals_WhenComparingToSameCoinObject_ReturnsTrue() {
		Coin coin = new Coin(1, 1, 1, 1);
		Coin coin2 = coin;

		assertTrue(coin.equals(coin2));
	}

	@Test
	public void equals_WhenComparingToNullCoinObject_ReturnsFalse() {
		Coin coin = new Coin(1, 1, 1, 1);
		Coin coin2 = null;

		assertFalse(coin.equals(coin2));
	}

	@Test
	public void equals_WhenComparingToAnotherObjectType_ReturnsFalse() {
		Coin coin = new Coin(1, 1, 1, 1);
		String coin2 = "I want to be a coin";

		assertFalse(coin.equals(coin2));
	}

	@Test
	public void equals_WhenComparingToACoinWithSameProperties_ReturnsTrue() {
		Coin coin = new Coin(1, 1, 1, 1);
		Coin coin2 = new Coin(1, 1, 1, 1);

		assertTrue(coin.equals(coin2));
	}

	@Test
	public void equals_WhenComparingToACoinWithDifferentProperties_ReturnsFalse() {
		Coin coin = new Coin(1, 1, 1, 1);
		Coin coin2 = new Coin(2, 1, 1, 1);

		assertFalse(coin.equals(coin2));
	}
}