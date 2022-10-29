package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.CreateComment;
import com.csye7125group1.Webapp.DataClasses.CreateReminder;
import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.TaskComments;
import com.csye7125group1.Webapp.Entities.TaskReminders;
import com.csye7125group1.Webapp.Entities.UserTasks;
import com.csye7125group1.Webapp.Repositories.*;
import com.csye7125group1.Webapp.Utility.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ReminderController {

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

    @PostMapping(path = "/v1/task/createreminder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createreminder(@Valid @RequestBody CreateReminder newreminder, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            if (userRepository.checkrecords(authcreds[0])==0 || taskRepository.checkrecords(newreminder.getTaskname(), authcreds[0])==0){
                return new ResponseEntity<String>("This is sus", HttpStatus.BAD_REQUEST);
            }
            if (reminderRepository.checkrecords(newreminder.getTaskname(), authcreds[0])>5){
                return new ResponseEntity<String>("Task exceeds maximum reminders", HttpStatus.BAD_REQUEST);
            }


            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                UserTasks task = taskRepository.getTask(newreminder.getTaskname(), authcreds[0]);

                TaskReminders reminder = new TaskReminders(newreminder);

                reminder.setUsertask(task);

                reminderRepository.save(reminder);

                return new ResponseEntity(HttpStatus.CREATED);
            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
