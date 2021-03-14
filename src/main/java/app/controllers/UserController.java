package app.controllers;

import app.entities.User;
import app.repositories.UserRepository;
import app.services.FileUploadUtil;
import app.services.UserService;
import app.services.UserValidator;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @GetMapping("/account")
    public String account(Model model, HttpServletRequest req) {

        String userName = req.getUserPrincipal().getName();

        System.out.println(userName);
        

        User user = null;

        if (!userName.isBlank()) {
            user = userRepository.findByUsername(userName);
        }


        System.out.println(user.getPhotosImagePath());

        model.addAttribute("user", user);

        return "account";
    }

    @GetMapping("/user/details")
    public ResponseEntity<User> getUser(HttpServletRequest req) {

        String userName = req.getUserPrincipal().getName();

        System.out.println(userName);

        User user = null;

        if (!userName.isBlank()) {
            user = userRepository.findByUsername(userName);
        }

        if(user == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<User>(user,HttpStatus.OK);

    }

    

    @PostMapping("/registration")
    public RedirectView registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,@RequestParam("image") MultipartFile multipartFile) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return new RedirectView("/registration");
        }

        String fileName = userForm.getUsername() + ".png";
       
        if(multipartFile != null)
        if(multipartFile.getSize() != 0)
        userForm.setPhotos(fileName);

        User savedUser = userService.save(userForm);


        if(multipartFile.getSize() == 0) return new RedirectView("/welcome");
        
        
        String uploadDir = "src/main/resources/static/images/user_photos/" + savedUser.getId();
 


        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new RedirectView("/welcome");
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping("/welcome")
    public RedirectView welcome(Model model, HttpServletRequest req) {

       
        

        return new RedirectView("/");
    }

    
    // @PostMapping("/users/save")
    // public RedirectView saveUser(@RequestParam("image") MultipartFile multipartFile,HttpServletRequest req) throws IOException {
         
    //     String userName = req.getUserPrincipal().getName();
    //     User user = null;
    //     if (!userName.isBlank()) {
    //         user = userRepository.findByUsername(userName);
    //     }

    //     String fileName = user.getUsername() + ".png";
    //     user.setPhotos(fileName);
         
    //     User savedUser = userRepository.save(user);
 
    //     String uploadDir = "src/main/resources/static/images/user_photos/" + savedUser.getId();
 
    //     FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
         
    //     return new RedirectView("/account", true);
    // }

}