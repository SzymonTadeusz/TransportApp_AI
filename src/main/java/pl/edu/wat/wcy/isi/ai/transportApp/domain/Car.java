package pl.edu.wat.wcy.isi.ai.transportApp.domain;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Car.
 */

@Document(collection = "car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("brand")
    private String brand;

    @Field("model")
    private String model;

    @Field("plate_no")
    private String plateNo;

    @Field("driver")
    private Driver driver;

    public Car(){}

    public Car(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Car added = objectMapper.reader(Car.class).readValue(json);
            this.setId(added.getId());
            this.setBrand(added.getBrand());
            this.setModel(added.getModel());
            this.setPlateNo(added.getId());
            this.setDriver(added.getDriver());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public Car brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public Car model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public Car plateNo(String plateNo) {
        this.plateNo = plateNo;
        return this;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        if(car.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + id +
            ", brand='" + brand + "'" +
            ", model='" + model + "'" +
            ", plateNo='" + plateNo + "'" +
            '}';
    }
}
