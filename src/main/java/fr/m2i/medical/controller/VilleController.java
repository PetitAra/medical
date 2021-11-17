package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.sql.Date;

//CONTROLLER WEB
@Controller
@RequestMapping("/ville")
public class VilleController {
    @Autowired
    private VilleService vs;

    @GetMapping(value = "")
    public String list (Model model){
        model.addAttribute("villes",vs.findAll());
        return "ville/list_ville";
    }

    @GetMapping(value = "/add")
    public String add (Model model){
        model.addAttribute("ville", new VilleEntity());
        return "ville/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPost (HttpServletRequest request) throws InvalidObjectException {

        String nom=request.getParameter("nom");
        int code_postale=Integer.parseInt(request.getParameter("code_postale"));
        String pays=request.getParameter("pays");

        VilleEntity v=new VilleEntity(0, nom, code_postale, pays);

        try{
            vs.addVille(v);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "redirect:/ville";
    }


    @GetMapping(value = "/edit/{id}")
    public String edit( Model model , @PathVariable int id ){
        model.addAttribute("ville" , vs.findVille(id) );
        return "ville/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPost (HttpServletRequest request, @PathVariable int id){
        VilleEntity vExistant= vs.findVille(id);

        String nom=request.getParameter("nom");
        int code_postale=Integer.parseInt(request.getParameter("code_postale"));
        String pays=request.getParameter("pays");

        VilleEntity v=new VilleEntity(0, nom, code_postale, pays);
        v.setId(vExistant.getId());

        try {
            vs.editVille(id, v);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/ville";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete (@PathVariable int id){
        vs.delete(id);
        return "redirect:/ville";
    }

    public VilleService getVs() {
        return vs;
    }

    public void setVs(VilleService vs) {
        this.vs = vs;
    }
}
