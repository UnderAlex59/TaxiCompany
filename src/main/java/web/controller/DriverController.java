package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.model.DriverStatus;
import web.model.Drivers;
import web.secrive.DriverService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/drivers", produces = MediaType.APPLICATION_JSON_VALUE)
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("")
    public List<Drivers> getAllDrivers(){
        return driverService.getAll();
    }

    @GetMapping("/{driverId}")
    public Drivers getDriversById(@PathVariable("driverId") Integer id){
        return driverService.getById(id);
    }

    @GetMapping("/getByStatus/{driverStatus}")
    public List<Drivers> getByStatus(@PathVariable("driverStatus")String driverStatus){
        return driverService.getByStatus(DriverStatus.fromValue(driverStatus));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Drivers createDriver(@RequestBody Drivers drivers){

        return driverService.createDrivers(drivers);
    }

    @PatchMapping("")
    public String updateById(@RequestBody Drivers drivers){

        return driverService.updateById(drivers);
    }

    @DeleteMapping("/{driverId}")
    public void deleteById(@PathVariable("driverId") Integer id){
        driverService.deleteById(id);
    }
}
