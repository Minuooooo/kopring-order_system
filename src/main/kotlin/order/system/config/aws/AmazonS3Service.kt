package order.system.config.aws

import com.amazonaws.services.s3.AmazonS3
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class AmazonS3Service(private val amazonS3Client: AmazonS3) {

    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucketName: String

    fun uploadFile(multipartFile: MultipartFile): String {

    }

    private fun validateFileExists(multipartFile: MultipartFile) {
        if (multipartFile.isEmpty) {
            throw
        }
    }
}