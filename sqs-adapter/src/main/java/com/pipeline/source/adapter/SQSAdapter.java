package com.pipeline.source.adapter;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.internal.securitytoken.RoleInfo;
import com.amazonaws.auth.profile.internal.securitytoken.STSProfileCredentialsServiceProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.util.StringUtils;

import java.util.Optional;

public class SQSAdapter {

    public static AmazonSQS buildClient(SQSConfiguration sqsConfiguration){
        final AmazonSQSClientBuilder amazonSQSAsyncClientBuilder= AmazonSQSClientBuilder.standard()
                .withRegion(sqsConfiguration.getRegion());
        Optional<AWSCredentialsProvider> awsCredentialsProvider = getAWSCredentials(sqsConfiguration);
        awsCredentialsProvider.ifPresent(amazonSQSAsyncClientBuilder::withCredentials);
        return amazonSQSAsyncClientBuilder.build();
    }

    private static Optional<AWSCredentialsProvider> getAWSCredentials(SQSConfiguration sqsConfiguration) {
        if(!StringUtils.isNullOrEmpty(sqsConfiguration.getRole())){
            return Optional.of(new STSProfileCredentialsServiceProvider(new RoleInfo().withRoleArn(sqsConfiguration.getRole())));
        }
        if(!StringUtils.isNullOrEmpty(sqsConfiguration.getAccess()) && !StringUtils.isNullOrEmpty(sqsConfiguration.getSecret())){
            return Optional.of(new AWSStaticCredentialsProvider(new BasicAWSCredentials(sqsConfiguration.getAccess(), sqsConfiguration.getSecret())));
        }
        return Optional.empty();
    }


}
