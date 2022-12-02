package com.csye7125group1.Webapp.Repositories;

import com.csye7125group1.Webapp.DataClasses.SearchTask;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchTaskRepository
        extends ElasticsearchRepository<SearchTask, String> {

    List<SearchTask> findByTask(String task);

    List<SearchTask> findBySummaryContaining(String summary);

}


