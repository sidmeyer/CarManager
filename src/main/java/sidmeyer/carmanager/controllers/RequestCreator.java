package sidmeyer.carmanager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sidmeyer.carmanager.model.Action;
import sidmeyer.carmanager.model.ActionRequest;
import sidmeyer.carmanager.model.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Stas on 24.03.2017.
 */
public class RequestCreator {

    private static final Logger LOG = LogManager.getLogger("CarManager");
    private String filePath;
    private ArrayList<ActionRequest> requests = new ArrayList<ActionRequest>();
    private HashMap<String, Car> cars = new HashMap<String, Car>();

    public RequestCreator(String filePath) {
        this.filePath = filePath;
        requests = readRequests();
    }

    private ArrayList<ActionRequest> readRequests() {

        LOG.info("Start reading requests from file \"" + filePath + "\".");

        ArrayList<ActionRequest> tempRequests = new ArrayList<ActionRequest>();

        File f = new File(filePath);
        try {
            BufferedReader fin = new BufferedReader(new FileReader(f));
            String line;
            int lineIndex = 0;
            while ((line = fin.readLine()) != null) {
                lineIndex++;
                LOG.debug("Processing line " + lineIndex + ": \"" + line + "\".");
                String carName = line.substring(2, line.length());
                if (!cars.containsKey(carName)) {
                    cars.put(carName, new Car(carName));
                }
                Action action;
                if (line.charAt(0) == '0') {
                    action = Action.OUT;
                } else if (line.charAt(0) == '1') {
                    action = Action.IN;
                } else {
                    LOG.error("Incorrect data in line " + lineIndex + ". Line skipped.");
                    continue;
                }

                tempRequests.add(new ActionRequest(cars.get(carName), action));
                LOG.debug("Added request: " + tempRequests.get(tempRequests.size() - 1));
            }
            LOG.info("Finish reading file. " + tempRequests.size() + " requests added.");
            fin.close();
        } catch (FileNotFoundException e) {
            LOG.error("Input file not found. " + e.getMessage());
        } catch (IOException e) {
            LOG.error("IOException" + e.getMessage());
        }
        return tempRequests;
    }

    public ArrayList<ActionRequest> getRequests() {
        return requests;
    }
}
