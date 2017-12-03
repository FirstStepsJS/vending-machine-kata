import java.util.Objects;

public class Coin {

	private double weight;
	private double diameter;
	private double thickness;
	private int reeds;

	public Coin(double weight, double diameter, double thickness, int reeds) {
		this.weight = weight;
		this.diameter = diameter;
		this.thickness = thickness;
		this.reeds = reeds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Coin coin = (Coin) o;

		return Objects.equals(weight, coin.weight) && Objects.equals(diameter, coin.diameter)
				&& Objects.equals(thickness, coin.thickness) && Objects.equals(reeds, coin.reeds);
	}
}
