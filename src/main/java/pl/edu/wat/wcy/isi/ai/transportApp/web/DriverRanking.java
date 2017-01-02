package pl.edu.wat.wcy.isi.ai.transportApp.web;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.wcy.isi.ai.transportApp.domain.Car;
import pl.edu.wat.wcy.isi.ai.transportApp.domain.DriverRankingRow;
import pl.edu.wat.wcy.isi.ai.transportApp.web.rest.CarResource;

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
    private pl.edu.wat.wcy.isi.ai.transportApp.service.DriverRankingSerivce driverRankingSerivce;


    @GetMapping("/driverRanking")
    @Timed
    public List<DriverRankingRow> getRanking() {
        return  driverRankingSerivce.getRanking();
    }
}
