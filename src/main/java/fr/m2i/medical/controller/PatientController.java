package fr.m2i.medical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


//CONTROLLER WEB
@Controller
@RequestMapping("/patient")
public class PatientController {

    @GetMapping(value = "")
    public String list (Model model){
        model.addAttribute("nom","Amine");
    return "list_patient";
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
