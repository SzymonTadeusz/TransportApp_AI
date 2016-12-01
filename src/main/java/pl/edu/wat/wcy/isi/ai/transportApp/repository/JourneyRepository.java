package pl.edu.wat.wcy.isi.ai.transportApp.repository;

import pl.edu.wat.wcy.isi.ai.transportApp.domain.Journey;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Journey entity.
 */
@SuppressWarnings("unused")
public interface JourneyRepository extends MongoRepository<Journey,String> {

}
