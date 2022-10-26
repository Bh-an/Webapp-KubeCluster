package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.*;
import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.UserLists;
import com.csye7125group1.Webapp.Entities.UserTasks;
import com.csye7125group1.Webapp.Repositories.ListRepository;
import com.csye7125group1.Webapp.Repositories.TaskRepository;
import com.csye7125group1.Webapp.Repositories.UserRepository;
import com.csye7125group1.Webapp.Utility.Authenticator;
import com.csye7125group1.Webapp.Utility.EmailCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class Taskcontroller {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;

    @PostMapping(path = "/v1/task/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTasks> createtask(@Valid @RequestBody CreateTask newtask, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            AppUser user = userRepository.finduserbyusername(authcreds[0]);
            UserLists list = listRepository.getList(newtask.getListname(), authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {
                UserTasks task = new UserTasks(newtask);

                list.addList(task);
                listRepository.save(list);

                return new ResponseEntity<UserTasks>(task, HttpStatus.CREATED);
            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
