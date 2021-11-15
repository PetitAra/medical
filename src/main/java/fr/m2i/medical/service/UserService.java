package fr.m2i.medical.service;
import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository ur;

    public UserService(UserRepository ur){
        this.ur =ur;
    }

    public Iterable<UserEntity> findAll() {
        return ur.findAll();
    }

    public UserEntity findUser(int id) {
        return ur.findById(id).get();
    }

    public void delete(int id) {
        ur.deleteById(id);
    }
}
