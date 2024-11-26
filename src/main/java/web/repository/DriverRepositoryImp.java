package web.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import web.model.Drivers;
import web.model.DriverStatus;

import java.util.List;


@Repository
@Transactional
public class DriverRepositoryImp implements DriverRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Drivers createDrivers(Drivers drivers) {
        if (drivers.getStatus() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Некорректный статус водителя (CODE 400)"));
        entityManager.persist(drivers);
        return drivers;
    }

    @Override
    public List<Drivers> getAll() {
        return entityManager.createQuery("from Drivers c order by c.id", Drivers.class).getResultList();
    }

    @Override
    public Drivers getById(Integer id) {
        Drivers drivers = entityManager.find(Drivers.class, id);
        if(drivers == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Водитель с ID = %d не найден (CODE 404)",id));
        return drivers;
    }

    @Override
    public String upddateById(Drivers updateDrivers) {
        Drivers oldDrivers = getById(updateDrivers.getId());
        if(oldDrivers == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Водитель с ID = %d не найден (CODE 404)",updateDrivers.getId()));
        entityManager.detach(oldDrivers);
        oldDrivers.setStatus(updateDrivers.getStatus());
        entityManager.merge(oldDrivers);
        return String.format("Данные водителя с id = %d обновлены", updateDrivers.getId());
    }

    @Override
    public List<Drivers> getByStatus(DriverStatus driverStatus) {
        return entityManager.createQuery(String.format("from Drivers c where c.status = %s order by c.id", driverStatus.name()), Drivers.class).getResultList();
    }

    @Override
    public String deleteById(Integer id) {
        Drivers deleteDrivers = getById(id);
        if(deleteDrivers == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Водитель с ID = %d не найден (CODE 404)",id));
        entityManager.remove(deleteDrivers);
        return String.format("Водитель %s удалён из базы", deleteDrivers.toString());
    }
}
