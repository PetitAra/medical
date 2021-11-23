package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.RdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.metadata.TableMetaDataContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/rdv")
public class RdvController {

    @Autowired
    private RdvService rs;

    @Autowired
    private PatientService ps;

    @GetMapping(value = "")
    public String list(Model model, HttpServletRequest request) {
        Integer patientId = Integer.parseInt(request.getParameter("patient") != null
                && request.getParameter("patient").length() > 0 ? request.getParameter("patient") : "0" );
        String datesearch = request.getParameter("datesearch" );

        Date dateRecherche = null ;
        Iterable<RdvEntity> rdvs;

        if( datesearch != null && datesearch.length() == 10 ){
            rdvs = rs.findAll( patientId , datesearch );
        }else{
            rdvs = rs.findAll( patientId , "" );
        }

        model.addAttribute("rdvs", rdvs);
        model.addAttribute("patients" , ps.findAll() );
        model.addAttribute("error", request.getParameter("error"));
        model.addAttribute("success", request.getParameter("success"));
        model.addAttribute("patientid", patientId);
        model.addAttribute("datesearch", dateRecherche);

        return "rdv/list_rdv";
    }



    @GetMapping(value = "/add")
    public String add (Model model){
        model.addAttribute("patients",ps.findAll());
        model.addAttribute("rdv", new RdvEntity());
        return "rdv/add_edit";
    }

    private RdvEntity createRDV( HttpServletRequest request ){
        String dateheure = request.getParameter("dateheure");
        dateheure = dateheure.replace("T" , " ");
        Integer duree = Integer.parseInt(request.getParameter("duree") );
        String note = request.getParameter("note");
        String type = request.getParameter("type");
        int patientId = Integer.parseInt( request.getParameter("patient") ) ;

        PatientEntity pe = new PatientEntity();
        pe.setId( patientId );

        System.out.println( "Date et heure passés  : " + dateheure );

        // Timestamp dateheure, Integer duree, String note, String type, PatientEntity patient
        // Préparation de l'entité à sauvegarder
        RdvEntity u = new RdvEntity( pe,Timestamp.valueOf( dateheure + ":00" ) , duree , note , type );
        return u;
    }

    @PostMapping(value = "/add")
    public String addPost( HttpServletRequest request , Model model ){
        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            rs.addRdv( createRDV( request ) );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }

        return "redirect:/rdv?success=true";
    }

    @RequestMapping( method = { RequestMethod.GET , RequestMethod.POST} , value = "/edit/{id}" )
    public String editGetPost( Model model , @PathVariable int id ,  HttpServletRequest request ) throws InvalidObjectException {

        if( request.getMethod().equals("POST") ){
            rs.editRdv( id, createRDV( request ) );
            return "redirect:/rdv?success=true";
        }else{
            try{
                model.addAttribute("patients" , ps.findAll() );
                model.addAttribute("rdv" , rs.findRdv( id ) );
            }catch ( NoSuchElementException e ){
                return "redirect:/rdv?error=rdv%20introuvalble";
            }

            return "rdv/add_edit";
        }
    }

    @GetMapping(value = "/delete/{id}")
    public String delete (@PathVariable int id){
        String message = "?success=true";
        try {
            rs.delete(id);
        }catch ( Exception e ){
            message = "?error=Rdv%20introuvalble";
        }
        return "redirect:/rdv"+message;
    }

}
