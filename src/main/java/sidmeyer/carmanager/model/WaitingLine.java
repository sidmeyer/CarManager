package sidmeyer.carmanager.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sidmeyer.carmanager.controllers.Observable;
import sidmeyer.carmanager.controllers.Observer;
import sidmeyer.carmanager.model.exceptions.FullGarageException;
import sidmeyer.carmanager.model.exceptions.FullWaitingLineException;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stas on 29.05.17.
 */
public class WaitingLine implements Observable {
    private List<Observer> observers = new ArrayList<>();

    private static final Logger LOG = LogManager.getLogger(WaitingLine.class);

    private final int capacity;
    private final Deque<Car> container = new LinkedList<>();
    private final Garage garage;

    public WaitingLine(final int capacity, final Garage garage) {
        this.capacity = capacity;
        this.garage = garage;
    }

    public boolean moveIn(Car car) throws FullWaitingLineException {

        if (container.contains(car)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Car %s already on waiting line.", car);
            }

            return false;
        }

        if (container.size() < capacity) {
            container.addLast(car);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Car %s entered waiting line.", car);
            }
            notifyObservers(car, Location.GARAGE);
            return true;
        } else {
            throw new FullWaitingLineException();
        }
    }

    public boolean moveOut(Car car) {

        if (container.contains(car)) {
            container.remove(car);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Car %s left waiting line", car);
            }
            notifyObservers(car, Location.OUT);
            return true;
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Car %s already out of waiting line", car);
            }
            return false;
        }

    }

    public boolean moveToGarage() {
        if (container.isEmpty()) {
            return false;
        }
        Car car = container.removeFirst();
        try {
            garage.moveIn(car);
            LOG.debug("Car %s moved from %s to %s.", car, Location.WAITING_LINE, Location.GARAGE);
            //notifyObservers(car, Location.GARAGE);
        } catch (FullGarageException fge) {
            container.addFirst(car);
            return false;
        }
        return true;
    }

    public Deque<Car> getWL() {
        return container;
    }

    public boolean contains(Car car) {
        return container.contains(car);
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
