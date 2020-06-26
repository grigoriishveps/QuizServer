package engine;

import engine.Question;

import java.util.List;

public class InfoPage {
    public int totalPages;
    public int totalElements;
    public boolean last;
    public boolean first;
    public String sort;
    public int number;
    public int numberOfElements;
    public int size;
    public boolean empty;
    public Object pageable;
    public List<Question> content;


    public InfoPage(){}
    public InfoPage(int pageNumber, int size){
        this.number = pageNumber;
        this.size = size;
    }
}
