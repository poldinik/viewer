package it.unifi.ing.hci.resource;

import it.unifi.ing.hci.model.FormData;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

abstract public class CommonResource {

    private final static String TEMP_DIR = System.getProperty("java.io.tmpdir");

    @ConfigProperty(name = "bucket.name")
    public String bucketName;

    protected PutObjectRequest buildPutRequest(FormData formData) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String formatted = format.format(date);
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(formatted + "-" + formData.fileName)
                .contentType(formData.mimeType)
                .build();
    }

    protected GetObjectRequest buildGetRequest(String objectKey) {
        return GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
    }

    protected File tempFilePath() {
        return new File(TEMP_DIR, new StringBuilder().append("s3AsyncDownloadedTemp")
                .append((new Date()).getTime()).append(UUID.randomUUID())
                .append(".").append(".tmp").toString());
    }

    protected File uploadToTemp(InputStream data) {
        File tempPath;
        try {
            tempPath = File.createTempFile("uploadS3Tmp", ".tmp");
            Files.copy(data, tempPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return tempPath;
    }
}
