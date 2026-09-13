package com.simplecraft.pyreneansafe.controller;

import com.simplecraft.pyreneansafe.model.PeakShelter;
import com.simplecraft.pyreneansafe.service.PeakShelterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shelters")
@RequiredArgsConstructor
public class PeakShelterController {

    private final PeakShelterService service;

    @GetMapping
    public ResponseEntity<List<PeakShelter>> getShelters(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(service.filterShelters(region, type));
    }

    @PostMapping
    public ResponseEntity<PeakShelter> createShelter(@Valid @RequestBody PeakShelter shelter) {
        return ResponseEntity.ok(service.createShelter(shelter));
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<PeakShelter>> getByRegion(@PathVariable String region) {
        return ResponseEntity.ok(service.getSheltersByRegion(region));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeakShelter> updateShelter(@PathVariable Long id, @Valid @RequestBody PeakShelter shelter) {
        return ResponseEntity.ok(service.updateShelter(id, shelter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelter(@PathVariable Long id) {
        service.deleteShelter(id);
        return ResponseEntity.noContent().build();
    }
}