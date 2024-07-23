package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.StatisticDTO;
import Starlink.starlink_access.model.Statistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StatisticService {
    Statistic create(StatisticDTO productDTO);
    Page<Statistic> getAll(Pageable pageable, StatisticDTO productDTO);
    Statistic update(Long id, StatisticDTO productDTO);
    void delete (Long id);
}
