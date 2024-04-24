package com.pipeline.source;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.pipeline.source.adapter.SQSAdapter;
import com.pipeline.source.adapter.SQSConfiguration;

import java.util.List;

public class SQSSource {

    private String queueUrl;

    private AmazonSQS amazonSQS;

    private int waitTimeSeconds;

    public SQSSource(SQSConfiguration sqsConfiguration){
       this.amazonSQS = SQSAdapter.buildClient(sqsConfiguration);
       this.queueUrl = sqsConfiguration.getQueueUrl();
       this.waitTimeSeconds = sqsConfiguration.getWaitTimeSeconds();
    }

    public List<Message> receiveMessage(){
        ReceiveMessageRequest receive_request = new ReceiveMessageRequest()
                .withQueueUrl(queueUrl)
                .withWaitTimeSeconds(waitTimeSeconds);
        ReceiveMessageResult receiveMessageResult = amazonSQS.receiveMessage(receive_request);
        return receiveMessageResult.getMessages();
    }
}
