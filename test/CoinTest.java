import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CoinTest {
	@Test
	public void equals_SameCoinObject_ReturnsTrue() {
		Coin coin = new Coin(1, 1, 1, 1);
		Coin coin2 = coin;

		assertTrue(coin.equals(coin2));
	}

}
