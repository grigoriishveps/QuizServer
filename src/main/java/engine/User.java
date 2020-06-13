package engine;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Entity
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @NotNull
    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    public String email;
    @Size(min = 5)
    public String password;

    //public ArrayList<CompletedQuiz> completedQuizes = new ArrayList<>();

    public User (){}

    public User (String email, String password){
        this.email = email;
        this.password = password;
    }


}
