package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.repositories.VilleRepository;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
public class PatientService {
    //injection des dependances
    private PatientRepository pr;
    private VilleRepository vr;


    public PatientService(PatientRepository pr, VilleRepository vr){
        this.pr =pr;
        this.vr = vr;
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

    public static boolean validate(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    private void checkPatient(PatientEntity p) throws InvalidObjectException {
        if(p.getNom().length()<=2){
            throw new InvalidObjectException("Nom de Patient invalide");
        }
        if(p.getPrenom().length()<=2){
            throw new InvalidObjectException("Prénom invalide");
        }
        if(p.getAdresse().length()<10){
            throw new InvalidObjectException("Adresse invalide");
        }
        VilleEntity ve = vr.findById(p.getVille().getId()).get();
        if(ve==null){
            throw new InvalidObjectException("Ville invalide");
        }
        if (!validate(p.getEmail()) || p.getEmail().length()<5) {
            throw new InvalidObjectException("Email invalide");
        }
        if(p.getTelephone().length()<8){
            throw new InvalidObjectException("Telephone invalide");
        }

    }
    public void addPatient(PatientEntity p) throws InvalidObjectException {
        checkPatient(p);
        pr.save(p);
    }

    public void editPatient(int id, PatientEntity p) throws InvalidObjectException {
        checkPatient(p);
       try{ PatientEntity pExistant= pr.findById(id).get();
        pExistant.setNom(p.getNom());
        pExistant.setPrenom(p.getPrenom());
        pExistant.setDateNaissance(p.getDateNaissance());
        pExistant.setAdresse(p.getAdresse());
        pExistant.setVille(p.getVille());
        pExistant.setTelephone(p.getTelephone());
        pExistant.setEmail(p.getEmail());

        pr.save(pExistant);}
       catch(NoSuchElementException e){
           throw e;
       }

    }
}
