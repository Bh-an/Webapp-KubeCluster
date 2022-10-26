package com.csye7125group1.Webapp.Entities;


import com.csye7125group1.Webapp.DataClasses.CreateList;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name="userlists")
@Table(name="userlists")
public class UserLists {

    @Id
    private String listid;
    private String listname;
    private LocalDateTime list_created;
    private LocalDateTime list_updated;

    public UserLists(String listname) {
        this.listid = UUID.randomUUID().toString();
        this.listname = listname;
        this.list_created = LocalDateTime.now();
        this.list_updated = LocalDateTime.now();
    }

    public UserLists(CreateList newlist) {
        this.listid = UUID.randomUUID().toString();
        this.listname = newlist.getListname();
        this.list_created = LocalDateTime.now();
        this.list_updated = LocalDateTime.now();
    }

    public UserLists(){};

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "refuser", referencedColumnName = "userid")
    private AppUser appuser_list;

    @OneToMany(mappedBy = "userlist_task", fetch = FetchType.EAGER)
    private List<UserTasks> usertasks_list = new ArrayList<>();

    public void addList(UserTasks task){
        this.usertasks_list.add(task);
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public LocalDateTime getList_created() {
        return list_created;
    }

    public void setList_created(LocalDateTime list_created) {
        this.list_created = list_created;
    }

    public LocalDateTime getList_updated() {
        return list_updated;
    }

    public void setList_updated(LocalDateTime list_updated) {
        this.list_updated = list_updated;
    }

    public AppUser getAppuser() {
        return appuser_list;
    }

    public void setAppuser(AppUser appuser) {
        this.appuser_list = appuser;
    }

    public List<UserTasks> getUsertasks() {
        return usertasks_list;
    }

    public void setUsertasks(List<UserTasks> usertasks) {
        this.usertasks_list = usertasks;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }
}
