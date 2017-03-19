package TrainSchedule;

import java.sql.Timestamp;
import java.util.*;

//=========================================

/**
 * This class contains schedule for the railway station.
 * ( list of trains and their properties ).
 * It provides methods to add/delete whole trains, edit
 * route for each train and to find, which train goes to the
 * given station next, if it's possible.
 */
final public class TrainSchedule {

    //===
    //=== DATA
    //===

    private Collection<Train> trainStorage;

    //===
    //=== CONSTRUCTORS
    //===

    /**
     * Basic constructor - creates empty schedule.
     */
    public TrainSchedule() { trainStorage = new ArrayList<>(); }

    /**
     * Constructs schedule from list of Train objects
     * @param currentStorage - list of trains, which should be in the schedule.
     * @throws NullPointerException if currentStorage is null
     */
    public TrainSchedule(ArrayList<Train> currentStorage) {
        if(currentStorage == null) throw new NullPointerException("Train storage pointer in constructor is null");
        for(Train e : currentStorage) {
            String tempName = e.getName();
            String tempDestination = e.getDestination();
            Date tempTime = e.getTime();
            Collection<String> tempStationStorage = e.getInterStationStorage();
            tempStationStorage.remove(tempDestination);
            this.trainStorage.add( new Train(tempName, tempTime, tempDestination, tempStationStorage) );
        }
    }

    //===
    //=== OTHER FUNCTIONS
    //===

    /**
     * Checks, if train exists
     * @param tempTrain - train, which should be in schedule
     * @return true - if train exists, false if not
     */
    public boolean contains(Train tempTrain) {
        return trainStorage.contains(tempTrain);
    }
    /**
     * Checks, if train exists
     * @param tempTrainName - train name, which should be in schedule
     * @return true - if train exists, false if not
     */
    public boolean contains(String tempTrainName) {
        for(Train e:trainStorage) if(e.getName().equals(tempTrainName)) return true;
        return false;
    }

    /**
     * Adds train to the schedule
     * @param currentTrain - train to add
     * @throws NullPointerException if currentTrain is null.
     */
    public void addTrain(Train currentTrain) {
        if(currentTrain == null) throw new NullPointerException("Train cannot be null");
        trainStorage.add(new Train(currentTrain));
    }

    /**
     * Adds train to the schedule
     * @param trainName - name of the train
     * @param arrivalTime - time train arrives to the station
     * @param destination - destination station
     * @throws IllegalArgumentException if such train already exists.
     */
    public void addTrain(String trainName, Date arrivalTime, String destination) {
        if(contains(new Train(trainName, arrivalTime, destination))) throw new IllegalArgumentException("Train already exists");
        trainStorage.add(new Train(trainName, arrivalTime, destination));
    }
    /**
     * The same, but with list of intermediate stations for the train.
     */
    public void addTrain(String trainName, Timestamp departureTime, String destination, Collection<String> interStations ) {
        if(contains(new Train(trainName, departureTime, destination))) throw new IllegalArgumentException("Train already exists");
        trainStorage.add(new Train(trainName, departureTime, destination, interStations));
    }

    /**
     * Deletes train from this schedule
     * @param trainName - name of train to delete
     * @throws NullPointerException if trainName is null
     * @throws IllegalArgumentException if such train does not exist.
     */
    public void deleteTrain(String trainName) {
        if(trainName == null) throw new NullPointerException("Train name cannot be null");
        if(!contains(trainName)) throw new IllegalArgumentException("Train does not exist");

        for(Train e : trainStorage) if(e.getName().equals(trainName)) {
            trainStorage.remove(e);
            return;
        }
    }

    /**
     * Adds intermediate station for the given train
     * @param trainName - name of the train to add inter-station
     * @param station - station to add
     * @throws NullPointerException if train name or station name is null
     */
    public void addIntermediateStation(String trainName, String station) {
        if(trainName == null) throw new NullPointerException("Train name cannot be null");
        if(station == null) throw new NullPointerException("Station name cannot be null");

        for(Train e : trainStorage) {
            if(e.getName().equals(trainName)) {
                e.addIntermediateStation(station);
                return;
            }
        }
    }

    /**
     * Deletes station from train's route
     * @param trainName - name of train to edit
     * @param station - name of station to delete
     * @throws NullPointerException if any of arguments is null.
     */
    public void deleteIntermediateStation(String trainName, String station) {
        if(trainName == null) throw new NullPointerException("Train name cannot be null");
        if(station == null) throw new NullPointerException("Station name cannot be null");

        for(Train e:trainStorage) {
            if(e.getName().equals(trainName)) e.deleteIntermediateStation(station);
        }
    }

    /**
     * Finds, which train goes to the given station next, if it's possible.
     * @param stationName - name of station where train should go.
     * @param currentTime - current date and time.
     * @return name of train, which goes to the given station in
     * shortest time, or empty string, if it's impossible.
     */
    public String findNextTrainTo(String stationName, Timestamp currentTime) {
        long shortestTime = -1;
        long timeBeforeArrival;
        String nextTrainName = "";

        for(Train e:trainStorage) {
            if(e.contains(stationName)) {
                timeBeforeArrival = e.timeTrainGoesTo(stationName, currentTime);
                if(timeBeforeArrival >= 0 && (shortestTime > timeBeforeArrival || shortestTime == -1)) {
                    shortestTime = timeBeforeArrival;
                    nextTrainName = e.getName();
                }
            }
        }
        return nextTrainName;
    }

    //===
    //=== STANDARD FUNCTIONS
    //===

    public String toString() {
        String result;
        result =  "TRAIN_STORAGE( " + Integer.toString(trainStorage.size()) + " trains )";
        if(trainStorage.isEmpty()) result += "\n";
        else {
            result += " {\n";
            for(Train e : trainStorage) result += "    " + e.toString() + "\n";
            result += "}\n";
        }
        return result;
    }

    public int hashCode() { return trainStorage.hashCode() * 23; }

    public boolean equals( Object other ) {
        if(other == this) return true;
        if(other == null || other.getClass() != this.getClass()) return false;
        TrainSchedule that = (TrainSchedule) other;
        for(Train e : trainStorage) if(!that.contains(e.getName())) return false;
        return true;
    }
}
