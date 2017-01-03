package pl.edu.wat.wcy.isi.ai.transportapp.web.rest;

import pl.edu.wat.wcy.isi.ai.transportapp.AiProjektApp;

import pl.edu.wat.wcy.isi.ai.transportapp.domain.Car;
import pl.edu.wat.wcy.isi.ai.transportapp.domain.Journey;
import pl.edu.wat.wcy.isi.ai.transportapp.repository.JourneyRepository;
import pl.edu.wat.wcy.isi.ai.transportapp.service.JourneyService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JourneyResource REST controller.
 *
 * @see JourneyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AiProjektApp.class)
public class JourneyResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_RATING = 0;
    private static final Integer UPDATED_RATING = 1;

    private static final Car DEFAULT_TAXI = new Car();
    private static final Car UPDATED_TAXI = new Car();

    @Inject
    private JourneyRepository journeyRepository;

    @Inject
    private JourneyService journeyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJourneyMockMvc;

    private Journey journey;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JourneyResource journeyResource = new JourneyResource();
        ReflectionTestUtils.setField(journeyResource, "journeyService", journeyService);
        this.restJourneyMockMvc = MockMvcBuilders.standaloneSetup(journeyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Journey createEntity() {
        Journey journey = new Journey()
                .date(DEFAULT_DATE)
                .rating(DEFAULT_RATING)
                .taxi(DEFAULT_TAXI);
        return journey;
    }

    @Before
    public void initTest() {
        journeyRepository.deleteAll();
        journey = createEntity();
    }

    @Test
    public void createJourney() throws Exception {
        int databaseSizeBeforeCreate = journeyRepository.findAll().size();

        // Create the Journey

        restJourneyMockMvc.perform(post("/api/journeys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(journey)))
                .andExpect(status().isCreated());

        // Validate the Journey in the database
        List<Journey> journeys = journeyRepository.findAll();
        assertThat(journeys).hasSize(databaseSizeBeforeCreate + 1);
        Journey testJourney = journeys.get(journeys.size() - 1);
        assertThat(testJourney.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testJourney.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testJourney.getTaxi()).isEqualTo(DEFAULT_TAXI);
    }

    @Test
    public void getAllJourneys() throws Exception {
        // Initialize the database
        journeyRepository.save(journey);

        // Get all the journeys
        restJourneyMockMvc.perform(get("/api/journeys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(journey.getId())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
                .andExpect(jsonPath("$.[*].taxi").value(hasItem(DEFAULT_TAXI.toString())));
    }

    @Test
    public void getJourney() throws Exception {
        // Initialize the database
        journeyRepository.save(journey);

        // Get the journey
        restJourneyMockMvc.perform(get("/api/journeys/{id}", journey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journey.getId()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.taxi").value(DEFAULT_TAXI.toString()));
    }

    @Test
    public void getNonExistingJourney() throws Exception {
        // Get the journey
        restJourneyMockMvc.perform(get("/api/journeys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateJourney() throws Exception {
        // Initialize the database
        journeyService.save(journey);

        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();

        // Update the journey
        Journey updatedJourney = journeyRepository.findOne(journey.getId());
        updatedJourney
                .date(UPDATED_DATE)
                .rating(UPDATED_RATING)
                .taxi(UPDATED_TAXI);

        restJourneyMockMvc.perform(put("/api/journeys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedJourney)))
                .andExpect(status().isOk());

        // Validate the Journey in the database
        List<Journey> journeys = journeyRepository.findAll();
        assertThat(journeys).hasSize(databaseSizeBeforeUpdate);
        Journey testJourney = journeys.get(journeys.size() - 1);
        assertThat(testJourney.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testJourney.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testJourney.getTaxi()).isEqualTo(UPDATED_TAXI);
    }

    @Test
    public void deleteJourney() throws Exception {
        // Initialize the database
        journeyService.save(journey);

        int databaseSizeBeforeDelete = journeyRepository.findAll().size();

        // Get the journey
        restJourneyMockMvc.perform(delete("/api/journeys/{id}", journey.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Journey> journeys = journeyRepository.findAll();
        assertThat(journeys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
