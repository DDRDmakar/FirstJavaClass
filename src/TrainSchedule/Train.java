package TrainSchedule;

import com.intellij.vcs.log.Hash;
import com.thoughtworks.xstream.mapper.Mapper;
import org.jetbrains.annotations.Contract;

import java.util.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;

//=========================================

/**
 * This class contains train properties for the given station:
 *     name, arrival time, destination station and
 *     all other stations on it's route.
 * It provides operations, required to add or delete
 * intermediate stations and to get time, in which it goes to
 * the given station.
 */

final class Train {

    //===
    //=== DATA
    //===

    final private String trainName;
    final private Date time;
    final private String destination;
    private Collection<String> intermediateStationStorage;

    //===
    //=== CONSTRUCTORS
    //===

    /**
     * Create new Train with specified name, arrival time,
     * destination station and list of intermediate stations
     * @param trainName - train name.
     * @param time - time train arrives to this station.
     * @param destination - destination station.
     * @param interStations - intermediate stations storage.
     * @throws NullPointerException if one or more arguments are null.
     * @throws IllegalArgumentException if name, destination or one of
     * intermediate stations are empty strings.
     */
    Train(String trainName, Date time, String destination, Collection<String> interStations) {
        if(trainName == null || time == null || destination == null || interStations == null)
            throw new NullPointerException("Train constructor arguments cannot be null");
        if(trainName.isEmpty() || destination.isEmpty() || interStations.contains(""))
            throw new IllegalArgumentException("Train constructor arguments cannot be empty strings");

        this.trainName = trainName;
        this.time = new Date(time.getTime());
        this.destination = destination;
        this.intermediateStationStorage = new HashSet<>();
        this.intermediateStationStorage.add(destination);
        this.intermediateStationStorage.addAll(interStations);
    }

    /**
     * The same constructor, but without list of intermediate stations.
     */
    Train(String trainName, Date time, String destination) {
        this(trainName, time, destination, new HashSet<>());
    }

    Train(Train that) {
        if (that == null) throw new NullPointerException("Train constructor arguments cannot be null");
        this.trainName = that.getName();
        this.destination = that.getDestination();
        this.time = that.getTime();
        this.intermediateStationStorage = that.getInterStationStorage();
    }

    //===
    //=== GETTERS
    //===
    /**
     * @return name of this train
     */
    String getName() { return trainName; }
    /**
     * @return destination station of this train
     */
    String getDestination() { return destination; }
    /**
     * @return arrival time of this train
     */
    Date getTime() { return new Date(time.getTime()); }
    /**
     * @return set of inter-station names
     */
    Collection<String> getInterStationStorage() {
        Collection<String> tempStorage = new HashSet<>();
        tempStorage.addAll(intermediateStationStorage);
        return tempStorage;
    }

    //===
    //=== OTHER FUNCTIONS
    //===
    /**
     * Checks, if this train goes to the given station.
     * @param target - station, where train should go.
     * @return true if train goes to the given station.
     *         false if it doesn't go there.
     */
    boolean contains(String target) { return intermediateStationStorage.contains(target); }

    /**
     * Adds new name to the intermediate stations list
     * @param intermediate - name of the new station
     * @throws NullPointerException if "intermediate" is null
     * @throws IllegalArgumentException if given name already is in list
     *                                     or it's empty string
     */
    void addIntermediateStation(String intermediate) {
        if(intermediate == null) throw new NullPointerException("Station name cannot be null");
        if(intermediate.isEmpty()) throw new IllegalArgumentException("Intermediate station name cannot be empty string");
        if(intermediateStationStorage.contains(intermediate)) throw new IllegalArgumentException("Station already exists");
        intermediateStationStorage.add(intermediate);
    }

    /**
     * Deletes name from the intermediate stations list.
     * @param intermediate - name to delete from list.
     * @throws NullPointerException if "intermediate" is null.
     * @throws NoSuchElementException if there is no such name in list.
     * @throws IllegalArgumentException if given station is destination of this train.
     */
    void deleteIntermediateStation(String intermediate) {
        if(intermediate == null) throw new NullPointerException("Station name cannot be null");
        if(!intermediateStationStorage.contains(intermediate)) throw new NoSuchElementException("Station does not exist");
        if(intermediate.equals(destination)) throw new IllegalArgumentException("Destination point cannot be deleted");
        intermediateStationStorage.remove(intermediate);
    }

    /**
     * Returns time before train arrival in milliseconds, if train goes to the given station.
     * @param target - name of the station, where train should go.
     * @param currentTime - current time in milliseconds.
     * @throws NullPointerException if any of arguments is null.
     * @throws NoSuchElementException if this train doesn't go to the given station.
     * @return - difference between current time and train arrival time.
     */
    long timeTrainGoesTo(String target, Date currentTime) {
        if(target == null) throw new NullPointerException("Station name cannot be null");
        if(currentTime == null) throw new NullPointerException("Time name cannot be null");
        if(!intermediateStationStorage.contains(target)) throw new NoSuchElementException("Train doesn't go to this station");
        return time.getTime() - currentTime.getTime();
    }

    //===
    //=== STANDARD FUNCTIONS
    //===

    @Override
    public String toString() {
        return "TRAIN( " + trainName + ": at time: " + time + ", " + Integer.toString(intermediateStationStorage.size()) + " inter-station(s) )";
    }

    @Override
    public int hashCode() {
        return (((trainName.hashCode()*23) + time.hashCode())*23 + intermediateStationStorage.hashCode())*23;
    }

    /**
     * Checks, if trains are the same.
     * @param other - other object to compare with this
     * @return true if other object has Train type and names of both trains are the same.
     *         false in any other case.
     */
    @Override
    public boolean equals( Object other ) {
        if(other == this) return true;
        if(other == null || other.getClass() != this.getClass()) return false;
        Train that = (Train) other;
        return this.trainName.equals(that.getName());
    }
}
