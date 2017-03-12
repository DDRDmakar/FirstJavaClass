package TrainSchedule;

import com.thoughtworks.xstream.mapper.Mapper;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;

//=========================================

final class Train {
    final private String trainName;
    final private Timestamp time;
    final private String destination;
    private Collection<String> intermediateStationStorage;

    Train(String trainName, Timestamp time, String destination, Collection<String> interStations) {
        if(trainName == null || time == null || destination == null || interStations == null)
            throw new NullPointerException("Train constructor arguments cannot be null");

        this.trainName = trainName;
        this.time = time;
        this.destination = destination;
        this.intermediateStationStorage = interStations;
        this.intermediateStationStorage.add(destination);
    }
    Train(String trainName, Timestamp time, String destination) {
        if(trainName == null || time == null || destination == null)
            throw new NullPointerException("Train constructor arguments cannot be null");

        this.trainName = trainName;
        this.time = time;
        this.destination = destination;
        this.intermediateStationStorage = new HashSet<>();
        this.intermediateStationStorage.add(destination);
    }

    String getName() { return trainName; }

    boolean contains(String target) { return intermediateStationStorage.contains(target); }

    void addIntermediateStation(String intermediate) {
        if(intermediate == null) throw new NullPointerException("Station name cannot be null");
        if(intermediateStationStorage.contains(intermediate)) throw new IllegalArgumentException("Station already exists");
        intermediateStationStorage.add(intermediate);
    }
    void deleteIntermediateStation(String intermediate) {
        if(intermediate == null) throw new NullPointerException("Station name cannot be null");
        if(!intermediateStationStorage.contains(intermediate)) throw new NoSuchElementException("Station does not exist");
        intermediateStationStorage.remove(intermediate);
    }

    // Returns time before arrival in ms
    long timeTrainGoesTo(String target, Timestamp currentTime) {
        if(target == null) throw new NullPointerException("Station name cannot be null");
        if(currentTime == null) throw new NullPointerException("Time name cannot be null");
        if(!intermediateStationStorage.contains(target)) throw new NoSuchElementException("Train doesn't go to this station");
        return time.getTime() - currentTime.getTime();
    }

    @Override
    public String toString() {
        return "TRAIN( " + trainName + ": at time: " + time + ", " + Integer.toString(intermediateStationStorage.size()) + " inter-station(s) )";
    }

    @Override
    public int hashCode() {
        return (((trainName.hashCode()*23) + time.hashCode())*23 + intermediateStationStorage.hashCode())*23;
    }

    @Override
    public boolean equals( Object other ) {
        if(other == this) return true;
        if(other == null || other.getClass() != this.getClass()) return false;
        Train that = (Train) other;
        return this.trainName.equals(that.getName());
    }
}
