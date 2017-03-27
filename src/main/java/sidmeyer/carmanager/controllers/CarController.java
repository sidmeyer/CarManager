package sidmeyer.carmanager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sidmeyer.carmanager.model.Action;
import sidmeyer.carmanager.model.ActionRequest;
import sidmeyer.carmanager.model.Car;
import sidmeyer.carmanager.model.CarStatistic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Stas on 25.03.2017.
 */
public class CarController {
	private static final Logger LOG = LogManager.getLogger("CarManager");
	private HashMap<Car, CarStatistic> statistic = new HashMap<Car, CarStatistic>();
	private final int garageCapacity;
	private final int waitingLineCapacity;

	public CarController(int garageCapacity, int waitingLCapacity) {
		this.garageCapacity = garageCapacity;
		this.waitingLineCapacity = waitingLCapacity;
		LOG.info("CarController initialized. Garage capacity: " +
				this.garageCapacity + ", waiting line capacity: " +
				this.waitingLineCapacity + ".");
	}

	public void processRequests(ArrayList<ActionRequest> requests) {
		LOG.info("Request processing started. Got " + requests.size() + " requests.");
		ArrayDeque<Car> garage = new ArrayDeque<Car>();
		LinkedList<Car> wl = new LinkedList<Car>();
		//Stack<Car> yard = new Stack<Car>();
		ArrayDeque<Car> yard = new ArrayDeque<Car>();



		for (ActionRequest request : requests) {
			Car car = request.getCar();
			Action action = request.getAction();

			if(statistic.get(car) == null) {
				statistic.put(car, new CarStatistic());
			}
			LOG.debug("Processing request: " + request + ".");
			LOG.trace("WL: " + wl.size() + ", G: " + garage.size() + ", Y: " + yard.size() + ".");
			if(action.equals(Action.IN)) {
				if(garage.contains(car)) {
					LOG.debug("Car " + car + " already in garage.");
				} else if (garage.size() < garageCapacity) {
					garage.addLast(car);
					LOG.debug("Car " + car + " entered garage.");
					statistic.get(car).incGarageCount();
				} else if(wl.contains(car)) {
					LOG.debug("Car " + car + " remains on waiting line.");
				} else if(wl.size() < waitingLineCapacity) {
					wl.addLast(car);
					LOG.debug("Car " + car + " joined waiting line.");
					statistic.get(car).incWaitingLineCount();
				} else {
					LOG.debug("Car " + car + " cancelled.");
					statistic.get(car).incCancelCount();
				}
			}

			if(action.equals(Action.OUT)) {
				if (wl.contains(car)) {
					wl.remove(car);
					LOG.debug("Car " + car + " went out from waiting line.");
				} else if(!garage.contains(car) && !wl.contains(car)){
					LOG.debug("Car " + car + " already out.");
				} else if (garage.size() > 0 && garage.getFirst().equals(car)) {
					garage.removeFirst();
					LOG.debug("Car " + car + " went out from garage.");
				} else if (garage.contains(car)) {
					while(!garage.getFirst().equals(car)) {
						yard.addFirst(garage.getFirst());
						statistic.get(garage.getFirst()).incYardCount();
						garage.removeFirst();
					}
					garage.removeFirst();
					LOG.debug("Car " + car + " went out from garage. Cars were on yard: " + yard.size() + ".");
					while(yard.size() > 0) {
						garage.addFirst(yard.getFirst());
						statistic.get(yard.getFirst()).incGarageCount();
						yard.removeFirst();
					}
					if(wl.size() > 0) {
						garage.addLast(wl.getFirst());
						wl.removeFirst();
						LOG.debug("Car " + garage.getLast() + " entered garage from waiting line.");
						statistic.get(garage.getLast()).incGarageCount();
					}
				}
			}
			LOG.trace("WL: " + wl.size() + ", G: " + garage.size() + ", Y: " + yard.size() + ".");
			LOG.trace("WL: " + wl + ", G: " + garage + ", Y: " + yard + ".");
		}
		LOG.trace(statistic);
		LOG.info("End processing requests.");
	}

	public HashMap<Car, CarStatistic> getStatistic() {
		return statistic;
	}
}
