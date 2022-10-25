package com.csye7125group1.Webapp.Entities;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="userlists")
@Table(name="userlists")
public class UserLists {

    @Id
    private String listid;
    private String listname;
    private LocalDateTime list_created;
    private LocalDateTime list_updated;

    public UserLists(String listname) {
        this.listname = listname;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "refuser", referencedColumnName = "userid")
    private AppUser appuser;

    @OneToMany(mappedBy = "userlist", fetch = FetchType.EAGER)
    private List<UserTasks> usertasks = new ArrayList<>();



}
