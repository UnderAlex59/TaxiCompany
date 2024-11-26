package web.repository;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import web.model.*;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class RideRepositoryImp implements RideRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Ride createRide(Passenger passengerId) {
        Passenger passenger = entityManager.find(Passenger.class, passengerId.getId());
        if(passenger == null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Пассажир с ID = %d не найден (CODE 404)",passengerId.getId()));
        Ride ride = Ride.builder().passenger(passenger).rideStatus(RideStatus.FORMED).build();
        entityManager.persist(ride);
        return ride;
    }

    @Override
    public Ride acceptRide(Drivers driversId, Integer rideId) {
        Ride ride = getById(rideId);
        if(ride == null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Заказ с ID = %d не найден (CODE 404)",rideId));
        Drivers driver = entityManager.find(Drivers.class, driversId.getId());
        if(driver == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Водитель с ID = %d не найден (CODE 404)",driversId.getId()));
        if(!ride.getRideStatus().equals(RideStatus.FORMED)) throw new ResponseStatusException((HttpStatus.FORBIDDEN), String.format("Поездка находится в состоянии %s и не может быть принята", ride.getRideStatus()));
        if(driver.getStatus().equals(DriverStatus.INACTIVE)) throw new ResponseStatusException((HttpStatus.FORBIDDEN), String.format("Водитель %s находится в состоянии Inactive и не может брать заказы", driver.toString()));
        entityManager.detach(ride);
        ride.setDrivers(driver);
        ride.setStartTime(LocalDateTime.now());
        ride.setRideStatus(RideStatus.INPROGRESS);
        entityManager.merge(ride);
        return ride;
    }

    @Override
    public Ride cancelledRide(Passenger passengerId, Integer rideId) {
        Ride ride = getById(rideId);
        if(ride == null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Поездка с ID = %d не найдена (CODE 404)",rideId));
        if (!ride.getRideStatus().equals(RideStatus.FORMED)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("Данный заказ находится в состоянии %s и не может быть отменен (CODE 403)", ride.getRideStatus()));
        }
        Passenger passenger = entityManager.find(Passenger.class, passengerId.getId());
        if(passenger == null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Пассажир с ID = %d не найден (CODE 404)",passengerId.getId()));
        if (!ride.getPassenger().getId().equals(passengerId.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("Пользователь %s не может " +
                    "отменить поездку пользователя %s (CODE 403)",passenger.toString(), ride.getPassenger().toString() ));
        }
        entityManager.detach(ride);
        ride.setRideStatus(RideStatus.CANCELLED);
        LocalDateTime now = LocalDateTime.now();
        ride.setStartTime(now);
        ride.setEndTime(now);
        entityManager.merge(ride);
        return ride;
    }

    @Override
    public Ride completRide(Passenger passengerId, Integer rideId) {
        Ride ride = getById(rideId);
        if(ride == null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Поездка с ID = %d не найдена (CODE 404)",rideId));
        Passenger passenger = entityManager.find(Passenger.class, passengerId.getId());
        if(passenger == null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Пассажир с ID = %d не найден (CODE 404)",passengerId.getId()));
        if (!ride.getPassenger().getId().equals(passenger.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("Пользователь %s не может " +
                    "завершить поездку пользователя %s (CODE 403)",passenger.toString(), ride.getPassenger().toString() ));
        }
        if (!ride.getRideStatus().equals(RideStatus.INPROGRESS)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("Данный заказ находится в состоянии %s и не может быть завершён (CODE 403)", ride.getRideStatus()));
        }
        entityManager.detach(ride);
        ride.setRideStatus(RideStatus.COMPLETED);
        LocalDateTime now = LocalDateTime.now();
        ride.setEndTime(now);
        entityManager.merge(ride);
        return ride;
    }

    @Override
    public List<Ride> getAll() {
        return entityManager.createQuery("from Ride r order by r.id", Ride.class).getResultList();
    }

    @Override
    public Ride getById(Integer id) {
        Ride ride =entityManager.find(Ride.class, id);
        if(ride == null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Поездка с ID = %d не найдена (CODE 404)",id));
        return ride;
    }

}
