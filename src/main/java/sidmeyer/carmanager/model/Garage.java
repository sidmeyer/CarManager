package sidmeyer.carmanager.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sidmeyer.carmanager.model.exceptions.FullGarageException;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by stas on 29.05.17.
 */
public class Garage {
    private static final Logger LOG = LogManager.getLogger(Garage.class);

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
            return true;
        } else {
            throw new FullGarageException();
        }

    }

    public boolean moveOut(final Car car) {

        if (!container.contains(car)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Car %s already out of garage.", car);
                return false;
            }
        }

        while (!container.peekFirst().equals(car)) {
            yard.push(container.pollFirst());
        }
        container.pollFirst();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Car %s left garage. Cars on yard: %s", car, yard);
        }
        while (yard.size() > 0) {
            container.addFirst(yard.pop());
        }

        return true;
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
}
