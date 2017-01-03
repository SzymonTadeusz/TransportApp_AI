package pl.edu.wat.wcy.isi.ai.transportapp.service;

import pl.edu.wat.wcy.isi.ai.transportapp.domain.Journey;

import java.util.List;

/**
 * Service Interface for managing Journey.
 */
public interface JourneyService {

    /**
     * Save a journey.
     *
     * @param journey the entity to save
     * @return the persisted entity
     */
    Journey save(Journey journey);

    /**
     *  Get all the journeys.
     *  
     *  @return the list of entities
     */
    List<Journey> findAll();

    /**
     *  Get the "id" journey.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Journey findOne(String id);

    /**
     *  Delete the "id" journey.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
