package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface PatientRepository extends CrudRepository<PatientEntity, Integer> {
    // Iterable<PatientEntity> findByNom (String nom);
}
