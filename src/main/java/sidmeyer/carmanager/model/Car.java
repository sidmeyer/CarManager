package sidmeyer.carmanager.model;

/**
 * Created by Stas on 23.03.2017.
 */
public class Car {
	private final String name;

	public Car(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Car car = (Car) o;

		return name != null ? name.equals(car.name) : car.name == null;
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	public String toString() {
		return name;
	}
}
