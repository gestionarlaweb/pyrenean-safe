package com.simplecraft.pyreneansafe.repository;

import com.simplecraft.pyreneansafe.model.PeakShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeakShelterRepository extends JpaRepository<PeakShelter, Long> {

    List<PeakShelter> findByRegion(String region);
    List<PeakShelter> findByType(PeakShelter.FacilityType type);
    List<PeakShelter> findByRegionAndType(String region, PeakShelter.FacilityType type);

    boolean existsByNameAndRegion(String name, String region);
}