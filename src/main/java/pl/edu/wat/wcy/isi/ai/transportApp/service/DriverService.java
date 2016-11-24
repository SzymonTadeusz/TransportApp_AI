package pl.edu.wat.wcy.isi.ai.transportApp.service;

import pl.edu.wat.wcy.isi.ai.transportApp.service.dto.DriverDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Driver.
 */
public interface DriverService {

    /**
     * Save a driver.
     *
     * @param driverDTO the entity to save
     * @return the persisted entity
     */
    DriverDTO save(DriverDTO driverDTO);

    /**
     *  Get all the drivers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DriverDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" driver.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DriverDTO findOne(String id);

    /**
     *  Delete the "id" driver.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
