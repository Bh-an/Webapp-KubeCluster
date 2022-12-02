package com.csye7125group1.Webapp.Services;
import java.util.List;


import com.csye7125group1.Webapp.DataClasses.SearchTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaPublishService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaPublishService.class);

    @Autowired
    private KafkaTemplate<String, SearchTask> kafkaTemplate;

    @Value(value = "${spring.kafka.topic}")
    private String kafkaTopic;

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    //    String kafkaTopic = "topic1";

    public void sendMessage(SearchTask task) {

        ListenableFuture<SendResult<String, SearchTask>> future =
                kafkaTemplate.send(kafkaTopic, task);

        future.addCallback(new ListenableFutureCallback<SendResult<String, SearchTask>>() {

            @Override
            public void onSuccess(SendResult<String, SearchTask> result) {
                logger.info("Sent message=[" + task +
                        "] with offset=[" + result.getRecordMetadata().offset() + "] to topic: " + kafkaTopic);
            }
            @Override
            public void onFailure(Throwable ex) {
                logger.info("Unable to send message=["
                        + task + "] due to : " + ex.getMessage());
            }
        });
    }

    public void send(SearchTask task) {
        logger.info("Sending User Json Serializer : {}", task);
        kafkaTemplate.send(kafkaTopic, task);
    }

    public void sendList(List<SearchTask> tasklist) {
        logger.info("Sending UserList Json Serializer : {}", tasklist);
        for (SearchTask task : tasklist) {
            kafkaTemplate.send(kafkaTopic, task);
        }
    }

}
