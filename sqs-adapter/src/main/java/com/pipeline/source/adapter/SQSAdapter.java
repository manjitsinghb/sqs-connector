package com.pipeline.source.adapter;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.util.StringUtils;

public class SQSAdapter {

    public static AmazonSQS buildClient(SQSConfiguration sqsConfiguration){
        final AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withRegion(sqsConfiguration.getRegion())
                .build();
        return amazonSQS;
    }


}
