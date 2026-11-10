package project.fooddelivery.api.configuration.seaweedfs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.time.Duration;

@Configuration
@EnableConfigurationProperties(SeaweedProperties.class)
public class S3Config {


    @Bean
    S3Client s3Client(SeaweedProperties seaweedProperties){
        return S3Client.builder()
                .endpointOverride(URI.create(seaweedProperties.endpoint()))
                .region(Region.of(seaweedProperties.region()))
                .credentialsProvider(credentials(seaweedProperties))
                .forcePathStyle(true)
                .overrideConfiguration( config -> config
                        .apiCallTimeout(Duration.ofSeconds(60))
                        .apiCallAttemptTimeout(Duration.ofSeconds(20)))
                .build();
    }


    @Bean
    S3Presigner s3Presigner(SeaweedProperties seaweedProperties) {
        return S3Presigner.builder()
                .endpointOverride(URI.create(seaweedProperties.endpoint()))
                .region(Region.of(seaweedProperties.region()))
                .credentialsProvider(credentials(seaweedProperties))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }

    private static StaticCredentialsProvider credentials(SeaweedProperties seaweedProperties){
        return StaticCredentialsProvider
                .create(AwsBasicCredentials.create(seaweedProperties.accessKey(), seaweedProperties.secretKey()));
    }
}
