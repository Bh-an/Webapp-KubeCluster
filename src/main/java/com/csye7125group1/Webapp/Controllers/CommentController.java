package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.CreateComment;
import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.TaskComments;
import com.csye7125group1.Webapp.Entities.UserTasks;
import com.csye7125group1.Webapp.Repositories.CommentRepository;
import com.csye7125group1.Webapp.Repositories.ListRepository;
import com.csye7125group1.Webapp.Repositories.TaskRepository;
import com.csye7125group1.Webapp.Repositories.UserRepository;
import com.csye7125group1.Webapp.Utility.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;

    @PostMapping(path = "/v1/task/createcomment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskComments> createcomment(@Valid @RequestBody CreateComment newcomment, @RequestHeader("Authorization") String authheader) {
        logger.info("create comment call start");
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            if (userRepository.checkrecords(authcreds[0])==0 || taskRepository.checkrecords(newcomment.getTaskname(), authcreds[0])==0){
                logger.info("create comment call end");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                UserTasks task = taskRepository.getTask(newcomment.getTaskname(), authcreds[0]);

                TaskComments comment = new TaskComments(newcomment);

                comment.setUsertask(task);

                commentRepository.save(comment);
                logger.info("create comment call end");

                return new ResponseEntity(HttpStatus.CREATED);
            }
            logger.info("create comment call end");

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        logger.info("create comment call end");

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
