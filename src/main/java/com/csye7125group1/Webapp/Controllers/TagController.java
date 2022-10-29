package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.CreateComment;
import com.csye7125group1.Webapp.DataClasses.CreateTag;
import com.csye7125group1.Webapp.DataClasses.UpdateTask;
import com.csye7125group1.Webapp.Entities.*;
import com.csye7125group1.Webapp.Repositories.*;
import com.csye7125group1.Webapp.Utility.Authenticator;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TagController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;

    @PostMapping(path = "/v1/user/createtag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createtag(@Valid @RequestBody CreateTag newtag, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            if (userRepository.checkrecords(authcreds[0])==0){

                return new ResponseEntity<String>("User Doesn't exist", HttpStatus.BAD_REQUEST);
            }

            if (tagRepository.checkrecords(newtag.getTag(), authcreds[0])!=0){
                return new ResponseEntity<String>("Tag already exists", HttpStatus.BAD_REQUEST);
            }

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {


                TaskTags tag = new TaskTags(newtag);

                tag.setAppuser(user);

                tagRepository.save(tag);

                return new ResponseEntity(HttpStatus.CREATED);
            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(path = "/v1/task/createtag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createtasktag(@Valid @RequestBody CreateTag newtag, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            if (userRepository.checkrecords(authcreds[0])==0){

                return new ResponseEntity<String>("User Doesn't exist", HttpStatus.BAD_REQUEST);
            }

            if (taskRepository.checkrecords(newtag.getTask(), authcreds[0])==0){
                return new ResponseEntity<String>("Task doesn't exist", HttpStatus.BAD_REQUEST);
            }

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                UserTasks task = taskRepository.getTask(newtag.getTask(), authcreds[0]);

                if (tagRepository.checkrecords(newtag.getTag(), authcreds[0])!=0){

                    TaskTags tag = tagRepository.getTag(newtag.getTag(), authcreds[0]);
                    tag.setUsertask(task);

                    tag.updatetime();
                    tagRepository.save(tag);

                    return new ResponseEntity( HttpStatus.CREATED);
                }

                TaskTags tag = new TaskTags(newtag);

                tag.setAppuser(user);
                tag.setUsertask(task);

                tag.updatetime();
                tagRepository.save(tag);

                return new ResponseEntity(HttpStatus.CREATED);
            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

//

    @DeleteMapping(path = "/v1/tag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletetag(@RequestBody CreateTag oldtag, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds != null) {

            AppUser user = userRepository.finduserbyusername(authcreds[0]);
            boolean fields = false;

            if (userRepository.checkrecords(authcreds[0])==0){

                return new ResponseEntity<String>("User Doesn't exist", HttpStatus.BAD_REQUEST);
            }

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                if (tagRepository.checkrecords(oldtag.getTag(), user.getUsername())==0) {
                    return new ResponseEntity<String>("Tag does not exist", HttpStatus.BAD_REQUEST);
                }

                TaskTags tag = tagRepository.getTag(oldtag.getTag(), authcreds[0]);

                tagRepository.delete(tag);


                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}

//@PutMapping(path = "/v1/task/attachtag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> attachtag(@RequestBody UpdateTask newtask, @RequestHeader("Authorization") String authheader) {
//        String[] authcreds = authenticator.getauthcreds(authheader);
//
//        if (authcreds!=null){
//
//            if (userRepository.checkrecords(authcreds[0])==0){
//
//                return new ResponseEntity<String>("User Doesn't exist", HttpStatus.BAD_REQUEST);
//            }
//
//            if (taskRepository.checkrecords(newtask.getTaskname(), authcreds[0])==0){
//                return new ResponseEntity<String>("Task doesn't exist", HttpStatus.BAD_REQUEST);
//            }
//
//            AppUser user = userRepository.finduserbyusername(authcreds[0]);
//
//            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {
//
//                UserTasks task = taskRepository.getTask(newtask.getTaskname(), authcreds[0]);
//
//                TaskTags tag = tagRepository.getTag()
//
//                tag.setAppuser(user);
//                tag.setUsertask(task);
//
//
//                tagRepository.save(tag);
//
//                return new ResponseEntity(HttpStatus.CREATED);
//            }
//
//            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//        }
//
//        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//    }