package com.pipeline.sink;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pipeline.source.adapter.SQSAdapter;
import com.pipeline.source.adapter.SQSConfiguration;

import java.util.List;

public class SQSSink<V extends String> {

    private String queueUrl;

    private AmazonSQS amazonSQS;

    private ObjectMapper objectMapper;

    public SQSSink(SQSConfiguration sqsConfiguration){
        this.amazonSQS = SQSAdapter.buildClient(sqsConfiguration);
        this.queueUrl = sqsConfiguration.getQueueUrl();
        this.objectMapper = new ObjectMapper();
    }

    public SQSSink(SQSConfiguration sqsConfiguration, ObjectMapper objectMapper){
        this.amazonSQS = SQSAdapter.buildClient(sqsConfiguration);
        this.queueUrl = sqsConfiguration.getQueueUrl();
        this.objectMapper = objectMapper;
    }

    public String publishMessage(Object message){
        SendMessageResult sendMessage = null;
        try {
            sendMessage = amazonSQS.sendMessage(queueUrl, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return sendMessage.getMessageId();
    }
}
