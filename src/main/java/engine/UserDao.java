package engine;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;


public class UserDao {
    @NotBlank
    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String email;
    @NotNull
    @Size(min = 5)
    private String password;
    private Collection<Role> authorities = new  ArrayList<>();
    public UserDao (){}

    public UserDao (String email, String password){
        this.email = email;
        this.password = password;
        this.authorities = new ArrayList<Role>();
    }
    public UserDao (String email, String password, Collection<Role> authorities){
        this.email = email;
        this.password = password;
        this.authorities =  authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<Role> authorities) {
        this.authorities = authorities;
    }
}
