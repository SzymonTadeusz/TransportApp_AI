package pl.edu.wat.wcy.isi.ai.transportApp.service;

import pl.edu.wat.wcy.isi.ai.transportApp.domain.Car;

import java.util.List;

/**
 * Service Interface for managing Car.
 */
public interface CarService {

    /**
     * Save a car.
     *
     * @param car the entity to save
     * @return the persisted entity
     */
    Car save(Car car);

    /**
     *  Get all the cars.
     *  
     *  @return the list of entities
     */
    List<Car> findAll();

    /**
     *  Get the "id" car.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Car findOne(String id);

    /**
     *  Delete the "id" car.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
