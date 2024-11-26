package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.model.Passenger;
import web.secrive.PassengerService;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/passengers", produces = MediaType.APPLICATION_JSON_VALUE)
public class PassengerController {


    @Autowired
    private PassengerService passengerService;

    @GetMapping("")
    public List<Passenger> getAllDrivers(){
        return passengerService.getAll();
    }

    @GetMapping("/{passrId}")
    public Passenger getDriversById(@PathVariable("passrId") Integer id){
        return passengerService.getById(id);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Passenger createDriver(@RequestBody Passenger passenger){
        return passengerService.createPassenger(passenger);
    }

    @PatchMapping("")
    public String updateById(@RequestBody Passenger passenger){
        return passengerService.updateById(passenger);
    }

    @DeleteMapping("/{passrId}")
    public void deleteById(@PathVariable("passrId") Integer id){
        passengerService.deleteById(id);
    }



}
