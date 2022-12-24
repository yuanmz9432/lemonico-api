package com.lemonico.cloud.config;



import com.lemonico.cloud.props.AwsProps;
import com.lemonico.cloud.props.S3Props;
import com.lemonico.cloud.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/**
 * AWSサービスディフォルトクライアント設定
 */
@EnableConfigurationProperties(AwsProps.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Configuration
public class AwsDefaultServiceConfig
{
    /**
     * AWSプロパティ
     */
    private final AwsProps awsProps;

    @Bean
    public S3Service s3Service(S3Presigner s3Presigner, S3Client s3Client) {
        S3Props props = awsProps.getS3().get("lemonico-sendbox");
        return S3Service.builder()
            .s3Presigner(s3Presigner)
            .s3Client(s3Client)
            .prefix(props.getPrefix())
            .bucketName(props.getBucketName())
            .preSignedUrlValidMinutes(props.getPreSignedUrlValidMinutes())
            .build();
    }
}