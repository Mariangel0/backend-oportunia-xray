package edu.backend.oportuniabackend.aws
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.textract.TextractClient

@Configuration
class AWSConfig(
    @Value("\${aws.region}")
    private val awsRegion: String
) {
    @Bean
    fun s3Client(): S3Client = S3Client.builder()
        .region(Region.of(awsRegion))
        .build()

    @Bean
    fun textractClient(): TextractClient = TextractClient.builder()
        .region(Region.of(awsRegion))
        .build()
}