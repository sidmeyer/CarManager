package sidmeyer.carmanager.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;

/**
 * Created by stas on 29.05.17.
 */
public class Yard {
    private static final Logger LOG = LogManager.getLogger(Yard.class);

    private static final Stack<Car> container = new Stack<>();

    public void push(final Car car) {
        container.push(car);
    }

    public Car pop() {
        return container.pop();
    }

    public int size() {
        return container.size();
    }

    public String toString() {
        return container.toString();
    }
}
