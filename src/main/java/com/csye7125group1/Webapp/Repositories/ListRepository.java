package com.csye7125group1.Webapp.Repositories;

import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.UserLists;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ListRepository extends CrudRepository<UserLists, String> {

//    @Query("select u from userlists u where u.username = ?1")
//    public AppUser finduserbyusername(String username);
//
//    @Query("select count(u) from userlists u where u.username = ?1")
//    public long checkrecords(String username);

    @Query("select u from userlists u where u.listname = ?1 and u.appuser_list.username = ?2")
    public UserLists getList(String listname, String username);
}
