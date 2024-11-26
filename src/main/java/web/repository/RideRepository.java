package web.repository;

import web.model.*;

import java.util.List;

public interface RideRepository {

    Ride createRide(Passenger passenger);

    Ride acceptRide(Drivers drivers, Integer rideId);

    Ride cancelledRide(Passenger passenger, Integer rideId);

    Ride completRide(Passenger passenger, Integer rideId);

    List<Ride> getAll();

    Ride getById(Integer id);




}
