package TrainSchedule;

import java.sql.Timestamp;
import java.util.Vector;
//import java.util.TreeMap;
//import java.util.SortedMap;
//import java.util.Date;

/**
 * Created by DDRDmakar on 2/15/17.
 */
public class TrainSchedule {

    private Vector<Train> trainStorage;

    public TrainSchedule() { trainStorage = new Vector<Train>(); }

    private int findTrainIndex(String trainName) {

        for(int i = 0; i < trainStorage.size(); ++i) {
            if(trainStorage.get(i).getName().equals(trainName)) {
                return i;
            }
        }
        return -1;
    }

    public void addTrain(String trainName, Timestamp departureTime, String destination) {
        if(findTrainIndex(trainName) == -1) trainStorage.add(new Train(trainName, departureTime, destination));
    }

    public void deleteTrain(String trainName) {
        int trainIndex = -1;
        for(int i = 0; i < trainStorage.size(); ++i) {
            if(trainStorage.get(i).getName().equals(trainName)) {
                trainStorage.remove(i);
                break;
            }
        }
    }

    public void addIntermediateStation(String trainName, String station) {
        int trainIndex = findTrainIndex(trainName);
        if(trainIndex != -1) trainStorage.get(trainIndex).addIntermediateStation(station);
    }

    public void deleteIntermediateStation(String trainName, String station) {
        int trainIndex = findTrainIndex(trainName);
        if(trainIndex != -1) trainStorage.get(trainIndex).deleteIntermediateStation(station);
    }

    public String find_next_train_to(String stationName, Timestamp currentTime) {
        long shortest_time = -1;
        String nextTrainName = "";

        for(Train e:trainStorage) {
            long timeBeforeArrival = e.timeTrainGoesTo(stationName, currentTime);
            if(timeBeforeArrival != -1 && ( shortest_time > timeBeforeArrival || shortest_time == -1 )) {
                shortest_time = timeBeforeArrival;
                nextTrainName = e.getName();
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

    public int hashCode() {
        return trainStorage.hashCode() * 23;
    }
}
