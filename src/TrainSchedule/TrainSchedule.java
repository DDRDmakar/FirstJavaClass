package TrainSchedule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.DataFormatException;

//=========================================

final public class TrainSchedule {

    private Collection<Train> trainStorage;

    public TrainSchedule() { trainStorage = new ArrayList<>(); }

    public void addTrain(String trainName, Timestamp departureTime, String destination) {
        if(trainStorage.contains(new Train(trainName))) throw new IllegalArgumentException();
        else trainStorage.add(new Train(trainName, departureTime, destination));
    }

    public void deleteTrain(String trainName) {
        if(trainStorage.contains(new Train(trainName))) trainStorage.remove(new Train(trainName));
        else throw new IllegalArgumentException();
    }

    public void addIntermediateStation(String trainName, String station) {
        for(Train e:trainStorage) {
            if(e.getName().equals(trainName)) e.addIntermediateStation(station);
        }
    }

    public void deleteIntermediateStation(String trainName, String station) {
        for(Train e:trainStorage) {
            if(e.getName().equals(trainName)) e.deleteIntermediateStation(station);
        }
    }

    public String findNextTrainTo(String stationName, Timestamp currentTime) throws DataFormatException {
        long shortest_time = -1;
        long timeBeforeArrival;
        String nextTrainName = "";

        for(Train e:trainStorage) {
            try {
                timeBeforeArrival = e.timeTrainGoesTo(stationName, currentTime);
            }
            catch(DataFormatException noway) { timeBeforeArrival = -1; }

            if(timeBeforeArrival != -1 && ( shortest_time > timeBeforeArrival || shortest_time == -1 )) {
                shortest_time = timeBeforeArrival;
                nextTrainName = e.getName();
            }
        }
        if(nextTrainName.isEmpty()) throw new DataFormatException();
        else return nextTrainName;
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

    public boolean equals( Object other ) { return other instanceof TrainSchedule && other.hashCode() == this.hashCode(); }
}
