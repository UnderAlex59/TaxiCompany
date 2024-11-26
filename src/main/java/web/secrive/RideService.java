package web.secrive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Drivers;
import web.model.Passenger;
import web.model.Ride;
import web.repository.RideRepository;

import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    public Ride createRide(Passenger passenger){
        return rideRepository.createRide(passenger);
    }

    public  Ride acceptRide(Drivers drivers, Integer rideId){
        return rideRepository.acceptRide(drivers, rideId);
    }

    public Ride cancelledRide(Passenger passenger, Integer rideId){
        return rideRepository.cancelledRide(passenger, rideId);
    }

    public Ride completRide(Integer rideId, Passenger passenger){
        return rideRepository.completRide(passenger, rideId);
    }

    public List<Ride> getAll(){
        return rideRepository.getAll();
    }
}
