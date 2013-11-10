/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import wad.booklibrary.domain.User;
import wad.booklibrary.repository.UserRepository;

@Service
public class UserService implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    private void init() {
        User admin = new User();
        admin.setName("admin");
        admin.setPassword("admin");
        admin.setRole("admin");
        userRepository.save(admin);
    }

    public void addUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRole("user");
        userRepository.save(user);
    }
    
    public User getUser(String name){
        return userRepository.findByName(name);
    }

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        User user = new User();
        String username = a.getName();
        String password = a.getCredentials().toString();
        user = userRepository.findByName(username);
        if (user != null && user.getPassword().equals(password)) {
            grantedAuths.add(new SimpleGrantedAuthority(user.getRole()));
            return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        }
        else {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    @Override
    public boolean supports(Class authType) {
        return authType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
