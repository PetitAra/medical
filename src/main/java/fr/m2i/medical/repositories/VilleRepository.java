package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface VilleRepository extends CrudRepository<VilleEntity, Integer> {
    Iterable<VilleEntity> findByDate(Date d);

    Iterable<VilleEntity> findByNom(String nom);
}
