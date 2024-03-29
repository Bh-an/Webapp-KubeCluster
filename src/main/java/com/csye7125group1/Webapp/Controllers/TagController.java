package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.CreateComment;
import com.csye7125group1.Webapp.DataClasses.CreateTag;
import com.csye7125group1.Webapp.DataClasses.UpdateTask;
import com.csye7125group1.Webapp.Entities.*;
import com.csye7125group1.Webapp.Repositories.*;
import com.csye7125group1.Webapp.Utility.Authenticator;
import org.apache.catalina.User;
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
import java.util.List;

@RestController
public class TagController {

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);
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
        logger.info("create tag call start");
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            if (userRepository.checkrecords(authcreds[0])==0){
                logger.warn("create tag call end: Bad Request (User Doesn't exist)");
                return new ResponseEntity<String>("User Doesn't exist", HttpStatus.BAD_REQUEST);
            }

            if (tagRepository.checkrecords(newtag.getTag(), authcreds[0])!=0){
                logger.warn("create tag call end: Bad Request (Tag already exists)");
                return new ResponseEntity<String>("Tag already exists", HttpStatus.BAD_REQUEST);
            }

            if (tagRepository.checkrecords(newtag.getTag(), authcreds[0])>10){
                logger.warn("create tag call end: Bad Request (Maximum number of tags)");
                return new ResponseEntity<String>("Maximum number of tags ", HttpStatus.BAD_REQUEST);
            }

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {


                TaskTags tag = new TaskTags(newtag);

                tag.setAppuser(user);

                tagRepository.save(tag);
                logger.info("create tag call end: Succesful");
                return new ResponseEntity(HttpStatus.CREATED);
            }

            logger.warn("create tag call end: Unauthorized");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        logger.warn("create tag call end: Unauthorized");
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(path = "/v1/task/createtag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createtasktag(@Valid @RequestBody CreateTag newtag, @RequestHeader("Authorization") String authheader) {
        logger.info("create task tag call start");
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            if (userRepository.checkrecords(authcreds[0])==0){
                logger.warn("create task tag call end (User Doesn't exist)");
                return new ResponseEntity<String>("User Doesn't exist", HttpStatus.BAD_REQUEST);
            }

            if (taskRepository.checkrecords(newtag.getTask(), authcreds[0])==0){
                logger.warn("create task tag call end (Task doesn't exist)");
                return new ResponseEntity<String>("Task doesn't exist", HttpStatus.BAD_REQUEST);
            }

            if (tagRepository.checkrecords(newtag.getTag(), authcreds[0])!=0){
                logger.warn("create task tag call end (Tag already exists)");
                return new ResponseEntity<String>("Tag already exists", HttpStatus.BAD_REQUEST);
            }

            if (tagRepository.checkrecords(newtag.getTag(), authcreds[0])>10){
                logger.warn("create task tag call end (Maximum number of tags)");
                return new ResponseEntity<String>("Maximum number of tags ", HttpStatus.BAD_REQUEST);
            }

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                UserTasks task = taskRepository.getTask(newtag.getTask(), authcreds[0]);

                if (tagRepository.checkrecords(newtag.getTag(), authcreds[0])!=0){

                    TaskTags tag = tagRepository.getTag(newtag.getTag(), authcreds[0]);
                    tag.setUsertask(task);

                    tag.updatetime();
                    tagRepository.save(tag);
                    logger.info("create task tag call end: Succesful");

                    return new ResponseEntity( HttpStatus.CREATED);
                }

                TaskTags tag = new TaskTags(newtag);

                tag.setAppuser(user);
                tag.setUsertask(task);

                tag.updatetime();
                tagRepository.save(tag);

                logger.info("create task tag call end: Succesful");
                return new ResponseEntity(HttpStatus.CREATED);
            }

            logger.info("create task tag call end");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        logger.info("create task tag call end");
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(path = "/v1/user/showtag", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> viewtag(@RequestHeader("Authorization") String authheader) {
        logger.info("view tag call start");
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                List<String> tags = new ArrayList<>();

                for (TaskTags tag : user.getUsertags()){
                    tags.add(tag.getTag());
                }
                logger.info("view tag call end");
                return new ResponseEntity<List<String>>(tags, HttpStatus.OK);

            }
            logger.info("view tag call end");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        logger.info("view tag call end");
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }

//

    @DeleteMapping(path = "/v1/task/removetag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removetag(@RequestBody CreateTag oldtag, @RequestHeader("Authorization") String authheader) {
        logger.info("remove tag call start");
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds != null) {

            AppUser user = userRepository.finduserbyusername(authcreds[0]);
            boolean fields = false;

            if (userRepository.checkrecords(authcreds[0])==0){
                logger.info("remove tag call end");
                return new ResponseEntity<String>("User Doesn't exist", HttpStatus.BAD_REQUEST);
            }

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                if (tagRepository.checkrecords(oldtag.getTag(), user.getUsername())==0) {
                    logger.info("remove tag call end");
                    return new ResponseEntity<String>("Tag does not exist", HttpStatus.BAD_REQUEST);
                }

                if (taskRepository.checkrecords(oldtag.getTask(), authcreds[0])==0){
                    logger.info("remove tag call end");
                    return new ResponseEntity<String>("Task doesn't exist", HttpStatus.BAD_REQUEST);
                }

                UserTasks task = taskRepository.getTask(oldtag.getTask(), authcreds[0]);

                List<TaskTags> newlist = task.getTasktags();

                TaskTags tag = tagRepository.getTag(oldtag.getTag(), authcreds[0]);

                newlist.remove(tag);

                task.setTasktags(newlist);

                taskRepository.save(task);


                logger.info("remove tag call end");
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

            logger.info("remove tag call end");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        }

        logger.info("remove tag call end");
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(path = "/v1/tag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletetag(@RequestBody CreateTag oldtag, @RequestHeader("Authorization") String authheader) {
       logger.info("delete tag call start");
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds != null) {

            AppUser user = userRepository.finduserbyusername(authcreds[0]);
            boolean fields = false;

            if (userRepository.checkrecords(authcreds[0])==0){
                logger.info("delete tag call end");
                return new ResponseEntity<String>("User Doesn't exist", HttpStatus.BAD_REQUEST);
            }

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                if (tagRepository.checkrecords(oldtag.getTag(), user.getUsername())==0) {
                    logger.info("delete tag call end");
                    return new ResponseEntity<String>("Tag does not exist", HttpStatus.BAD_REQUEST);
                }

                TaskTags tag = tagRepository.getTag(oldtag.getTag(), authcreds[0]);

                tagRepository.delete(tag);


                logger.info("delete tag call end");
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

            logger.info("delete tag call end");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        }

        logger.info("delete tag call end");
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