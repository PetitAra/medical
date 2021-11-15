package fr.m2i.medical.api;


import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {

    UserService us;

    public UserAPIController(UserService us){this.us =us;}


    @GetMapping(value = "", produces = "application/json")
    public Iterable<UserEntity> getAll(){
        return us.findAll();
        //return pr.findByNom("Dupont");
    }

    @GetMapping (value="/{id}", produces = "application/json")
    public UserEntity get(@PathVariable int id){
        return us.findUser(id);
    }

    @DeleteMapping(value="/{id}", produces = "application/json")
    public void delete(@PathVariable int id){
        us.delete(id);
    }
}

