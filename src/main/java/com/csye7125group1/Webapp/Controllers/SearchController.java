package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.SearchTask;
import com.csye7125group1.Webapp.Repositories.SearchTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchTaskRepository searchTaskRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/v1/search/task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> searchtask(@Valid @RequestBody SearchTask searchquery, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        logger.info("Searching tasks for query: Taskname = " + searchquery.getTask());

        if (authcreds != null) {

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                Iterable<SearchTask> tasklist = searchTaskRepository.findByTaskContaining(searchquery.getTask());

                List<String> searchlist = new ArrayList<>();
                for (SearchTask task : tasklist) {
                    if (task.getUsername() == authcreds[0]) {
                        searchlist.add("Task: "+ task.getTask() + "Summary: " + task.getSummary());
                    }
                }

                return new ResponseEntity<List<String>>(searchlist, HttpStatus.OK);
            }
            logger.error("Searching tasks for query: Taskname = " + searchquery.getTask() + "Unauthorized");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        logger.error("Searching tasks for query: Taskname = " + searchquery.getTask() + "Unauthorized");
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping(path = "/v1/search/summary", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> searchsummary(@Valid @RequestBody SearchTask searchquery, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        logger.info("Searching tasks for query: Summary = " + searchquery.getSummary());

        if (authcreds != null) {

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                Iterable<SearchTask> tasklist = searchTaskRepository.findBySummaryContaining(searchquery.getSummary());

                List<String> searchlist = new ArrayList<>();
                for (SearchTask task : tasklist) {
                    if (task.getUsername() == authcreds[0]) {
                        searchlist.add("Task: "+ task.getTask() + "Summary: " + task.getSummary());
                    }
                }

                return new ResponseEntity<List<String>>(searchlist, HttpStatus.OK);
            }
            logger.error("Searching tasks for query: Summary = " + searchquery.getSummary() + "Unauthorized");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        logger.error("Searching tasks for query: Summary = " + searchquery.getSummary() + "Unauthorized");
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }

}

//        Iterable<SearchTask> tasklist = searchTaskRepository.findAll();
//
//        for (SearchTask task : tasklist) {
//            System.out.println(task.getTask());
//        }
//
//        logger.info("list tasks call end");
//        return new ResponseEntity<SearchTask>(responseuser, HttpStatus.CREATED);
//    }
//
//}
