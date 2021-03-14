package app.services;

import org.springframework.stereotype.Service;

import app.entities.User;




@Service
public interface UserService {

    User save(User user);

    User findByUsername(String username);
    
    
}