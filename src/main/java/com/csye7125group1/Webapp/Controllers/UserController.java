package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.CreateList;
import com.csye7125group1.Webapp.DataClasses.CreateUser;
import com.csye7125group1.Webapp.DataClasses.UpdateUser;
import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.UserLists;
import com.csye7125group1.Webapp.Repositories.ListRepository;
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
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;

    @PostMapping(path = "/v1/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createuser(@Valid @RequestBody CreateUser newuser){
        if(!EmailCheck.checkEmail(newuser.getUsername())){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if(userRepository.checkrecords(newuser.getUsername())!=0){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        String password = newuser.getPassword();
        newuser.setPassword(passwordEncoder.encode(password));
        AppUser user = new AppUser(newuser);
        userRepository.save(user);

        AppUser tempuser = userRepository.finduserbyusername(newuser.getUsername());
        UserLists newlist = new UserLists(new CreateList("default"));
        newlist.setAppuser(tempuser);
        listRepository.save(newlist);

        AppUser responseuser = userRepository.finduserbyusername(newuser.getUsername());
        return new ResponseEntity<AppUser>(responseuser, HttpStatus.CREATED);
    }

    @PutMapping(path = "/v1/user/self", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUser> updateuser(@RequestBody UpdateUser newuser, @RequestHeader("Authorization") String authheader){
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            AppUser user = userRepository.finduserbyusername(authcreds[0]);
            boolean fields = false;
            if (passwordEncoder.matches(authcreds[1], user.getPassword())){
//                if (updateuser.getUsername() != null){
//                    if(!updateuser.getUsername().matches(user.getUsername())) {
//                        return new ResponseEntity(HttpStatus.BAD_REQUEST);
//                    }
//                }
                if (newuser.getFirst_name() != null){
                    user.setFirst_name(newuser.getFirst_name());
                    fields = true;
                }
                if (newuser.getMiddle_name() != null){
                    user.setMiddle_name(newuser.getMiddle_name());
                    fields = true;
                }
                if (newuser.getLast_name() != null){
                    user.setLast_name(newuser.getLast_name());
                    fields = true;
                }
                if (newuser.getUsername() != null){
                    user.setUsername(newuser.getUsername());
                    fields = true;
                }
                if (newuser.getPassword() != null){
                    String password = newuser.getPassword();
                    user.setPassword(passwordEncoder.encode(password));
                    fields = true;
                }

                if (!fields){
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
                user.accountupdate();
                userRepository.save(user);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

    }



