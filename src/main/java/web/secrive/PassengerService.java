package web.secrive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Passenger;
import web.repository.PassengerRepositoryImp;

import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepositoryImp passengerRepositoryImp;


    public Passenger createPassenger(Passenger passenger) {
        passengerRepositoryImp.createPassenger(passenger);
        return passenger;
    }

    public List<Passenger> getAll() {
        return passengerRepositoryImp.getAll();
    }

    public Passenger getById(Integer id) {
        return passengerRepositoryImp.getById(id);
    }

    public String updateById(Passenger passenger) {
        return passengerRepositoryImp.upddateById(passenger);
    }

    public String deleteById(Integer id) {
        return passengerRepositoryImp.deleteById(id);
    }

}
