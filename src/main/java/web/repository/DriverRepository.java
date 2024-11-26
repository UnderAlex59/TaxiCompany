package web.repository;

import web.model.Drivers;
import web.model.DriverStatus;

import java.util.List;

public interface DriverRepository {

    Drivers createDrivers(Drivers drivers);

    List<Drivers> getAll();

    List<Drivers> getByStatus(DriverStatus driverStatus);

    Drivers getById(Integer id);

    String upddateById(Drivers drivers);

    String deleteById(Integer id);

}
