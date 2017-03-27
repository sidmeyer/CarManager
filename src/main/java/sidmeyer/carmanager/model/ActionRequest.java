package sidmeyer.carmanager.model;

/**
 * Created by Stas on 24.03.2017.
 */
public class ActionRequest {
	private final Car car;
	private final Action action;

	public ActionRequest(Car car, Action action) {
		this.car = car;
		this.action = action;
	}

	public Car getCar() {
		return car;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public String toString() {
		return "ActionRequest{" +
				"car=" + car +
				", action=" + action +
				'}';
	}
}
