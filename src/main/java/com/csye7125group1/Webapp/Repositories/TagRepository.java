package com.csye7125group1.Webapp.Repositories;

import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.TaskComments;
import com.csye7125group1.Webapp.Entities.TaskTags;
import com.csye7125group1.Webapp.Entities.UserLists;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<TaskTags, String> {

//    @Query("select u from tasktags u where u.reftaskid = ?1")
//    public AppUser finduserbyusername(String reftaskid);
//
//    @Query("select count(u) from tasktags u where u.reftaskid = ?1")
//    public long checkCount(String reftaskid);

    @Query("select u from tasktags u where u.tag = ?1 and u.appuser_tag.username = ?2")
    public TaskTags getTag(String tag, String username);

    @Query("select count(u) from tasktags u where u.tag = ?1 and u.appuser_tag.username = ?2")
    public long checkrecords(String tag, String username);
}
