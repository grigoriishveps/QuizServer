package engine;

import engine.Quiz;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Entity
public class QuizDao {

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
        public QuizDao(){}

        public QuizDao( String title, String text, String[] options, int[] answer){
            this.title = title;
            this.text = text;
            this.options = options;
            this.answer = answer;
        }
        public Quiz toQuiz(String author){
            return new Quiz(this, author);
    }
}
