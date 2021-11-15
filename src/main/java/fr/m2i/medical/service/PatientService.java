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

    public boolean villeExists(VilleEntity v) {
        return VilleRepository.findByNom(v.getNom()) != null;
    }

    public boolean dateExists(PatientEntity p) {
        return PatientRepository.findByDate(p.getDateNaissance()) != null;
    }

    public boolean isValidAddress(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(email).matches()){
            return true;
        }
        return false;
    }

    public void checkPatient(PatientEntity p) throws InvalidObjectException {
        if(p.getNom().length()<=2){
            throw new InvalidObjectException("Nom de Patient invalide");
        }
        if(p.getPrenom().length()<=2){
            throw new InvalidObjectException("Nom du pays invalide");
        }

        if (dateExists(p.getDateNaissance()) == false){
            throw new InvalidObjectException("La date invalide");
        }

        if(villeExists(p.getVille()) == false){
            throw new InvalidObjectException("Nom de la ville invalide");
        }

        if(!isValidAddress(p.getAdresse()) && p.getAdresse().length()<10){
            throw new InvalidObjectException("Adresse invalide");
        }
    }

    public void addVille(PatientEntity p) throws InvalidObjectException {
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

        pr.save(pExistant);}
       catch(NoSuchElementException e){
           throw e;
       }

    }
}
