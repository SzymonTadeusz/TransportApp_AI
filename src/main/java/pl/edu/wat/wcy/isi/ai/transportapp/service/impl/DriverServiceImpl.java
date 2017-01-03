package pl.edu.wat.wcy.isi.ai.transportapp.service.impl;

import pl.edu.wat.wcy.isi.ai.transportapp.service.DriverService;
import pl.edu.wat.wcy.isi.ai.transportapp.service.mapper.DriverMapper;
import pl.edu.wat.wcy.isi.ai.transportapp.domain.Driver;
import pl.edu.wat.wcy.isi.ai.transportapp.repository.DriverRepository;
import pl.edu.wat.wcy.isi.ai.transportapp.service.dto.DriverDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Service Implementation for managing Driver.
 */
@Service
public class DriverServiceImpl implements DriverService {

    private final Logger log = LoggerFactory.getLogger(DriverServiceImpl.class);

    @Inject
    private DriverRepository driverRepository;

    @Inject
    private DriverMapper driverMapper;

    /**
     * Save a driver.
     *
     * @param driverDTO the entity to save
     * @return the persisted entity
     */
    public DriverDTO save(DriverDTO driverDTO) {
        log.debug("Request to save Driver : {}", driverDTO);
        Driver driver = driverMapper.driverDTOToDriver(driverDTO);
        driver = driverRepository.save(driver);
        DriverDTO result = driverMapper.driverToDriverDTO(driver);
        return result;
    }

    /**
     *  Get all the drivers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<DriverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drivers");
        Page<Driver> result = driverRepository.findAll(pageable);
        return result.map(driver -> driverMapper.driverToDriverDTO(driver));
    }

    /**
     *  Get one driver by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public DriverDTO findOne(String id) {
        log.debug("Request to get Driver : {}", id);
        Driver driver = driverRepository.findOne(id);
        DriverDTO driverDTO = driverMapper.driverToDriverDTO(driver);
        return driverDTO;
    }

    /**
     *  Delete the  driver by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Driver : {}", id);
        driverRepository.delete(id);
    }
}
