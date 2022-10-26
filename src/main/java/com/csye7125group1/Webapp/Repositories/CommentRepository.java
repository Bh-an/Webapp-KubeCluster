package com.csye7125group1.Webapp.Repositories;

import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.TaskComments;
import com.csye7125group1.Webapp.Entities.UserLists;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<TaskComments, String> {

//    @Query("select u from taskcomments u where u.reftaskid = ?1")
//    public TaskComments findCommentByTaskId(String reftaskid);
//
//    @Query("select count(u) from taskcomments u where u.reftaskid = ?1")
//    public long checkCount(String reftaskid);
}
