package web.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import web.model.Passenger;

import java.util.List;

@Repository
@Transactional
public class PassengerRepositoryImp implements PassengerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Passenger createPassenger(Passenger passenger) {
        entityManager.persist(passenger);
        return passenger;
    }

    @Override
    public List<Passenger> getAll() {
        return entityManager.createQuery("from Passenger p order by p.id", Passenger.class).getResultList();
    }

    @Override
    public Passenger getById(Integer id) {
        Passenger passenger = entityManager.find(Passenger.class, id);
        if(passenger == null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Пассажир с ID = %d не найден (CODE 404)",id));
        return passenger;
    }

    @Override
    public String upddateById(Passenger passenger) {
        Passenger oldPassenger = getById(passenger.getId());
        if(oldPassenger == null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Пассажир с ID = %d не найден (CODE 404)",passenger.getId()));
        entityManager.detach(oldPassenger);
        oldPassenger.setPhone(passenger.getPhone());
        entityManager.merge(oldPassenger);
        return String.format("Данные пассажира с id = %d обновлены", oldPassenger.getId());
    }

    @Override
    public String deleteById(Integer id) {
        Passenger passenger = getById(id);
        if(passenger == null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Пассажир с ID = %d не найден (CODE 404)",id));
        entityManager.remove(passenger);
        return String.format("Пассажир %s удалён из базы", passenger.toString());
    }
}
