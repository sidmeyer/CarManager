package sidmeyer.carmanager.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sidmeyer.carmanager.model.Action;
import sidmeyer.carmanager.model.ActionRequest;
import sidmeyer.carmanager.model.Car;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class includes method which reads file and makes requests list.
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

        try (BufferedReader fin = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {

            String line;
            int lineIndex = 0;
            while ((line = fin.readLine()) != null) {
                lineIndex++;
                LOG.debug("Processing line " + lineIndex + ": \"" + line + "\".");
                if(line.length() < 3) {
                    LOG.error("Incorrect data in line " + lineIndex + ". Line skipped.");
                    continue;
                }
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
