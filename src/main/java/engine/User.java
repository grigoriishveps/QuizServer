package engine;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @NotNull
    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    public String email;
    @Size(min = 5)
    public String password;
    @ManyToMany(fetch = FetchType.EAGER)
    public Collection<Role> authoritiesList = new  ArrayList<>();

    //public ArrayList<CompletedQuiz> completedQuizes = new ArrayList<>();

    public User (){}

    public User (String email, String password, Collection<Role> authorities){
        this.email = email;
        this.password = password;
        this.authoritiesList = authorities;
    }

    public User(UserDao userDao){
        this.email = userDao.getEmail();
        this.password = userDao.getPassword();
        this.authoritiesList = userDao.getAuthorities();
    }

    @Override
    public Collection<Role> getAuthorities() {
        return authoritiesList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
