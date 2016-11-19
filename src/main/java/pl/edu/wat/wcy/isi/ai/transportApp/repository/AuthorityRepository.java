package pl.edu.wat.wcy.isi.ai.transportApp.repository;

import pl.edu.wat.wcy.isi.ai.transportApp.domain.Authority;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}
