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

    public List<PeakShelter> getSheltersByRegion(String region) {
        return repository.findByRegion(region);
    }

    public PeakShelter createShelter(PeakShelter shelter) {
        if (repository.existsByNameAndRegion(shelter.getName(), shelter.getRegion())) {
            throw new IllegalArgumentException("Ya existe un refugio o pico con el nombre '" + shelter.getName() + "' en la región '" + shelter.getRegion() + "'.");
        }
        return repository.save(shelter);
    }

    public PeakShelter updateShelter(Long id, PeakShelter updatedShelter) {
        PeakShelter existingShelter = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encuentra el refugio o pico con el ID: " + id));

        boolean nameAndRegionExists = repository.existsByNameAndRegion(updatedShelter.getName(), updatedShelter.getRegion());
        if (nameAndRegionExists && (!existingShelter.getName().equals(updatedShelter.getName()) || !existingShelter.getRegion().equals(updatedShelter.getRegion()))) {
            throw new IllegalArgumentException("Ya existe otro refugio o pico con el nombre '" + updatedShelter.getName() + "' en la región '" + updatedShelter.getRegion() + "'.");
        }

        existingShelter.setName(updatedShelter.getName());
        existingShelter.setType(updatedShelter.getType());
        existingShelter.setRegion(updatedShelter.getRegion());
        existingShelter.setLatitude(updatedShelter.getLatitude());
        existingShelter.setLongitude(updatedShelter.getLongitude());
        existingShelter.setElevation(updatedShelter.getElevation());

        return repository.save(existingShelter);
    }

    public void deleteShelter(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se puede borrar. No existe ningún refugio o pico con el ID: " + id);
        }
        repository.deleteById(id);
    }
}