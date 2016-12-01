package pl.edu.wat.wcy.isi.ai.transportApp.domain;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Driver.
 */

@Document(collection = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("driving_license_no")
    private String drivingLicenseNo;

    @Min(value = 1900)
    @Max(value = 2016)
    @Field("birth_date")
    private Integer birthDate;

    public Driver(){}

    public Driver(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Driver added = objectMapper.reader(Driver.class).readValue(json);
            this.setBirthDate(added.getBirthDate());
            this.setDrivingLicenseNo(added.getDrivingLicenseNo());
            this.setName(added.getName());
            this.setId(added.getId());
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

    public String getName() {
        return name;
    }

    public Driver name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrivingLicenseNo() {
        return drivingLicenseNo;
    }

    public Driver drivingLicenseNo(String drivingLicenseNo) {
        this.drivingLicenseNo = drivingLicenseNo;
        return this;
    }

    public void setDrivingLicenseNo(String drivingLicenseNo) {
        this.drivingLicenseNo = drivingLicenseNo;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public Driver birthDate(Integer birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        if(driver.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", drivingLicenseNo='" + drivingLicenseNo + "'" +
            ", birthDate='" + birthDate + "'" +
            '}';
    }
}
