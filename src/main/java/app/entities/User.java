package app.entities;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    private Long id;
    @Column(nullable = true, length = 64)
    private String photos;
    private String username;
    private String password;
    private String passwordConfirm;
    private String role;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    @OneToMany(mappedBy="customer")
    private Set<Order> orderHistory;
    @OneToOne
    private Order cart;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getPhotos() {
        return photos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Order getCart() {
        return cart;
    }

    public void setCart(Order cart) {
        this.cart = cart;
    }

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;

        if(role.equals("ADMIN")) return "/images/" + photos;
         
        return "/images/user_photos/" + id + "/" + photos;
    }

    @Override
    @ElementCollection(targetClass=String.class)
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {

            List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
    
            list.add(new SimpleGrantedAuthority("ROLE_" + role));
    
            return list;
    }

    public boolean isIsAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    public void setIsAccountNonExpired(boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }

    public boolean isIsAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    public void setIsAccountNonLocked(boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    public boolean isIsCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    public void setIsCredentialsNonExpired(boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    public boolean isIsEnabled() {
        return this.isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    
    
 
}