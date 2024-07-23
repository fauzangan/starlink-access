package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.ServiceDetailDTO;
import Starlink.starlink_access.model.Statistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StatisticService {
    Statistic create(ServiceDetailDTO productDTO);
    Page<Statistic> getAll(Pageable pageable, ServiceDetailDTO productDTO);
    Statistic update(Long id, ServiceDetailDTO productDTO);
    void delete (Long id);
}
