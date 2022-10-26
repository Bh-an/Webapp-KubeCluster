package com.csye7125group1.Webapp.Repositories;

import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.TaskTags;
import com.csye7125group1.Webapp.Entities.UserLists;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<TaskTags, String> {

//    @Query("select u from tasktags u where u.reftaskid = ?1")
//    public AppUser finduserbyusername(String reftaskid);
//
//    @Query("select count(u) from tasktags u where u.reftaskid = ?1")
//    public long checkCount(String reftaskid);
}
