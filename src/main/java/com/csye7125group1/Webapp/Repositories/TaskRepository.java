package com.csye7125group1.Webapp.Repositories;

import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.UserLists;
import com.csye7125group1.Webapp.Entities.UserTasks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<UserTasks, String> {

//    @Query("select u from usertasks u where u.username = ?1")
//    public AppUser finduserbyusername(String username);
//
//    @Query("select count(u) from users u where u.username = ?1")
//    public long checkrecords(String username);

    @Query("select u from usertasks u where u.task = ?1 and u.userlist_task.appuser_list.username = ?2")
    public UserTasks getTask(String task, String username);

    @Query("select count(u) from usertasks u where u.task = ?1 and u.userlist_task.appuser_list.username = ?2")
    public long checkrecords(String task, String username);
}
