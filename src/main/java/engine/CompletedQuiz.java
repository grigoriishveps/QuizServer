package engine;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CompletedQuiz {

    public Long userid;

    public Long quizId;
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date completedAt;
    CompletedQuiz(){};
    CompletedQuiz(Long userid, Long quizId, Date completedAt){
        this.userid = userid;
        this.quizId = quizId;
        this.completedAt = completedAt;
    }

    public QuizTime toQuizTime(){
        return new QuizTime(quizId, completedAt);
    }
}
