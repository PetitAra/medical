package fr.m2i.medical.api;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InvalidObjectException;
import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

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

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<PatientEntity> add(@RequestBody PatientEntity p) {
        System.out.println(p);
        try {
            ps.addVille(p);
            //creation de url d'access au nouvel objet => http://localhost:80/api/patient/20
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();
            return ResponseEntity.created(uri).body(p);
        } catch (InvalidObjectException e) {
            //return ResponseEntity.badRequest().build();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable int id, @RequestBody PatientEntity p) {
        try {
            ps.editPatient(id, p);
        } catch (InvalidObjectException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient introuvable");
        }
    }

    @DeleteMapping  (value="/{id}", produces = "application/json")
    public void delete(@PathVariable int id){
        ps.delete(id);
    }
}
