package sidmeyer.carmanager.controllers;

import sidmeyer.carmanager.model.Car;
import sidmeyer.carmanager.model.Location;

/**
 * Created by stas on 30.05.17.
 */
public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(Car car, Location location);
}
