package pl.edu.wat.wcy.isi.ai.transportApp.repository;

import pl.edu.wat.wcy.isi.ai.transportApp.domain.PersistentToken;
import pl.edu.wat.wcy.isi.ai.transportApp.domain.User;
import java.time.LocalDate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends MongoRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
