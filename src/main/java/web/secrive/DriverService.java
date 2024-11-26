package web.secrive;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.DriverStatus;
import web.model.Drivers;
import web.repository.DriverRepositoryImp;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepositoryImp driverRepositoryImp;


    public Drivers createDrivers(Drivers drivers) {
        driverRepositoryImp.createDrivers(drivers);
        return drivers;
    }

    public List<Drivers> getAll() {
        return driverRepositoryImp.getAll();
    }

    public Drivers getById(Integer id) {
        return driverRepositoryImp.getById(id);
    }

    public String updateById(Drivers drivers) {
        return driverRepositoryImp.upddateById(drivers);
    }

    public String deleteById(Integer id) {
        return driverRepositoryImp.deleteById(id);
    }

    public List<Drivers> getByStatus(DriverStatus driverStatus){
        return driverRepositoryImp.getByStatus(driverStatus);
    }
}
