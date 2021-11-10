package fr.m2i.medical.api;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientAPIController {

    PatientService ps;

    public PatientAPIController(PatientService ps){
        this.ps =ps;
    }


    @GetMapping(value = "", produces = "application/json")
    public Iterable<PatientEntity> getAll(){
        return ps.findAll();
        //return pr.findByNom("Dupont");
    }

    @GetMapping (value="/{id}", produces = "application/json")
    public PatientEntity get(@PathVariable int id){
        return ps.findPatient(id);
    }

    @DeleteMapping  (value="/{id}", produces = "application/json")
    public void delete(@PathVariable int id){
        ps.delete(id);
    }
}
