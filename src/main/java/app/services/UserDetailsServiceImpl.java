package app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entities.User;
import app.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.security.user.name}")
    private String adminUserName;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Value("${spring.security.user.roles}")
    private String adminRole;

    private boolean isAdminCreated = false;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals(adminUserName) && !isAdminCreated) {

            User user = new User();

            user.setUsername(adminUserName);
            user.setRole("ADMIN");
            user.setPassword(adminPassword);
            user.setPhotos("admin.png");
            user.setPasswordConfirm(adminPassword);

            User savedUser = userRepository.save(user);

            isAdminCreated = true;

            return savedUser;
        }

        User user = userRepository.findByUsername(username);

        return user;

    }

}