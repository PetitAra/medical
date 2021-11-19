package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsernameOrEmail(String username, String email);
}
