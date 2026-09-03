package com.amazon.aws.sample.sqs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.QueueNameExistsException;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SqsService {

    @Value("${aws.sqs.queue.name}")
    public String queueName;
    @Value("${aws.sqs.queue.message}")
    public String queueMessage;

    public void sendMessage(){
        final SqsClient sqs = SqsClient.builder()
                .region(Region.US_EAST_1).build();

        try {
            sqs.createQueue(CreateQueueRequest.builder().queueName(queueName).build());
        } catch (QueueNameExistsException e) {
            // Queue already exists, ignore
        }

        String queueUrl = sqs.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build()).queueUrl();

        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(queueMessage)
                .delaySeconds(5)
                .build();
        sqs.sendMessage(sendMsgRequest);
    }
}
