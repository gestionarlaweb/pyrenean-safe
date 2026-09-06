package com.simplecraft.pyreneansafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "peak_shelters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeakShelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FacilityType type; // PEAK o SHELTER

    private String region;
    private Double latitude;
    private Double longitude;
    private Integer elevation;

    public enum FacilityType {
        PEAK,
        SHELTER
    }
}