package sidmeyer.carmanager.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sidmeyer.carmanager.model.exceptions.FullWaitingLineException;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by stas on 29.05.17.
 */
public class WaitingLine {
    private static final Logger LOG = LogManager.getLogger(WaitingLine.class);

    private final int capacity;
    private final Deque<Car> container = new LinkedList<>();

    public WaitingLine(int capacity) {
        this.capacity = capacity;
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
            return true;
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Car %s already out of waiting line", car);
            }
            return false;
        }

    }

    public Car poll() {
        if (!container.isEmpty()) {
            return container.poll();
        }
        return null;
    }

    public Deque<Car> getWL() {
        return container;
    }
}
