package sidmeyer.carmanager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sidmeyer.carmanager.model.*;
import sidmeyer.carmanager.model.exceptions.FullGarageException;
import sidmeyer.carmanager.model.exceptions.FullWaitingLineException;

import java.util.*;

/**
 * Created by Stas on 25.03.2017.
 */
public class CarController {
	private static final Logger LOG = LogManager.getLogger("CarManager");
	private HashMap<Car, CarStatistic> statistic = new HashMap<Car, CarStatistic>();
//	private final int garageCapacity;
//	private final int waitingLineCapacity;
//	private Deque<Car> garage = new LinkedList<>();
//	private Deque<Car> wl = new LinkedList<>();
//	private Stack<Car> yard = new Stack<>();

	//test
	private final Yard yard = new Yard();
	private final Garage garage;
	private final WaitingLine wl;
	//test

	public CarController(final Garage garage, final WaitingLine wl) {
		this.garage = garage;
		this.wl = wl;
	}

//	public CarController(int garageCapacity, int waitingLCapacity) {
//		this.garageCapacity = garageCapacity;
//		this.waitingLineCapacity = waitingLCapacity;
//
//		//test
//
//		//test
//
//		LOG.info("CarController initialized. Garage capacity: " +
//				this.garageCapacity + ", waiting line capacity: " +
//				this.waitingLineCapacity + ".");
//	}

	/**
	 * Processes the requests/ Makes changes in garage and waiting line according to requests.
	 * Fills statistic.
	 * @param requests - list of requests.
	 */
//	@Deprecated
//	public void processRequestsOld(ArrayList<ActionRequest> requests) {
//		LOG.info("Request processing started. Got " + requests.size() + " requests.");
//
//		for (ActionRequest request : requests) {
//			Car car = request.getCar();
//			Action action = request.getAction();
//
//			if (statistic.get(car) == null) {
//				statistic.put(car, new CarStatistic());
//			}
//			LOG.debug("Processing request: " + request + ".");
//			LOG.trace("WL: " + wl.size() + ", G: " + garage.size() + ", Y: " + yard.size() + ".");
//			if (action.equals(Action.IN)) {
//				if (garage.contains(car)) {
//					LOG.debug("Car " + car + " already in garage.");
//				} else if (garage.size() < garageCapacity) {
//					garage.addLast(car);
//					LOG.debug("Car " + car + " entered garage.");
//					statistic.get(car).incGarageCount();
//				} else if (wl.contains(car)) {
//					LOG.debug("Car " + car + " remains on waiting line.");
//				} else if (wl.size() < waitingLineCapacity) {
//					wl.addLast(car);
//					LOG.debug("Car " + car + " joined waiting line.");
//					statistic.get(car).incWaitingLineCount();
//				} else {
//					LOG.debug("Car " + car + " cancelled.");
//					statistic.get(car).incCancelCount();
//				}
//			}
//
//			if (action.equals(Action.OUT)) {
//				if (wl.contains(car)) {
//					wl.remove(car);
//					LOG.debug("Car " + car + " went out from waiting line.");
//				} else if (!garage.contains(car) && !wl.contains(car)) {
//					LOG.debug("Car " + car + " already out.");
//				} else if (garage.size() > 0 && garage.peekFirst().equals(car)) {
//					garage.pollFirst();
//					LOG.debug("Car " + car + " went out from garage.");
//				} else if (garage.contains(car)) {
//					while (!garage.peekFirst().equals(car)) {
//						yard.push(garage.getFirst());
//						statistic.get(garage.peekFirst()).incYardCount();
//						garage.pollFirst();
//					}
//					garage.pollFirst();
//					LOG.debug("Car " + car + " went out from garage. Cars were on yard: " + yard.size() + ".");
//					while (yard.size() > 0) {
//						garage.addFirst(yard.pop());
//						statistic.get(garage.peekFirst()).incGarageCount();
//					}
//					if (wl.size() > 0) {
//						garage.addLast(wl.getFirst());
//						wl.removeFirst();
//						LOG.debug("Car " + garage.peekLast() + " entered garage from waiting line.");
//						statistic.get(garage.peekLast()).incGarageCount();
//					}
//				}
//			}
//			LOG.trace("WL: " + wl.size() + ", G: " + garage.size() + ", Y: " + yard.size() + ".");
//			LOG.trace("WL: " + wl + ", G: " + garage + ", Y: " + yard + ".");
//		}
//		LOG.trace(statistic);
//		LOG.info("End processing requests.");
//	}

	public void processRequests(final List<ActionRequest> requests) {
		LOG.info("Request processing started. Got %d requests.", requests.size());

		for (ActionRequest request : requests) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Processing request: %s.", request);
			}

			Car car = request.getCar();
			Action action = request.getAction();

			if (action.equals(Action.IN)) {
				switch (getLocation(car)) {
					case OUT:
						try {
							garage.moveIn(car);
							LOG.debug("Car %s entered garage.", car);
						} catch (FullGarageException fge) {
							try {
								wl.moveIn(car);
								LOG.debug("Car %s joined waiting line.", car);
							} catch (FullWaitingLineException fwle) {
								LOG.debug("Car %s cancelled.", car);
							}
						}
						break;
					case GARAGE:
						LOG.debug("Car %s already in garage.", car);
						break;
					case WAITING_LINE:
						LOG.debug("Car %s cancelled.", car);
						break;
				}
			}


			if (action.equals(Action.OUT)) {
				switch (getLocation(car)) {
					case OUT:
						LOG.debug("Car %s already out.", car);
						break;
					case WAITING_LINE:
						wl.moveOut(car);
						LOG.debug("Car %s moved out from %s.", car, Location.WAITING_LINE);
						break;
					case GARAGE:
						garage.moveOut(car);
						LOG.debug("Car %s moved out from %s.", car, Location.GARAGE);
						wl.moveToGarage();
						break;
				}
			}


		}

	}

	public HashMap<Car, CarStatistic> getStatistic() {
		return statistic;
	}

//	public Deque<Car> getGarage() {
//		return garage;
//	}

	//test
	public Deque<Car> getGarage() {
		return garage.getGarage();
	}

//	public Deque<Car> getWl() {
//		return wl;
//	}

	public Deque<Car> getWl() {
		return wl.getWL();
	}

	private Location getLocation(Car car) {
		if (garage.contains(car)) {
			return Location.GARAGE;
		}

		if (wl.contains(car)) {
			return Location.WAITING_LINE;
		}

		return Location.OUT;
	}
}
