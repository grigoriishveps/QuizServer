package engine;

public class AnswerResponse {
    public boolean success;
    public String feedback;

    AnswerResponse(boolean success, String feedback){
        this.success = success;
        this.feedback = feedback;
    }
}
