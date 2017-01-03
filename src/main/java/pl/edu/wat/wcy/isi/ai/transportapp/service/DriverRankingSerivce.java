package pl.edu.wat.wcy.isi.ai.transportapp.service;

import pl.edu.wat.wcy.isi.ai.transportapp.domain.DriverRankingRow;

import java.util.List;

/**
 * Created by jakub on 02.01.17.
 */

public interface DriverRankingSerivce {
    List<DriverRankingRow> getRanking();
}
