package web.repository;

import web.model.Passenger;

import java.util.List;

public interface PassengerRepository {

    Passenger createPassenger(Passenger passenger);

    List<Passenger> getAll();

    Passenger getById(Integer id);

    String upddateById(Passenger passenger);

    String deleteById(Integer id);
}
