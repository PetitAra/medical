package fr.m2i.medical.controller;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.service.StorageService;
import fr.m2i.medical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/user")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class UserController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService us;



    //param page : numéro de la page actuelle
    // size : nbre d'élements par page
    @GetMapping(value = "")
    public String list(Model model, HttpServletRequest request) {
        Iterable<UserEntity> users = us.findAll();

        model.addAttribute("users", users);
        model.addAttribute("error", request.getParameter("error"));
        model.addAttribute("success", request.getParameter("success"));

        return "user/list_user";
    }

    // http://localhost:8080/user/add
    @GetMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("user", new UserEntity());
        return "user/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPost(HttpServletRequest request, Model model) throws InvalidObjectException {

        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String roles = request.getParameter("usertype");
        String password = request.getParameter("password");
        String photouser = request.getParameter("photouser");

        UserEntity u = new UserEntity(0, username, email, roles, password, name, photouser);

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try {
            us.addUser(u);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("user", u);
            model.addAttribute("error", e.getMessage());
            return "user/add_edit";
        }
        return "redirect:/user?success=true";
    }


    @GetMapping(value = "/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        try {
            model.addAttribute("user", us.findUser(id));
        } catch (NoSuchElementException e) {
            return "redirect:/user?error=User%20introuvalble";
        }
        return "user/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPost(HttpServletRequest request, @PathVariable int id, Model model) {
        UserEntity uExistant = us.findUser(id);

        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String roles = request.getParameter("usertype");
        String password = request.getParameter("password");
        String photouser = request.getParameter("photouser");

        UserEntity u = new UserEntity(0, username, email, roles, password, name, photouser);
        u.setId(uExistant.getId());

        try {
            us.editUser(id, u);
        } catch (Exception e) {
            u.setId(-1); // hack
            System.out.println(e.getMessage());
            model.addAttribute("user", u);
            model.addAttribute("error", e.getMessage());
            return "user/add_edit";
        }
        return "redirect:/user?success=true";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable int id) {
        String message = "?success=true";
        try {
            us.delete(id);
        } catch (Exception e) {
            message = "?error=User%20introuvalble";
        }
        return "redirect:/user" + message;
    }

    @PostMapping(value = "/profil/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER" })
    public String editProfil( @PathVariable int id , HttpServletRequest request, @RequestParam("photoProfil") MultipartFile file ) throws IOException {
        // Récupération des paramètres envoyés en POST
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String roles = request.getParameter("usertype");
        String username = request.getParameter("username");

        String photo = storageService.store(file , "src\\main\\resources\\static\\images\\uploads");
        // String username, String email, String roles, String password, String name
        // Préparation de l'entité à sauvegarderpassword
        UserEntity u = new UserEntity(0, username, email, roles, "", name,photo);
        u.setId( id );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            us.editProfil( id, u );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }

        // Mettre à jour l'utilisateur ????


        return "redirect:/patient?success=true";
    }


    public UserService getUs() {
        return us;
    }

    public void setUs(UserService us) {
        this.us = us;
    }

}