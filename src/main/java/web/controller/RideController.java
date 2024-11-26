package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.model.Drivers;
import web.model.Passenger;
import web.model.Ride;
import web.secrive.RideService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rides", produces = MediaType.APPLICATION_JSON_VALUE)
public class RideController {

    @Autowired
    private RideService rideService;

    @GetMapping("")
    public List<Ride> getAll(){
        return rideService.getAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Ride createRide(@RequestBody Passenger passenger){
        return rideService.createRide(passenger);
    }

    @PatchMapping("/acceptRide/{rideId}")
    public Ride acceptRide(@RequestBody Drivers driver, @PathVariable("rideId") Integer rideId){
        return rideService.acceptRide(driver, rideId);
    }

    @PatchMapping("/cancelledRide/{rideId}")
    public Ride cancelledRide(@RequestBody Passenger passenger, @PathVariable("rideId") Integer rideId){
        return rideService.cancelledRide(passenger, rideId);
    }

    @PatchMapping("/completRide/{rideId}")
    public Ride completRide(@PathVariable("rideId") Integer rideId, @RequestBody Passenger passenger){
        return rideService.completRide(rideId, passenger);
    }


}
