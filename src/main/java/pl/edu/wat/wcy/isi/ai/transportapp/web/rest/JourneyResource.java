package pl.edu.wat.wcy.isi.ai.transportapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.edu.wat.wcy.isi.ai.transportapp.domain.Journey;
import pl.edu.wat.wcy.isi.ai.transportapp.service.JourneyService;
import pl.edu.wat.wcy.isi.ai.transportapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Journey.
 */
@RestController
@RequestMapping("/api")
public class JourneyResource {

    private final Logger log = LoggerFactory.getLogger(JourneyResource.class);
        
    @Inject
    private JourneyService journeyService;

    /**
     * POST  /journeys : Create a new journey.
     *
     * @param journey the journey to create
     * @return the ResponseEntity with status 201 (Created) and with body the new journey, or with status 400 (Bad Request) if the journey has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/journeys")
    @Timed
    public ResponseEntity<Journey> createJourney(@Valid @RequestBody Journey journey) throws URISyntaxException {
        log.debug("REST request to save Journey : {}", journey);
        if (journey.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("journey", "idexists", "A new journey cannot already have an ID")).body(null);
        }
        Journey result = journeyService.save(journey);
        return ResponseEntity.created(new URI("/api/journeys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("journey", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /journeys : Updates an existing journey.
     *
     * @param journey the journey to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated journey,
     * or with status 400 (Bad Request) if the journey is not valid,
     * or with status 500 (Internal Server Error) if the journey couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/journeys")
    @Timed
    public ResponseEntity<Journey> updateJourney(@Valid @RequestBody Journey journey) throws URISyntaxException {
        log.debug("REST request to update Journey : {}", journey);
        if (journey.getId() == null) {
            return createJourney(journey);
        }
        Journey result = journeyService.save(journey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("journey", journey.getId().toString()))
            .body(result);
    }

    /**
     * GET  /journeys : get all the journeys.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of journeys in body
     */
    @GetMapping("/journeys")
    @Timed
    public List<Journey> getAllJourneys() {
        log.debug("REST request to get all Journeys");
        return journeyService.findAll();
    }

    /**
     * GET  /journeys/:id : get the "id" journey.
     *
     * @param id the id of the journey to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journey, or with status 404 (Not Found)
     */
    @GetMapping("/journeys/{id}")
    @Timed
    public ResponseEntity<Journey> getJourney(@PathVariable String id) {
        log.debug("REST request to get Journey : {}", id);
        Journey journey = journeyService.findOne(id);
        return Optional.ofNullable(journey)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /journeys/:id : delete the "id" journey.
     *
     * @param id the id of the journey to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/journeys/{id}")
    @Timed
    public ResponseEntity<Void> deleteJourney(@PathVariable String id) {
        log.debug("REST request to delete Journey : {}", id);
        journeyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("journey", id.toString())).build();
    }

}
