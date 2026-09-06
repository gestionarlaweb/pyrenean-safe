package com.simplecraft.pyreneansafe.controller;

import com.simplecraft.pyreneansafe.model.PeakShelter;
import com.simplecraft.pyreneansafe.service.PeakShelterService;
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
    public ResponseEntity<List<PeakShelter>> getAllShelters() {
        return ResponseEntity.ok(service.getAllShelters());
    }

    @PostMapping
    public ResponseEntity<PeakShelter> createShelter(@RequestBody PeakShelter shelter) {
        return ResponseEntity.ok(service.createShelter(shelter));
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<PeakShelter>> getByRegion(@PathVariable String region) {
        return ResponseEntity.ok(service.getSheltersByRegion(region));
    }
}