package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.repositories.VilleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Iterable<PatientEntity> findAll(String search) {
        if(search!=null){
            return pr.findByNomContainsOrPrenomContains(search, search);
        }
        return pr.findAll();
    }

    public Page<PatientEntity> findAllByPage(Integer pageNo, Integer pageSize , String search  ) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        if( search != null && search.length() > 0 ){
            return pr.findByNomContains(search, paging );
        }

        return pr.findAll( paging );
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
