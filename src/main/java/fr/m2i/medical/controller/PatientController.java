package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;


//CONTROLLER WEB
@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService ps;

    @Autowired
    private VilleService vs;

    @GetMapping(value = "")
    public String list (Model model){
        model.addAttribute("patients",ps.findAll());
    return "patient/list_patient";
    }

    @GetMapping(value = "/add")
    public String add (Model model){
        model.addAttribute("villes",vs.findAll());
        model.addAttribute("patient", new PatientEntity());
        return "patient/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPost (HttpServletRequest request){

        String nom=request.getParameter("nom");
        String prenom=request.getParameter("prenom");
        String naissance=request.getParameter("naissance");
        String adresse=request.getParameter("adresse");
        int ville=Integer.parseInt(request.getParameter("ville"));
        String email=request.getParameter("email");
        String telephone=request.getParameter("telephone");

        VilleEntity v=new VilleEntity();
        v.setId(ville);
        PatientEntity p=new PatientEntity(0, nom, prenom, adresse, Date.valueOf(naissance),email, telephone, v);

        try{
            ps.addPatient(p);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "redirect:/patient";
    }


    @GetMapping(value = "/edit/{id}")
    public String edit( Model model , @PathVariable int id ){
        model.addAttribute("patient" , ps.findPatient(id) );
        model.addAttribute("villes",vs.findAll());
        //... récupérer le patient à modifier et le passer à la vue
        return "patient/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPost (HttpServletRequest request, @PathVariable int id){
        PatientEntity pExistant= ps.findPatient(id);

        String nom=request.getParameter("nom");
        String prenom=request.getParameter("prenom");
        String naissance=request.getParameter("naissance");
        String adresse=request.getParameter("adresse");
        int ville=Integer.parseInt(request.getParameter("ville"));
        String email=request.getParameter("email");
        String telephone=request.getParameter("telephone");

        VilleEntity v=new VilleEntity();
        v.setId(ville);
        PatientEntity p=new PatientEntity(0, nom, prenom, adresse, Date.valueOf(naissance),email, telephone, v);
        p.setId(pExistant.getId());

        try {
            ps.editPatient(id, p);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/patient";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete (@PathVariable int id){
        ps.delete(id);
        return "redirect:/patient";
    }


    public PatientService getPs() {
        return ps;
    }

    public void setPs(PatientService ps) {
        this.ps = ps;
    }


    /*
    // @RequestMapping(value = "/test", method = RequestMethod.GET)
    //@GetMapping(value = "/test") - l'option 2
    @ResponseBody
    public String testme(){
        return "<h1>Bonjour</h1>";
    }
*/
   /* @GetMapping (value = "/delete/id")
    public void delete(@PathVariable int id){

        pr.deleteById(id);
    }*/
}
