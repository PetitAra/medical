package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.repositories.VilleRepository;
import org.springframework.stereotype.Service;

@Service
public class VilleService {

    private VilleRepository vr;

    public VilleService(VilleRepository vr){
        this.vr =vr;
    }

    public Iterable<VilleEntity> findAll() {
        return vr.findAll();
    }

    public VilleEntity findVille(int id) {
        return vr.findById(id).get();
    }

    public void delete(int id) {
        vr.deleteById(id);
    }
}
