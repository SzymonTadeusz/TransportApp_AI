package pl.edu.wat.wcy.isi.ai.transportapp.web;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.wcy.isi.ai.transportapp.domain.Car;
import pl.edu.wat.wcy.isi.ai.transportapp.domain.DriverRankingRow;
import pl.edu.wat.wcy.isi.ai.transportapp.web.rest.CarResource;
import pl.edu.wat.wcy.isi.ai.transportapp.service.DriverRankingSerivce;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jakub on 02.01.17.
 */
@RestController
@RequestMapping("/api")
public class DriverRanking {
    private final Logger log = LoggerFactory.getLogger(CarResource.class);

    @Inject
    private DriverRankingSerivce driverRankingSerivce;


    @GetMapping("/driverRanking")
    @Timed
    public List<DriverRankingRow> getRanking() {
        return  driverRankingSerivce.getRanking();
    }
}
