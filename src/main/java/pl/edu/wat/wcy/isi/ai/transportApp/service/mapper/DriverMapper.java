package pl.edu.wat.wcy.isi.ai.transportApp.service.mapper;

import pl.edu.wat.wcy.isi.ai.transportApp.domain.Driver;
import pl.edu.wat.wcy.isi.ai.transportApp.service.dto.DriverDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Driver and its DTO DriverDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DriverMapper {

    DriverDTO driverToDriverDTO(Driver driver);

    List<DriverDTO> driversToDriverDTOs(List<Driver> drivers);

    Driver driverDTOToDriver(DriverDTO driverDTO);

    List<Driver> driverDTOsToDrivers(List<DriverDTO> driverDTOs);
}
