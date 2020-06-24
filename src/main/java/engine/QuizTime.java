package engine;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class QuizTime {
    @Id
    public Long id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    public Date completedAt;

    QuizTime(Long id, Date completedAt){
        this.id = id;
        this.completedAt = completedAt;
    }
}
