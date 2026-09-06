package com.simplecraft.pyreneansafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "peak_shelters", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "region"})
}) // (uniqueConstraints) para evitar que se repitan el mismo nombre y región en la base de datos.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeakShelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name cannot be blank")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "The facility type (PEAK or SHELTER) is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FacilityType type;

    @NotBlank(message = "The region is required")
    private String region;

    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Double longitude;

    @Min(value = 0, message = "Elevation cannot be negative")
    private Integer elevation;

    public enum FacilityType {
        PEAK,
        SHELTER
    }
}