package pl.edu.wat.wcy.isi.ai.transportapp.web.rest;

import pl.edu.wat.wcy.isi.ai.transportapp.AiProjektApp;

import pl.edu.wat.wcy.isi.ai.transportapp.domain.Car;
import pl.edu.wat.wcy.isi.ai.transportapp.repository.CarRepository;
import pl.edu.wat.wcy.isi.ai.transportapp.service.CarService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CarResource REST controller.
 *
 * @see CarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AiProjektApp.class)
public class CarResourceIntTest {

    private static final String DEFAULT_BRAND = "AAAAA";
    private static final String UPDATED_BRAND = "BBBBB";

    private static final String DEFAULT_MODEL = "AAAAA";
    private static final String UPDATED_MODEL = "BBBBB";

    private static final String DEFAULT_PLATE_NO = "AAAAA";
    private static final String UPDATED_PLATE_NO = "BBBBB";

    @Inject
    private CarRepository carRepository;

    @Inject
    private CarService carService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCarMockMvc;

    private Car car;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarResource carResource = new CarResource();
        ReflectionTestUtils.setField(carResource, "carService", carService);
        this.restCarMockMvc = MockMvcBuilders.standaloneSetup(carResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Car createEntity() {
        Car car = new Car()
                .brand(DEFAULT_BRAND)
                .model(DEFAULT_MODEL)
                .plateNo(DEFAULT_PLATE_NO);
        return car;
    }

    @Before
    public void initTest() {
        carRepository.deleteAll();
        car = createEntity();
    }

    @Test
    public void createCar() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car

        restCarMockMvc.perform(post("/api/cars")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(car)))
                .andExpect(status().isCreated());

        // Validate the Car in the database
        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(databaseSizeBeforeCreate + 1);
        Car testCar = cars.get(cars.size() - 1);
        assertThat(testCar.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testCar.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testCar.getPlateNo()).isEqualTo(DEFAULT_PLATE_NO);
    }

    @Test
    public void getAllCars() throws Exception {
        // Initialize the database
        carRepository.save(car);

        // Get all the cars
        restCarMockMvc.perform(get("/api/cars?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(car.getId())))
                .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
                .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
                .andExpect(jsonPath("$.[*].plateNo").value(hasItem(DEFAULT_PLATE_NO.toString())));
    }

    @Test
    public void getCar() throws Exception {
        // Initialize the database
        carRepository.save(car);

        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", car.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car.getId()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.plateNo").value(DEFAULT_PLATE_NO.toString()));
    }

    @Test
    public void getNonExistingCar() throws Exception {
        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCar() throws Exception {
        // Initialize the database
        carService.save(car);

        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Update the car
        Car updatedCar = carRepository.findOne(car.getId());
        updatedCar
                .brand(UPDATED_BRAND)
                .model(UPDATED_MODEL)
                .plateNo(UPDATED_PLATE_NO);

        restCarMockMvc.perform(put("/api/cars")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCar)))
                .andExpect(status().isOk());

        // Validate the Car in the database
        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(databaseSizeBeforeUpdate);
        Car testCar = cars.get(cars.size() - 1);
        assertThat(testCar.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testCar.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCar.getPlateNo()).isEqualTo(UPDATED_PLATE_NO);
    }

    @Test
    public void deleteCar() throws Exception {
        // Initialize the database
        carService.save(car);

        int databaseSizeBeforeDelete = carRepository.findAll().size();

        // Get the car
        restCarMockMvc.perform(delete("/api/cars/{id}", car.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(databaseSizeBeforeDelete - 1);
    }
}
