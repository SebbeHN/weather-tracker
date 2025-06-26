package com.example.weather.service;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

@Singleton
public class WeatherAlertService {

    private final SnsClient snsClient;
    private final String topicArn;

    public WeatherAlertService(
            @Value("${aws.region}") String region,
            @Value("${aws.credentials.access-key-id}") String accessKeyId,
            @Value("${aws.credentials.secret-access-key}") String secretAccessKey,
            @Value("${aws.sns.topic-arn}") String topicArn
    ) {
        this.topicArn = topicArn;

        this.snsClient = SnsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)
                ))
                .build();
    }

    public void sendAlert(String message) {
        PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(topicArn)
                .build();

        snsClient.publish(request);
    }
}
