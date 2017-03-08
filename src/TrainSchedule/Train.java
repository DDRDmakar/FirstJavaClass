package TrainSchedule;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.zip.DataFormatException;

//=========================================

final class Train {
    private String trainName;
    private Timestamp time;
    private Collection<String> intermediateStationStorage;

    Train(String trainName, Timestamp time, String destination) {
        this.trainName = trainName;
        this.time = time;
        this.intermediateStationStorage = new HashSet<>();
        this.intermediateStationStorage.add(destination);
    }
    Train(String trainName) {
        this.trainName = trainName;
        this.time = new Timestamp(System.currentTimeMillis());
        this.intermediateStationStorage = new HashSet<>();
    }

     String getName() { return trainName; }

    void addIntermediateStation(String intermediate) throws IllegalArgumentException {
        if(intermediateStationStorage.contains(intermediate)) throw new IllegalArgumentException();
        else intermediateStationStorage.add(intermediate);
    }
    void deleteIntermediateStation(String intermediate) throws IllegalArgumentException {
        if(intermediateStationStorage.contains(intermediate)) intermediateStationStorage.remove(intermediate);
        else throw new IllegalArgumentException();
    }

    // Returns time before arrival in ms
    long timeTrainGoesTo(String target, Timestamp currentTime) throws DataFormatException {
        if(intermediateStationStorage.contains(target) && time.toLocalDateTime().isAfter(currentTime.toLocalDateTime()))
            return time.getTime() - currentTime.getTime();
        else throw new DataFormatException();
    }

    public String toString() {
        return "TRAIN( " + trainName + ": at time: " + time + ", " + Integer.toString(intermediateStationStorage.size()) + " inter-station(s) )";
    }

    public int hashCode() {
        int result = 7;
        result += ( trainName.hashCode() + time.hashCode() + intermediateStationStorage.hashCode() ) * 13;
        return result;
    }

    public boolean equals( Object other ) {
        return other instanceof Train && this.trainName.equals(((Train)other).getName());

    }
}
