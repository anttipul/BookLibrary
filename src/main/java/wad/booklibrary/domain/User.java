/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wad.booklibrary.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name="USERS")
public class User implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    @Column(unique = true, name="NAME")
    private String name;
    
    @NotBlank
    @Column(name="PASSWORD")
    private String password;

    @Column(name="ROLEE")
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }   

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

