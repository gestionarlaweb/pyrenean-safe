package com.simplecraft.pyreneansafe.service;

import com.simplecraft.pyreneansafe.model.PeakShelter;
import com.simplecraft.pyreneansafe.repository.PeakShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeakShelterService {

    private final PeakShelterRepository repository;

    public List<PeakShelter> getAllShelters() {
        return repository.findAll();
    }

    public PeakShelter createShelter(PeakShelter shelter) {
        if (repository.existsByNameAndRegion(shelter.getName(), shelter.getRegion())) {
            throw new IllegalArgumentException("Ya existe un refugio o pico con el nombre '" + shelter.getName() + "' en la región '" + shelter.getRegion() + "'.");
        }
        return repository.save(shelter);
    }

    public List<PeakShelter> getSheltersByRegion(String region) {
        return repository.findByRegion(region);
    }
}