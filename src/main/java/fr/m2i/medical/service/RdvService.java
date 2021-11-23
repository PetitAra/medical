package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.repositories.RdvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.NoSuchElementException;

@Service
public class RdvService {

    private RdvRepository rr;
    private PatientRepository pr;

    public RdvService(RdvRepository rr, PatientRepository pr) {
        this.rr = rr;
        this.pr = pr;
    }

    public Iterable<RdvEntity> findAll() {
        return rr.findAll();
    }

    public Iterable<RdvEntity> findAll(int patient , String dh) {
        Date dateRecherche;
        if( patient > 0 && dh.toString().length() == 10 ){
            dateRecherche = Date.valueOf(dh); // request.getParameter("datesearch") => "2021-11-22"
            return rr.findByCustomByDateEtPatient(patient , dateRecherche );
        }else if(  patient > 0 ){
            return rr.findByPatient_Id(patient);
        }else if(  dh.toString().length() == 10){
            dateRecherche = Date.valueOf(dh); // request.getParameter("datesearch") => "2021-11-22"
            return rr.findByCustomByDate(dateRecherche);
        }

        return rr.findAll();
    }

    public RdvEntity findRdv(int id) {
        return rr.findById(id).get();
    }

    public void delete(int id) {
        rr.deleteById(id);
    }

    public void addRdv(RdvEntity r) throws InvalidObjectException {
        rr.save(r);
    }

    public void editRdv(int id, RdvEntity r) throws InvalidObjectException {
        try {
            RdvEntity rExistant = rr.findById(id).get();
            rExistant.setPatient(r.getPatient());
            rExistant.setDateHeure(r.getDateHeure());
            rExistant.setDuree(r.getDuree());
            rExistant.setNote(r.getNote());
            rExistant.setType(r.getType());

            rr.save(rExistant);
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
}
