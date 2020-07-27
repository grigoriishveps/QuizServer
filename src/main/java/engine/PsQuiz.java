package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
public class PsQuiz {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private RepositoryQuiz dataQuiz;
    @Autowired
    private RepositoryCompletedQuiz dataCompletedQuiz;
    @Autowired
    private RepositoryUsers dataUsers;
    @Autowired
    private RepositoryRole dataRole;

    private int index = 0;
    public PsQuiz(){}
    //--------------------------
    @GetMapping("/api/quizzes" )
    public InfoPage getQuizzes(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "9") Integer pageSize,
                               @RequestParam(defaultValue = "id") String sortBy){
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
        InfoPage infoPage = new InfoPage(page, pageSize);
        Page<Quiz> readyPage = dataQuiz.findAll(paging);
        infoPage.totalElements = (int) dataQuiz.count();
        infoPage.totalPages = readyPage.getTotalPages();
        if (page == 0)
            infoPage.first = true;
        if (page == readyPage.getTotalPages()-1)
            infoPage.last = true;
        List<Question> list = ((List<Quiz>) readyPage.getContent()).stream().map(x->(new Question(x))).collect(Collectors.toList());
        infoPage.content = list;
        infoPage.empty = list.isEmpty();
        infoPage.numberOfElements = list.size();
        return infoPage;
    }

    @GetMapping("/api/quiz/{id}")
    public ResponseEntity<Question> getQuiz(@PathVariable int id){
        Optional<Quiz> quiz = dataQuiz.findById((long) id);
        if (quiz.isPresent())
            return ResponseEntity.ok(new Question(quiz.get()));
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    };

    @GetMapping("/api/quizzes/completed")
    public InfoPageCompletedQuiz getCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "9") Integer pageSize,
                                                     @RequestParam(defaultValue = "completedAt") String sortBy, @AuthenticationPrincipal UserDetails user){
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        InfoPageCompletedQuiz infoPage = new InfoPageCompletedQuiz();

        Page<CompletedQuiz> readyPage = dataCompletedQuiz.findByUserid( dataUsers.findByEmail(user.getUsername()).id, paging);
        infoPage.totalElements = (int) dataQuiz.count();
        infoPage.totalPages = readyPage.getTotalPages();
        if (page == 0)
            infoPage.first = true;
        if (page == readyPage.getTotalPages()-1)
            infoPage.last = true;
        List<QuizTime> list = (readyPage.getContent()).stream().map(x->(x.toQuizTime())).collect(Collectors.toList());
        infoPage.content = list;
        infoPage.empty = list.isEmpty();
        return infoPage;
    }

    @PostMapping("/api/quizzes")
    public Question takeAnswer(@AuthenticationPrincipal UserDetails user, @Valid @RequestBody QuizDao quizDao){
        index++;
        Quiz quiz = quizDao.toQuiz(user.getUsername());
        if (quiz.answer == null)
            quiz.answer = new int[0];
        dataQuiz.save(quiz);
        return new Question(quiz);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public  ResponseEntity<AnswerResponse> takeAnswer(@PathVariable Long id, @AuthenticationPrincipal UserDetails user, @RequestBody Answer answer ){
        if (!dataQuiz.existsById(id))
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        else{
            Quiz quiz = dataQuiz.findById(id).get();
//            Arrays.sort(quiz.answer);
//            Arrays.sort(answer.answer);

            if( Arrays.equals(quiz.answer,answer.answer)) {
                String s = user.getUsername();
                CompletedQuiz completedQuiz = new CompletedQuiz(dataUsers.findByEmail(s).id, id, new Date());
                dataCompletedQuiz.save(completedQuiz);
                return ResponseEntity.ok(new AnswerResponse(true, "Congratulations, you're right!"));
            }
            else{
                return ResponseEntity.ok(new AnswerResponse(false, "Wrong answer! Please, try again."));
            }
        }
    }
    //--------------------------------------------
    @PostMapping("api/register")
    public ResponseEntity registerUser(@Valid @RequestBody UserDao user){
        if (dataUsers.existsByEmail(user.getEmail()))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else {
            //dataRole.save(new Role("USER"));
            dataUsers.save(new User(user.getEmail(), passwordEncoder.encode(user.getPassword()), Collections.singletonList(dataRole.findByName("USER"))));
            return new ResponseEntity(HttpStatus.OK);
        }
    }




    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){
        Optional<Quiz> quiz = dataQuiz.findById(id);
        if (quiz.isPresent()) {
            if (user.getUsername().equals(quiz.get().authorEmail)){
                dataQuiz.deleteById(id);
                return new ResponseEntity(HttpStatus.NO_CONTENT);}
            else
                return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/uilogin")
    public ResponseEntity checkAutorization(){
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/user/roles")
    public ResponseEntity getRoles(@AuthenticationPrincipal User user){
        System.out.println(((List<Role>) user.getAuthorities()));
        return new ResponseEntity(HttpStatus.OK);
    }



}
