package pl.edu.wat.wcy.isi.ai.transportApp.repository;

import pl.edu.wat.wcy.isi.ai.transportApp.domain.Driver;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Driver entity.
 */
@SuppressWarnings("unused")
public interface DriverRepository extends MongoRepository<Driver,String> {

}
