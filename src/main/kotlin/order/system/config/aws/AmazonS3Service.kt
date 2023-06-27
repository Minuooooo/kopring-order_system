package order.system.config.aws

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import order.system.exception.situation.EmptyFileException
import order.system.exception.situation.FileUploadFailureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class AmazonS3Service(
        private val amazonS3Client: AmazonS3,

        @Value("\${cloud.aws.s3.bucket}")
        private val bucketName: String
) {

    fun uploadFile(multipartFile: MultipartFile): String {

        validateFileExists(multipartFile)

        val fileName = createFileName(requireNotNull(multipartFile.originalFilename))
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = multipartFile.contentType

        try {
            multipartFile.inputStream.use { inputStream ->
                amazonS3Client.putObject(PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead))
            }
        } catch (e: Exception) {
            throw FileUploadFailureException(e.message)
        }

        return fileName
    }

    private fun validateFileExists(multipartFile: MultipartFile) {
        if (multipartFile.isEmpty) {
            throw EmptyFileException()
        }
    }

    private fun createFileName(fileName: String): String = UUID.randomUUID().toString() + getFileExtension(fileName)

    private fun getFileExtension(fileName: String): String {
        val fileExtensionIndex = fileName.lastIndexOf(".")

        if(!StringUtils.hasText(fileName.substring(fileExtensionIndex))) {
            throw StringIndexOutOfBoundsException()
        }
        return fileName.substring(fileExtensionIndex)
    }
}