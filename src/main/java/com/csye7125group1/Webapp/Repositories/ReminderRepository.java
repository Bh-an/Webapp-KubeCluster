package com.csye7125group1.Webapp.Repositories;

import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.TaskReminders;
import com.csye7125group1.Webapp.Entities.UserLists;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReminderRepository extends CrudRepository<TaskReminders, String> {

    @Query("select u from taskreminders u where u.reftaskid = ?1")
    public TaskReminders findReminderByTaskId(String reftaskid);

//    @Query("select count(u) from users u where u.username = ?1")
//    public long checkrecords(String username);
}
