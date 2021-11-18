package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.PatientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEntity, Integer> {
    // Iterable<PatientEntity> findByNom (String nom);

    Iterable<PatientEntity> findByNomContainsOrPrenomContains(String nsearch,String psearch);

}

