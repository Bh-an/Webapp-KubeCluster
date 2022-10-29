package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.CreateList;
import com.csye7125group1.Webapp.DataClasses.UpdateList;
import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.UserLists;
import com.csye7125group1.Webapp.Entities.UserTasks;
import com.csye7125group1.Webapp.Repositories.ListRepository;
import com.csye7125group1.Webapp.Repositories.UserRepository;
import com.csye7125group1.Webapp.Utility.Authenticator;
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
public class ListController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;

    @PostMapping(path = "/v1/list/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createlist(@Valid@RequestBody CreateList newlist, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                if (listRepository.checkrecords(newlist.getListname(), authcreds[0])!=0){
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
                UserLists list = new UserLists(new CreateList(newlist.getListname()));

                list.setAppuser(user);
                listRepository.save(list);

                return new ResponseEntity<String>(list.getListname(), HttpStatus.CREATED);
            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(path = "/v1/list/rename", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateuser(@RequestBody UpdateList newlist, @RequestHeader("Authorization") String authheader) {
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                UserLists list = listRepository.getList(newlist.getOldlistname(), authcreds[0]);
                list.setListname(newlist.getNewlistname());

                listRepository.save(list);

                return new ResponseEntity<String>("Old list name: " + newlist.getOldlistname() + "\nNew list name: " + list.getListname(), HttpStatus.CREATED);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(path = "/v1/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> viewlists(@RequestHeader("Authorization") String authheader) {

        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            AppUser user = userRepository.finduserbyusername(authcreds[0]);

            if (passwordEncoder.matches(authcreds[1], user.getPassword())) {

                List<String> lists = new ArrayList<>();

                for (UserLists list : user.getUserlists()){

                    lists.add(list.getListname());
                }

                return new ResponseEntity<List<String>>(lists, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }
}
