package sidmeyer.carmanager.view;

import sidmeyer.carmanager.model.Car;
import sidmeyer.carmanager.model.CarStatistic;

import java.util.HashMap;

/**
 * Created by Stas on 27.03.2017.
 */
public class StatisticViewer {
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
}
