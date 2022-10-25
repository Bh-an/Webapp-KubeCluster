package com.csye7125group1.Webapp.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="tasktags")
@Table(name="tasktags")
public class TaskTags {

    @Id
    private String tagid;
    private String reftaskid;
    private String tag;
    private LocalDateTime tag_created;
    private LocalDateTime tag_updated;

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "reftask", referencedColumnName = "taskid")
    private UserTasks usertask;

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "refuser", referencedColumnName = "userid")
    private AppUser appuser;

}
