package TrainSchedule;

import java.util.Vector;
//import java.time.LocalDateTime;
import java.sql.Timestamp;
//import java.time.ZoneOffset;
//import java.time.Instant;
//import java.util.Calendar;

/**
 * Created by DDRDmakar on 2/15/17.
 */

public class Train {
    private String trainName;
    private Timestamp time;
    private String destination;
    private Vector<String> intermediateStationStorage;

    public Train(String trainName, Timestamp time, String destination) {
        this.trainName = trainName;
        this.time = time;
        this.destination = destination;
        this.intermediateStationStorage = new Vector<>();
        this.intermediateStationStorage.add(destination);
    }

    public String getName() { return trainName; }

    public void addIntermediateStation(String intermediate) {
        if(intermediateStationStorage.indexOf(intermediate) == -1) intermediateStationStorage.add(intermediate);
    }
    public void deleteIntermediateStation(String intermediate) {
        int interStationIndex = intermediateStationStorage.indexOf(intermediate);
        if(interStationIndex != -1) intermediateStationStorage.remove(interStationIndex);
    }

    // Returns time before arrival in ms or -1
    public long timeTrainGoesTo(String target, Timestamp currentTime) {
        if( intermediateStationStorage.contains(target) && time.toLocalDateTime().isAfter(currentTime.toLocalDateTime()))
            return time.getTime() - currentTime.getTime();
        else return new Long(-1);
    }

    public Timestamp getTime() { return time; }

    public String toString() {
        return "TRAIN( " + trainName + ": goes to " + destination + " at time: " + time + ", " + Integer.toString(intermediateStationStorage.size()) + " inter-station(s) )";
    }

    public int hashCode() {
        int result = 7;
        result += ( trainName.hashCode() + time.hashCode() + destination.hashCode() + intermediateStationStorage.hashCode() ) * 13;
        return result;
    }

    public boolean equals( Train other ) {
        return this.trainName.equals(other.getName());
    }
}
