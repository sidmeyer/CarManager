import org.junit.Test;
import sidmeyer.carmanager.controllers.CarController;
import sidmeyer.carmanager.controllers.RequestCreator;
import sidmeyer.carmanager.model.ActionRequest;
import sidmeyer.carmanager.model.Car;
import sidmeyer.carmanager.model.CarStatistic;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Stas on 23.03.2017.
 */
public class Tests {

	/*
	model statistic
	equals
	HashMap<String,CarStatistic> expected;

WL.capacity = 5, G.capacity = 4:

Group 1:
0 A
1 B
1 C
1 D
1 E
1 F
1 G
0 H

Group 1 stats:
Car	WL	G	Y	C
A	0	0	0	0
B	0	1	0	0
C	0	1	0	0
D	0	1	0	0
E	0	1	0	0
F	1	0	0	0
G	1	0	0	0
H	0	0	0	0

*/
	@Test
	public void testStatistic()throws Exception {
		HashMap<Car,CarStatistic> expected = new HashMap<Car,CarStatistic>() {{
			put(new Car("A"), new CarStatistic(0, 0, 0, 0));
			put(new Car("B"), new CarStatistic(0, 1, 0, 0));
			put(new Car("C"), new CarStatistic(0, 1, 0, 0));
			put(new Car("D"), new CarStatistic(0, 1, 0, 0));
			put(new Car("E"), new CarStatistic(0, 1, 0, 0));
			put(new Car("F"), new CarStatistic(1, 0, 0, 0));
			put(new Car("G"), new CarStatistic(1, 0, 0, 0));
			put(new Car("H"), new CarStatistic(0, 0, 0, 0));
		}};

		RequestCreator requestCreator = new RequestCreator("");
		ArrayList<ActionRequest> actionRequests = requestCreator.getRequests();
		CarController carController = new CarController(4, 5);
		carController.processRequests(actionRequests);

		HashMap<Car,CarStatistic> actual = carController.getStatistic();

		assertEquals("Statistic", expected, actual);
	}

}
