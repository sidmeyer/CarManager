package sidmeyer.carmanager.view;

import sidmeyer.carmanager.model.Car;
import sidmeyer.carmanager.model.CarStatistic;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Stas on 27.03.2017.
 */
public class StatisticViewer {
	/**
	 * Prints statistic for all car. How many times each car was on waiting line,
	 * in garage, on yard and has been cancelled.
	 * @param statistic - car statistic.
	 */
	public static void printStatistic(HashMap<Car, CarStatistic> statistic) {
		System.out.format("%10s%8s%8s%8s%8s\n", "Car", "WL", "Garage", "Yard", "Cancel");
		for (Car car : statistic.keySet()) {
			System.out.format("%10s%8s%8s%8s%8s\n", car.getName(),
					statistic.get(car).getWaitingLineCount(),
					statistic.get(car).getGarageCount(),
					statistic.get(car).getYardCount(),
					statistic.get(car).getCancelCount());
		}
	}

	/**
	 * Prints cars on waiting line and in garage.
	 * @param garage - cars in garage.
	 * @param wl - cars on waiting line.
	 */
	public static void printLocations(Deque<Car> garage, Deque<Car> wl) {
		System.out.format("WL:\t%s (%d cars)\n", wl, wl.size());
		System.out.format("G: \t%s (%d cars)\n", garage, garage.size());
	}
}
