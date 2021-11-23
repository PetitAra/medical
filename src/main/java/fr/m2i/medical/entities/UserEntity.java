package fr.m2i.medical.entities;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "patients", catalog = "")
public class UserEntity {
    private int id;
    private String username;
    private String email;
    private String roles;
    private String password;
    private String name;
    private String photouser;


    public UserEntity() {
    }

    public UserEntity(int id, String username, String email, String roles, String password, String name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.name = name;
    }

    public UserEntity(int id, String username, String email, String roles, String password, String name , String photouser) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.name = name;
        this.photouser = photouser;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "roles")
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotouser() {
        return photouser;
    }

    public void setPhotouser(String photouser) {
        this.photouser = photouser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(roles, that.roles) && Objects.equals(password, that.password) && Objects.equals(name, that.name) && Objects.equals(photouser, that.photouser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, roles, password, name, photouser);
    }

}
