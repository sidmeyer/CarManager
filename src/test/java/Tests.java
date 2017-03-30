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
Group 1+2:
1 H
1 A
0 G
0 D
Group 1+2+3:
1 I
1 J
1 K
1 L
1 I
1 K
1 L
0 J

*/
	@Test
	public void testStatistic01()throws Exception {
		//testInputData01.txt
		/*
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

		RequestCreator requestCreator = new RequestCreator(System.getProperty("user.dir") + "/src/test/resources/testInputData01.txt");
		ArrayList<ActionRequest> actionRequests = requestCreator.getRequests();
		CarController carController = new CarController(4, 5);
		carController.processRequests(actionRequests);

		HashMap<Car,CarStatistic> actual = carController.getStatistic();

		assertEquals("Statistic", expected, actual);
	}

	@Test
	public void testStatistic02()throws Exception {
		//testInputData02.txt
		/*
Group 2 stats:
Car	WL	G	Y	C
A	1	0	0	0
B	0	2	1	0
C	0	2	1	0
D	0	1	0	0
E	0	1	0	0
F	1	1	0	0
G	1	0	0	0
H	1	0	0	0
		 */
		HashMap<Car,CarStatistic> expected = new HashMap<Car,CarStatistic>() {{
			put(new Car("A"), new CarStatistic(1, 0, 0, 0));
			put(new Car("B"), new CarStatistic(0, 2, 1, 0));
			put(new Car("C"), new CarStatistic(0, 2, 1, 0));
			put(new Car("D"), new CarStatistic(0, 1, 0, 0));
			put(new Car("E"), new CarStatistic(0, 1, 0, 0));
			put(new Car("F"), new CarStatistic(1, 1, 0, 0));
			put(new Car("G"), new CarStatistic(1, 0, 0, 0));
			put(new Car("H"), new CarStatistic(1, 0, 0, 0));
		}};

		RequestCreator requestCreator = new RequestCreator(System.getProperty("user.dir") + "/src/test/resources/testInputData02.txt");
		ArrayList<ActionRequest> actionRequests = requestCreator.getRequests();
		CarController carController = new CarController(4, 5);
		carController.processRequests(actionRequests);

		HashMap<Car,CarStatistic> actual = carController.getStatistic();

		assertEquals("Statistic", expected, actual);
	}

	@Test
	public void testStatistic03()throws Exception {
		//testInputData03.txt
		/*
Group 3 stats:
Car	WL	G	Y	C
A	1	0	0	0
B	0	2	1	0
C	0	2	1	0
D	0	1	0	0
E	0	1	0	0
F	1	1	0	0
G	1	0	0	0
H	1	0	0	0
I	1	0	0	0
J	1	0	0	0
K	1	0	0	0
L	0	0	0	2
		 */
		HashMap<Car,CarStatistic> expected = new HashMap<Car,CarStatistic>() {{
			put(new Car("A"), new CarStatistic(1, 0, 0, 0));
			put(new Car("B"), new CarStatistic(0, 2, 1, 0));
			put(new Car("C"), new CarStatistic(0, 2, 1, 0));
			put(new Car("D"), new CarStatistic(0, 1, 0, 0));
			put(new Car("E"), new CarStatistic(0, 1, 0, 0));
			put(new Car("F"), new CarStatistic(1, 1, 0, 0));
			put(new Car("G"), new CarStatistic(1, 0, 0, 0));
			put(new Car("H"), new CarStatistic(1, 0, 0, 0));
			put(new Car("I"), new CarStatistic(1, 0, 0, 0));
			put(new Car("J"), new CarStatistic(1, 0, 0, 0));
			put(new Car("K"), new CarStatistic(1, 0, 0, 0));
			put(new Car("L"), new CarStatistic(0, 0, 0, 2));
		}};

		RequestCreator requestCreator = new RequestCreator(System.getProperty("user.dir") + "/src/test/resources/testInputData03.txt");
		ArrayList<ActionRequest> actionRequests = requestCreator.getRequests();
		CarController carController = new CarController(4, 5);
		carController.processRequests(actionRequests);

		HashMap<Car,CarStatistic> actual = carController.getStatistic();

		assertEquals("Statistic", expected, actual);
	}

	@Test
	public void testStatistic04()throws Exception {
		//testInputData04.txt
		/*

Group 4:
1 A
1 B
1 C
1 D
1 E
1 F
1 G
1 H
1 H
1 A
1 G
1 D
1 I
1 J
1 K
1 L
1 I
1 K
1 L
1 J

Group 3 stats:
Car	WL	G	Y	C
A	0	1	0	0
B	0	1	0	0
C	0	1	0	0
D	0	1	0	0
E	1	0	0	0
F	1	0	0	0
G	1	0	0	0
H	1	0	0	0
I	1	0	0	0
J	0	0	0	2
K	0	0	0	2
L	0	0	0	2
		 */
		HashMap<Car,CarStatistic> expected = new HashMap<Car,CarStatistic>() {{
			put(new Car("A"), new CarStatistic(0, 1, 0, 0));
			put(new Car("B"), new CarStatistic(0, 1, 0, 0));
			put(new Car("C"), new CarStatistic(0, 1, 0, 0));
			put(new Car("D"), new CarStatistic(0, 1, 0, 0));
			put(new Car("E"), new CarStatistic(1, 0, 0, 0));
			put(new Car("F"), new CarStatistic(1, 0, 0, 0));
			put(new Car("G"), new CarStatistic(1, 0, 0, 0));
			put(new Car("H"), new CarStatistic(1, 0, 0, 0));
			put(new Car("I"), new CarStatistic(1, 0, 0, 0));
			put(new Car("J"), new CarStatistic(0, 0, 0, 2));
			put(new Car("K"), new CarStatistic(0, 0, 0, 2));
			put(new Car("L"), new CarStatistic(0, 0, 0, 2));
		}};

		RequestCreator requestCreator = new RequestCreator(System.getProperty("user.dir") + "/src/test/resources/testInputData04.txt");
		ArrayList<ActionRequest> actionRequests = requestCreator.getRequests();
		CarController carController = new CarController(4, 5);
		carController.processRequests(actionRequests);

		HashMap<Car,CarStatistic> actual = carController.getStatistic();

		assertEquals("Statistic", expected, actual);
	}

}
