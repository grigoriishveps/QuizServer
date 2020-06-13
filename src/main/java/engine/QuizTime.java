package engine;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class QuizTime {
    @Id
    public Long id;

    @Temporal(TemporalType.TIMESTAMP)
    public Date completedAt;

    QuizTime(Long id, Date completedAt){
        this.id = id;
        this.completedAt = completedAt;
    }
}
