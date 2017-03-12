package TrainSchedule;

import java.sql.Timestamp;
import java.util.*;

//=========================================

final public class TrainSchedule {

    private Collection<Train> trainStorage;

    public TrainSchedule() { trainStorage = new ArrayList<>(); }
    public TrainSchedule(ArrayList<Train> currentStorage) { trainStorage = currentStorage; }

    public boolean contains(Train tempTrain) {
        return trainStorage.contains(tempTrain);
    }
    public boolean contains(String tempTrainName) {
        for(Train e:trainStorage) if(e.getName().equals(tempTrainName)) return true;
        return false;
    }

    public void addTrain(Train currentTrain) {
        if(currentTrain == null) throw new NullPointerException("Train cannot be null");
        trainStorage.add(currentTrain);
    }

    public void addTrain(String trainName, Timestamp departureTime, String destination) {
        if(contains(new Train(trainName, departureTime, destination))) throw new IllegalArgumentException("Train already exists");
        trainStorage.add(new Train(trainName, departureTime, destination));
    }
    public void addTrain(String trainName, Timestamp departureTime, String destination, Collection<String> interStations ) {
        if(contains(new Train(trainName, departureTime, destination))) throw new IllegalArgumentException("Train already exists");
        trainStorage.add(new Train(trainName, departureTime, destination, interStations));
    }

    public void deleteTrain(String trainName) {
        if(trainName == null) throw new NullPointerException("Train name cannot be null");
        if(!contains(trainName)) throw new IllegalArgumentException("Train does not exist");

        for(Train e:trainStorage) if(e.getName().equals(trainName)) {
            trainStorage.remove(e);
            return;
        }
    }

    public void addIntermediateStation(String trainName, String station) {
        if(trainName == null) throw new NullPointerException("Train name cannot be null");
        if(station == null) throw new NullPointerException("Station name cannot be null");

        for(Train e:trainStorage) {
            if(e.getName().equals(trainName)) e.addIntermediateStation(station);
        }
    }

    public void deleteIntermediateStation(String trainName, String station) {
        if(trainName == null) throw new NullPointerException("Train name cannot be null");
        if(station == null) throw new NullPointerException("Station name cannot be null");

        for(Train e:trainStorage) {
            if(e.getName().equals(trainName)) e.deleteIntermediateStation(station);
        }
    }

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
