package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.sql.Date;
import java.util.NoSuchElementException;

//CONTROLLER WEB
@Controller
@RequestMapping("/ville")
public class VilleController {
    @Autowired
    private VilleService vs;

    //param page : numéro de la page actuelle
    // size : nbre d'élements par page
    @GetMapping(value = "")
    public String list(Model model, HttpServletRequest request,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "5") int size) {
        String search = request.getParameter("search");

        Page<VilleEntity> villes = vs.findAllByPage(page, size, search);

        model.addAttribute("villes", villes);
        model.addAttribute("error", request.getParameter("error"));
        model.addAttribute("success", request.getParameter("success"));
        model.addAttribute("search", search);

        model.addAttribute("pageCurrent", page);

        model.addAttribute("pages", new int[villes.getTotalPages()]);

        return "ville/list_ville";
    }


    @GetMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("ville", new VilleEntity());
        return "ville/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPost(HttpServletRequest request, Model model) throws InvalidObjectException {

        String nom = request.getParameter("nom");
        int code_postale = Integer.parseInt(request.getParameter("code_postale"));
        String pays = request.getParameter("pays");

        VilleEntity v = new VilleEntity(0, nom, code_postale, pays);

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try {
            vs.addVille(v);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("ville", v);
            model.addAttribute("error", e.getMessage());
            return "ville/add_edit";
        }
        return "redirect:/ville?success=true";
    }

    public VilleService getVservice() {
        return vs;
    }


    @GetMapping(value = "/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        try {
            model.addAttribute("ville", vs.findVille(id));
        } catch (NoSuchElementException e) {
            return "redirect:/ville?error=Ville%20introuvalble";
        }
        return "ville/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPost(HttpServletRequest request, @PathVariable int id, Model model) {
        VilleEntity vExistant = vs.findVille(id);

        String nom = request.getParameter("nom");
        int code_postale = Integer.parseInt(request.getParameter("code_postale"));
        String pays = request.getParameter("pays");

        VilleEntity v = new VilleEntity(0, nom, code_postale, pays);
        v.setId(vExistant.getId());

        try {
            vs.editVille(id, v);
        } catch (Exception e) {
            v.setId(-1); // hack
            System.out.println(e.getMessage());
            model.addAttribute("ville", v);
            model.addAttribute("error", e.getMessage());
            return "ville/add_edit";
        }
        return "redirect:/ville?success=true";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable int id) {
        String message = "?success=true";
        try {
            vs.delete(id);
        } catch (Exception e) {
            message = "?error=Ville%20introuvalble";
        }
        return "redirect:/ville" + message;
    }

    public VilleService getVs() {
        return vs;
    }

    public void setVs(VilleService vs) {
        this.vs = vs;
    }
}
