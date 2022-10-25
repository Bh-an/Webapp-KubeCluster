package com.csye7125group1.Webapp.Entities;

import com.csye7125group1.Webapp.DataClasses.CreateUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name="users")
@Table(name="users")
public class AppUser {
    @Id
    private String userid;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String username;
    private LocalDateTime account_created;
    private LocalDateTime account_updated;


    @OneToMany(mappedBy = "appuser", fetch = FetchType.EAGER)
    private List<UserLists> userlists = new ArrayList<>();

    @OneToMany(mappedBy = "appuser", fetch = FetchType.EAGER)
    private List<TaskTags> usertags = new ArrayList<>();

    @JsonIgnore
    private String password;

    public AppUser(String first_name, String last_name, String username, String password) {
        this.userid = UUID.randomUUID().toString();
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.account_created = LocalDateTime.now();
        this.account_updated = LocalDateTime.now();
        this.password = password;
    }

    public AppUser(CreateUser newuser) {

        this.userid = UUID.randomUUID().toString();
        this.first_name = newuser.getFirst_name();
        this.last_name = newuser.getLast_name();
        this.username = newuser.getUsername();
        this.account_created = LocalDateTime.now();
        this.account_updated = LocalDateTime.now();
        this.password = newuser.getPassword();
        this.userlists.add(new UserLists("default_list"));
    }

    public AppUser() {
    }

    public String getId() {
        return userid;
    }

    public void setId(String id) {
        this.userid = userid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setMiddle_name(String first_name) {
        this.first_name = middle_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getAccount_created() {
        return account_created;
    }

    public void setAccount_created(LocalDateTime account_created) {
        this.account_created = account_created;
    }

    public LocalDateTime getAccount_updated() {
        return account_updated;
    }

    public void setAccount_updated(LocalDateTime account_updated) {
        this.account_updated = account_updated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Appuser{" +
                "id='" + userid + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", username='" + username + '\'' +
                ", account_created=" + account_created +
                ", account_updated=" + account_updated +
                '}';
    }

    public void accountupdate() {
        this.account_updated = LocalDateTime.now();
    }
}
