package sidmeyer.carmanager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sidmeyer.carmanager.model.ActionRequest;
import sidmeyer.carmanager.view.StatisticViewer;

import java.util.*;

/**
 * Created by Stas on 24.03.2017.
 */
public class Main {
	private static final Logger LOG = LogManager.getLogger("CarManager");
	private static int garageCapacity = 4;
	private static int waitingLineCapacity = 5;
	private static String filePath;


	public static void main(String[] args) {
		LOG.info("Start main.");
		garageCapacity = 4;
		waitingLineCapacity = 5;
		filePath = System.getProperty("user.dir") + "/src/main/resources/inputData.txt";
		try {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
					case "-f":
					case "--file":
						filePath = args[i + 1];
						break;
					case "-g":
						garageCapacity = Integer.parseInt(args[i + 1]);
						break;
					case "-wl":
						waitingLineCapacity = Integer.parseInt(args[i + 1]);
						break;
					case "-h":
					case "--help":
						printHelp();
						return;
				}
			}
		} catch (Exception e) {
			LOG.error("Wrong command line arguments: " + Arrays.toString(args) + ". Exiting.");
			printHelp();
			return;
		}
		LOG.info("Garage capacity=" + garageCapacity +
				", waiting line capacity: " + waitingLineCapacity +
				", file: " + filePath + ".");
		RequestCreator requestCreator = new RequestCreator(filePath);
		ArrayList<ActionRequest> actionRequests = requestCreator.getRequests();
		CarController carController = new CarController(garageCapacity, waitingLineCapacity);
		carController.processRequests(actionRequests);

		StatisticViewer.printStatistic(carController.getStatistic());
		StatisticViewer.printLocations(carController.getGarage(), carController.getWl());

		LOG.info("Finish main.");
	}

	private static void printHelp() {
		System.out.println("-f\tpath to input file\n" +
				"-g\tgarage capacity\n" +
				"-wl\twaiting line capacity");
	}
}
