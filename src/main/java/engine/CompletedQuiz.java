package engine;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CompletedQuiz {

    public Long userid;

    public Long quizId;
    @Id
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
