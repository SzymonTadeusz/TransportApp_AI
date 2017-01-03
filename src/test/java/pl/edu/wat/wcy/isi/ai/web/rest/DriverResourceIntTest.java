package pl.edu.wat.wcy.isi.ai.web.rest;

import pl.edu.wat.wcy.isi.ai.transportapp.domain.Driver;
import pl.edu.wat.wcy.isi.ai.transportapp.repository.DriverRepository;
import pl.edu.wat.wcy.isi.ai.transportapp.service.DriverService;
import pl.edu.wat.wcy.isi.ai.transportapp.service.dto.DriverDTO;
import pl.edu.wat.wcy.isi.ai.transportapp.service.mapper.DriverMapper;
import pl.edu.wat.wcy.isi.ai.transportapp.web.rest.DriverResource;
import pl.edu.wat.wcy.isi.ai.transportapp.AiProjektApp;

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
import pl.edu.wat.wcy.isi.ai.transportapp.web.rest.TestUtil;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DriverResource REST controller.
 *
 * @see DriverResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AiProjektApp.class)
public class DriverResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_DRIVING_LICENSE_NO = "AAAAA";
    private static final String UPDATED_DRIVING_LICENSE_NO = "BBBBB";

    private static final Integer DEFAULT_BIRTH_DATE = 1;
    private static final Integer UPDATED_BIRTH_DATE = 2;

    @Inject
    private DriverRepository driverRepository;

    @Inject
    private DriverMapper driverMapper;

    @Inject
    private DriverService driverService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDriverMockMvc;

    private Driver driver;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DriverResource driverResource = new DriverResource();
        ReflectionTestUtils.setField(driverResource, "driverService", driverService);
        this.restDriverMockMvc = MockMvcBuilders.standaloneSetup(driverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Driver createEntity() {
        Driver driver = new Driver()
                .name(DEFAULT_NAME)
                .drivingLicenseNo(DEFAULT_DRIVING_LICENSE_NO)
                .birthDate(DEFAULT_BIRTH_DATE);
        return driver;
    }

    @Before
    public void initTest() {
        driverRepository.deleteAll();
        driver = createEntity();
    }

    @Test
    public void createDriver() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver
        DriverDTO driverDTO = driverMapper.driverToDriverDTO(driver);

        restDriverMockMvc.perform(post("/api/drivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
                .andExpect(status().isCreated());

        // Validate the Driver in the database
        List<Driver> drivers = driverRepository.findAll();
        assertThat(drivers).hasSize(databaseSizeBeforeCreate + 1);
        Driver testDriver = drivers.get(drivers.size() - 1);
        assertThat(testDriver.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDriver.getDrivingLicenseNo()).isEqualTo(DEFAULT_DRIVING_LICENSE_NO);
        assertThat(testDriver.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
    }

    @Test
    public void getAllDrivers() throws Exception {
        // Initialize the database
        driverRepository.save(driver);

        // Get all the drivers
        restDriverMockMvc.perform(get("/api/drivers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(driver.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].drivingLicenseNo").value(hasItem(DEFAULT_DRIVING_LICENSE_NO.toString())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)));
    }

    @Test
    public void getDriver() throws Exception {
        // Initialize the database
        driverRepository.save(driver);

        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", driver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(driver.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.drivingLicenseNo").value(DEFAULT_DRIVING_LICENSE_NO.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE));
    }

    @Test
    public void getNonExistingDriver() throws Exception {
        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDriver() throws Exception {
        // Initialize the database
        driverRepository.save(driver);
        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Update the driver
        Driver updatedDriver = driverRepository.findOne(driver.getId());
        updatedDriver
                .name(UPDATED_NAME)
                .drivingLicenseNo(UPDATED_DRIVING_LICENSE_NO)
                .birthDate(UPDATED_BIRTH_DATE);
        DriverDTO driverDTO = driverMapper.driverToDriverDTO(updatedDriver);

        restDriverMockMvc.perform(put("/api/drivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
                .andExpect(status().isOk());

        // Validate the Driver in the database
        List<Driver> drivers = driverRepository.findAll();
        assertThat(drivers).hasSize(databaseSizeBeforeUpdate);
        Driver testDriver = drivers.get(drivers.size() - 1);
        assertThat(testDriver.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDriver.getDrivingLicenseNo()).isEqualTo(UPDATED_DRIVING_LICENSE_NO);
        assertThat(testDriver.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    }

    @Test
    public void deleteDriver() throws Exception {
        // Initialize the database
        driverRepository.save(driver);
        int databaseSizeBeforeDelete = driverRepository.findAll().size();

        // Get the driver
        restDriverMockMvc.perform(delete("/api/drivers/{id}", driver.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Driver> drivers = driverRepository.findAll();
        assertThat(drivers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
