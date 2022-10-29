package com.csye7125group1.Webapp.Repositories;

import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.TaskComments;
import com.csye7125group1.Webapp.Entities.TaskReminders;
import com.csye7125group1.Webapp.Entities.UserLists;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReminderRepository extends CrudRepository<TaskReminders, String> {

//    @Query("select u from taskreminders u where u.reftaskid = ?1")
//    public TaskReminders findReminderByTaskId(String reftaskid);

//    @Query("select count(u) from users u where u.username = ?1")
//    public long checkrecords(String username);

    @Query("select u from taskreminders u where u.usertask_reminder.task = ?1 and u.usertask_reminder.userlist_task.appuser_list.username = ?2")
    public List<TaskReminders> getReminders(String task, String username);

    @Query("select count(u) from taskreminders u where u.usertask_reminder.task = ?1 and u.usertask_reminder.userlist_task.appuser_list.username = ?2")
    public long checkrecords(String task, String username);
}
