package com.simplecraft.pyreneansafe.config;

import com.simplecraft.pyreneansafe.model.PeakShelter;
import com.simplecraft.pyreneansafe.model.User;
import com.simplecraft.pyreneansafe.repository.PeakShelterRepository;
import com.simplecraft.pyreneansafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PeakShelterRepository peakShelterRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        // 1. Inicializar picos y refugios si la tabla está vacía
        if (peakShelterRepository.count() == 0) {
            PeakShelter p1 = new PeakShelter();
            p1.setName("Aneto");
            p1.setType(PeakShelter.FacilityType.PEAK);
            p1.setRegion("Benasque");
            p1.setElevation(3404);
            p1.setLatitude(42.634);
            p1.setLongitude(0.655);
            peakShelterRepository.save(p1);

            PeakShelter p2 = new PeakShelter();
            p2.setName("Refugio de Góriz");
            p2.setType(PeakShelter.FacilityType.SHELTER);
            p2.setRegion("Ordesa");
            p2.setElevation(2200);
            p2.setLatitude(42.668);
            p2.setLongitude(0.035);
            peakShelterRepository.save(p2);

            PeakShelter p3 = new PeakShelter();
            p3.setName("Monte Perdido");
            p3.setType(PeakShelter.FacilityType.PEAK);
            p3.setRegion("Ordesa");
            p3.setElevation(3355);
            p3.setLatitude(42.685);
            p3.setLongitude(0.032);
            peakShelterRepository.save(p3);

            PeakShelter p4 = new PeakShelter();
            p4.setName("Refugio de la Renclusa");
            p4.setType(PeakShelter.FacilityType.SHELTER);
            p4.setRegion("Benasque");
            p4.setElevation(2140);
            p4.setLatitude(42.643);
            p4.setLongitude(0.642);
            peakShelterRepository.save(p4);

            PeakShelter p5 = new PeakShelter();
            p5.setName("Pico de Posets");
            p5.setType(PeakShelter.FacilityType.PEAK);
            p5.setRegion("Posets");
            p5.setElevation(3375);
            p5.setLatitude(42.659);
            p5.setLongitude(0.455);
            peakShelterRepository.save(p5);

            PeakShelter p6 = new PeakShelter();
            p6.setName("Refugio de Ángel Orús");
            p6.setType(PeakShelter.FacilityType.SHELTER);
            p6.setRegion("Benasque");
            p6.setElevation(2150);
            p6.setLatitude(42.648);
            p6.setLongitude(0.468);
            peakShelterRepository.save(p6);

            PeakShelter p7 = new PeakShelter();
            p7.setName("Pico de Balaitús");
            p7.setType(PeakShelter.FacilityType.PEAK);
            p7.setRegion("Sallent de Gállego");
            p7.setElevation(3144);
            p7.setLatitude(42.842);
            p7.setLongitude(-0.282);
            peakShelterRepository.save(p7);

            PeakShelter p8 = new PeakShelter();
            p8.setName("Refugio de Respomuso");
            p8.setType(PeakShelter.FacilityType.SHELTER);
            p8.setRegion("Sallent de Gállego");
            p8.setElevation(2200);
            p8.setLatitude(42.831);
            p8.setLongitude(-0.258);
            peakShelterRepository.save(p8);

            PeakShelter p9 = new PeakShelter();
            p9.setName("Pica d'Estats");
            p9.setType(PeakShelter.FacilityType.PEAK);
            p9.setRegion("Pallars Sobirà");
            p9.setElevation(3143);
            p9.setLatitude(42.667);
            p9.setLongitude(1.368);
            peakShelterRepository.save(p9);

            PeakShelter p10 = new PeakShelter();
            p10.setName("Refugio Ventosa i Calvell");
            p10.setType(PeakShelter.FacilityType.SHELTER);
            p10.setRegion("Aigüestortes");
            p10.setElevation(2220);
            p10.setLatitude(42.598);
            p10.setLongitude(0.902);
            peakShelterRepository.save(p10);

            System.out.println("--> ¡10 registros de picos y refugios con coordenadas cargados con éxito!");
        }

        // 2. Crear usuario Administrador por defecto si no existe
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@pyreneansafe.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(User.Role.ROLE_ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("--> Usuario ADMIN creado por defecto: admin / admin123");
        }
    }
}