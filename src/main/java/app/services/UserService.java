package app.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import app.entities.User;




@Service
public interface UserService {

    User save(User user);

    User findByUsername(String username);
    
    
}