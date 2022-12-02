package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.*;
import com.csye7125group1.Webapp.Entities.*;
import com.csye7125group1.Webapp.Repositories.*;
import com.csye7125group1.Webapp.Services.KafkaPublishService;
import com.csye7125group1.Webapp.Utility.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;
    @Autowired
    KafkaPublishService kafkaProducer;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @PostMapping(path = "/v1/task/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTasks> createtask(@Valid @RequestBody CreateTask newtask, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        logger.info("v1/task/create API called with username: " +  authcreds[0]);

        if (authcreds != null) {

            AppUser user = userRepository.finduserbyusername(authcreds[0]);
            UserLists list = listRepository.getList(newtask.getListname(), authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                if (taskRepository.checkrecords(newtask.getTask(), authcreds[0]) != 0) {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }

                UserTasks task = new UserTasks(newtask);

                task.setUserlist(list);

                taskRepository.save(task);

                logger.info("Task created with id: " +  task.getTaskid());

                SearchTask esTask = new SearchTask(task, authcreds[0]);

                kafkaProducer.sendMessage(esTask);

                return new ResponseEntity(HttpStatus.CREATED);
            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(path = "/v1/task/updatetask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatetask(@RequestBody UpdateTask updatedtask, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds != null) {

            AppUser user = userRepository.finduserbyusername(authcreds[0]);
            boolean fields = false;
            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                if (taskRepository.checkrecords(updatedtask.getTaskname(), authcreds[0]) == 0) {
                    return new ResponseEntity<String>("Task does not exist", HttpStatus.BAD_REQUEST);
                }

                UserTasks task = taskRepository.getTask(updatedtask.getTaskname(), authcreds[0]);


//                if (updateuser.getUsername() != null){
//                    if(!updateuser.getUsername().matches(user.getUsername())) {
//                        return new ResponseEntity(HttpStatus.BAD_REQUEST);
//                    }
//                }
                if (updatedtask.getListname() != null) {
                    UserLists list = listRepository.getList(updatedtask.getListname(), authcreds[0]);
                    task.setUserlist(list);
                    list.updatetime();
                    listRepository.save(list);
                    fields = true;
                }
                if (updatedtask.getDuedate() != null) {
                    task.setDuedate(updatedtask.getDuedate());
                    task.setState(task.evalstate(updatedtask.getDuedate()));
                    fields = true;

                }
                    if (!fields) {
                        return new ResponseEntity(HttpStatus.BAD_REQUEST);
                    }
                    task.updatetime();
                    user.accountupdate();
                    taskRepository.save(task);
                    userRepository.save(user);
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity(HttpStatus.UNAUTHORIZED);

            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }



    @DeleteMapping(path = "/v1/task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletetask(@RequestBody UpdateTask updatedtask, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds != null) {

            AppUser user = userRepository.finduserbyusername(authcreds[0]);
            boolean fields = false;
            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                if (taskRepository.checkrecords(updatedtask.getTaskname(), authcreds[0]) == 0) {
                    return new ResponseEntity<String>("Task does not exist", HttpStatus.BAD_REQUEST);
                }

                UserTasks task = taskRepository.getTask(updatedtask.getTaskname(), authcreds[0]);

                List<TaskComments> commentsList = commentRepository.getComments(updatedtask.getTaskname(), user.getUsername());
                List<TaskReminders> reminderList = reminderRepository.getReminders(updatedtask.getTaskname(), user.getUsername());

                for (TaskComments comment : commentsList) {

                    commentRepository.delete(comment);
                }

                for (TaskReminders reminder : reminderList) {

                    reminderRepository.delete(reminder);
                }

                taskRepository.delete(task);


                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


    @GetMapping(path = "/v1/task/show", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShowTask> viewtask(@RequestBody UpdateTask updatedtask, @RequestHeader("Authorization") String authheader) {

        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                UserTasks task = taskRepository.getTask(updatedtask.getTaskname(), authcreds[0]);

//                task.setUserlist_task(null);

                ShowTask showtask = new ShowTask(task);

//                Map<String, List<String>> responsemap = new HashMap<String, List<String>>();



                return new ResponseEntity<ShowTask>(showtask, HttpStatus.OK);

            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping(path = "/v1/task", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> viewtasks(@RequestBody CreateList targetlist, @RequestHeader("Authorization") String authheader) {

        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                UserLists list = listRepository.getList(targetlist.getListname(), authcreds[0]);

//                task.setUserlist_task(null);
                List<String> tasklist = new ArrayList<>();

                for (UserTasks task : list.getUsertasks()){
                    tasklist.add(task.getTask());
                }

//                Map<String, List<String>> responsemap = new HashMap<String, List<String>>();



                return new ResponseEntity<List<String>>(tasklist, HttpStatus.OK);

            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }
}
