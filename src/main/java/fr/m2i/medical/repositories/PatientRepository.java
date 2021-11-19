package fr.m2i.medical.repositories;
import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientRepository extends PagingAndSortingRepository<PatientEntity, Integer> {
    // Iterable<PatientEntity> findByNom (String nom);

    Iterable<PatientEntity> findByNomContainsOrPrenomContains(String nsearch,String psearch);

    public Page<PatientEntity> findByNomContains(String search, Pageable pageable);

    public Page<PatientEntity> findAll(Pageable pageable );
}

