package pl.edu.wat.wcy.isi.ai.transportapp.service.impl;

import pl.edu.wat.wcy.isi.ai.transportapp.service.JourneyService;
import pl.edu.wat.wcy.isi.ai.transportapp.domain.Journey;
import pl.edu.wat.wcy.isi.ai.transportapp.repository.JourneyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Journey.
 */
@Service
public class JourneyServiceImpl implements JourneyService{

    private final Logger log = LoggerFactory.getLogger(JourneyServiceImpl.class);

    @Inject
    private JourneyRepository journeyRepository;

    /**
     * Save a journey.
     *
     * @param journey the entity to save
     * @return the persisted entity
     */
    public Journey save(Journey journey) {
        log.debug("Request to save Journey : {}", journey);
        Journey result = journeyRepository.save(journey);
        return result;
    }

    /**
     *  Get all the journeys.
     *
     *  @return the list of entities
     */
    public List<Journey> findAll() {
        log.debug("Request to get all Journeys");
        List<Journey> result = journeyRepository.findAll();

        return result;
    }

    /**
     *  Get one journey by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Journey findOne(String id) {
        log.debug("Request to get Journey : {}", id);
        Journey journey = journeyRepository.findOne(id);
        return journey;
    }

    /**
     *  Delete the  journey by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Journey : {}", id);
        journeyRepository.delete(id);
    }

}
