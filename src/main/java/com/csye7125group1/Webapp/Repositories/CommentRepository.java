package com.csye7125group1.Webapp.Repositories;

import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.TaskComments;
import com.csye7125group1.Webapp.Entities.UserLists;
import com.csye7125group1.Webapp.Entities.UserTasks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<TaskComments, String> {

//    @Query("select u from taskcomments u where u.reftaskid = ?1")
//    public TaskComments findCommentByTaskId(String reftaskid);
//
//    @Query("select count(u) from taskcomments u where u.reftaskid = ?1")
//    public long checkCount(String reftaskid);

    @Query("select u from taskcomments u where u.usertask_comment.task = ?1 and u.usertask_comment.userlist_task.appuser_list.username = ?2")
    public List<TaskComments> getComments(String task, String username);

    @Query("select count(u) from taskcomments u where u.usertask_comment.task = ?1 and u.usertask_comment.userlist_task.appuser_list.username = ?2")
    public long checkrecords(String task, String username);
}
