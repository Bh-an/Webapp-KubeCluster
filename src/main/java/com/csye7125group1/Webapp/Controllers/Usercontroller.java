package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.CreateUser;
import com.csye7125group1.Webapp.DataClasses.UpdateUser;
import com.csye7125group1.Webapp.Entities.AppUser;
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
public class Usercontroller {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;

    @PostMapping(path = "/v1/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUser> createuser(@Valid @RequestBody CreateUser newuser){
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
        return new ResponseEntity<AppUser>(user, HttpStatus.CREATED);
    }

    @PutMapping(path = "/v1/user/self", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateuser(@RequestBody UpdateUser updateuser, @RequestHeader("Authorization") String authheader){
        String[] authcreds = authenticator.getauthcreds(authheader);

        if (authcreds!=null){

            AppUser user = userRepository.finduserbyusername(authcreds[0]);
            boolean fields = false;
            if (passwordEncoder.matches(authcreds[1], user.getPassword())){
                if (updateuser.getUsername() != null){
                    if(!updateuser.getUsername().matches(user.getUsername())) {
                        return new ResponseEntity(HttpStatus.BAD_REQUEST);
                    }
                }
                if (updateuser.getFirst_name() != null){
                    user.setFirst_name(updateuser.getFirst_name());
                    fields = true;
                }
                if (updateuser.getMiddle_name() != null){
                    user.setMiddle_name(updateuser.getMiddle_name());
                    fields = true;
                }
                if (updateuser.getLast_name() != null){
                    user.setLast_name(updateuser.getLast_name());
                    fields = true;
                }
                if (updateuser.getPassword() != null){
                    String password = updateuser.getPassword();
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
