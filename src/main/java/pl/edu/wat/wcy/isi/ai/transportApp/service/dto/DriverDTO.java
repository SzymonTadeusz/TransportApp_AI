package pl.edu.wat.wcy.isi.ai.transportApp.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Driver entity.
 */
public class DriverDTO implements Serializable {

    private String id;

    private String name;

    private String drivingLicenseNo;

    private Integer birthDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDrivingLicenseNo() {
        return drivingLicenseNo;
    }

    public void setDrivingLicenseNo(String drivingLicenseNo) {
        this.drivingLicenseNo = drivingLicenseNo;
    }
    public Integer getBirthDate() {
        return birthDate;
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

        DriverDTO driverDTO = (DriverDTO) o;

        if ( ! Objects.equals(id, driverDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DriverDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", drivingLicenseNo='" + drivingLicenseNo + "'" +
            ", birthDate='" + birthDate + "'" +
            '}';
    }
}
