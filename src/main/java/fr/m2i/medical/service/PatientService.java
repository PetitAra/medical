package fr.m2i.medical.service;

import fr.m2i.medical.api.PatientAPIController;
import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.repositories.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    //injection des dependances
    private PatientRepository pr;

    public PatientService(PatientRepository pr){
        this.pr =pr;
    }

    public Iterable<PatientEntity> findAll() {
        return pr.findAll();
    }

    public PatientEntity findPatient(int id) {
        return pr.findById(id).get();
    }

    public void delete(int id) {
        pr.deleteById(id);
    }
}
