package fr.m2i.medical.service;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private UserRepository ur;

    public UserService(UserRepository ur ){
        this.ur = ur;
    }

    public Iterable<UserEntity> findAll(  ) {
        return ur.findAll();
    }

    public UserEntity findUser(int id) {
        return ur.findById(id).get();
    }

    public void addUser(UserEntity u) throws InvalidObjectException {
        u.setPassword(encoder.encode(u.getPassword()));
        ur.save(u);}

    public void editProfil( int id , UserEntity u) throws NoSuchElementException {
        try{
            UserEntity uExistant = ur.findById(id).get();

            uExistant.setEmail( u.getEmail() );
            uExistant.setName( u.getName() );
            uExistant.setUsername( u.getUsername() );
            uExistant.setPhotouser( u.getPhotouser() );

            ur.save( uExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }
    }

    public void delete(int id) {
        ur.deleteById(id);
    }

    @Autowired
    private PasswordEncoder encoder;

    public void editUser(int id, UserEntity u) throws InvalidObjectException, NoSuchElementException {

        try{
            UserEntity uExistante=ur.findById(id).get();
            uExistante.setUsername(u.getUsername());
            uExistante.setEmail(u.getEmail());
            uExistante.setRoles(u.getRoles());
            uExistante.setName(u.getName());
            uExistante.setPhotouser(u.getPhotouser());

            if (u.getPassword().length()>0){
                uExistante.setPassword(encoder.encode(u.getPassword()));
            }

            ur.save(uExistante);
        }catch(NoSuchElementException e){
            throw e;
        }


    }
}