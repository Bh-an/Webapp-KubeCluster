package com.csye7125group1.Webapp.Repositories;

import com.csye7125group1.Webapp.Entities.AppUser;
import com.csye7125group1.Webapp.Entities.UserLists;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<UserLists, Integer> {

    @Query("select u from users u where u.username = ?1")
    public AppUser finduserbyusername(String username);

    @Query("select count(u) from users u where u.username = ?1")
    public long checkrecords(String username);
}
