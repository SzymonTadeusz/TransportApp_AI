package pl.edu.wat.wcy.isi.ai.transportApp.repository;

import pl.edu.wat.wcy.isi.ai.transportApp.domain.Car;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Car entity.
 */
@SuppressWarnings("unused")
public interface CarRepository extends MongoRepository<Car,String> {

}
