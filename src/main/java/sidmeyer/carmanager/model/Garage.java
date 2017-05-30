package sidmeyer.carmanager.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sidmeyer.carmanager.controllers.Observable;
import sidmeyer.carmanager.controllers.Observer;
import sidmeyer.carmanager.model.exceptions.FullGarageException;

import java.util.*;

/**
 * Created by stas on 29.05.17.
 */
public class Garage implements Observable {
    private static final Logger LOG = LogManager.getLogger(Garage.class);

    private List<Observer> observers = new ArrayList<>();

    private final int capacity;
    private final Deque<Car> container = new LinkedList<>();

    private final Yard yard;

    public Garage(final int capacity, final Yard yard) {
        this.capacity = capacity;
        this.yard = yard;
    }

    public boolean moveIn(final Car car) throws FullGarageException {

        if (container.contains(car)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Car %s already in garage.", car);
            }
            return false;
        }

        if (container.size() < capacity) {
            container.addFirst(car);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Car %s entered garage.", car);
            }
            notifyObservers(car, Location.GARAGE);
            return true;
        } else {
            throw new FullGarageException();
        }

    }

    public boolean moveOut(final Car car) {

        if (container.contains(car)) {

            while (null != container.peekFirst() && !container.peekFirst().equals(car)) {
                Car tempCar = container.pollFirst();
                yard.push(tempCar);
                notifyObservers(tempCar, Location.YARD);
            }

            container.pollFirst();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Car %s left garage. Cars on yard: %s", car, yard);
            }
            notifyObservers(car, Location.OUT);

            while (yard.size() > 0) {
                Car tempCar = yard.pop();
                container.addFirst(tempCar);
                notifyObservers(tempCar, Location.GARAGE);
            }

            return true;
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Car %s already out of garage.", car);
            }
            return false;
        }
    }

    public boolean contains(final Car car) {
        return container.contains(car);
    }

    public boolean hasFreeSpace() {
        return container.size() < capacity;
    }

    //test
    public Deque<Car> getGarage() {
        return container;
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Car car, Location location) {
        for (Observer observer : observers) {
            observer.handleEvent(car, location);
        }
    }
}
