package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.DataClasses.SearchTask;
import com.csye7125group1.Webapp.Repositories.SearchTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchTaskRepository searchTaskRepository;

    @GetMapping(path = "/v1/search/list")
    public String listTasks() {
        logger.info("list tasks call start");
        Iterable<SearchTask> tasklist = searchTaskRepository.findAll();

        for (SearchTask task : tasklist) {
            System.out.println(task.getTask());
        }

        logger.info("list tasks call end");
        return "Message sent successfully to the Kafka topic test";
    }

}
