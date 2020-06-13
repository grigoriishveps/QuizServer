package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @NotNull
    @Pattern(regexp = ".+")
    public String title;
    @NotNull
    @Pattern(regexp = ".+")
    public String text;
    @NotNull
    @Size(min = 2)
    public String[] options;
    public int []answer;
    @NotBlank
    public String authorEmail;

    public Quiz(){}

    public Quiz( String title, String text, String[] options, int[] answer, String authorEmail){
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
        this.authorEmail = authorEmail;
    }
    public Quiz(QuizDao quizDao, String author){
        this.title = quizDao.title;
        this.text = quizDao.text;
        this.options = quizDao.options;
        this.answer = quizDao.answer;
        this.authorEmail = author;
    }

}
