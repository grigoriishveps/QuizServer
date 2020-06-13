package engine;

import engine.Quiz;

public class Question {
    public Long id;
    public String title;
    public String text;
    public String [] options;

    public Question (){}

    public Question (Quiz quiz){
        this.id = quiz.id;
        this.title = quiz.title;
        this.text = quiz.text;
        this.options = quiz.options;
    }
}
