package project.fooddelivery.api.configuration.seaweedfs;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "seaweed.s3")
public record SeaweedProperties(
        @NotBlank String endpoint,
        @NotBlank String region,
        @NotBlank String accessKey,
        @NotBlank String secretKey,
        @NotBlank String bucket,
        boolean createBucketOnStartup,
        @Value("${seaweed.s3.presing-ttl}")
        Duration presignTtl
) {
}
