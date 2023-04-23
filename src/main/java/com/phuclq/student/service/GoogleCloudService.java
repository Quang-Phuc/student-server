package com.phuclq.student.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class GoogleCloudService {
    @Autowired
    Environment environment;

    public Storage getStorage() throws IOException {
        String projectId = environment.getProperty("gcp.project-id");
        String pathAccountService = environment.getProperty("gcp.path-accout-service");
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(new
                        FileInputStream(pathAccountService))).build();
        return storageOptions.getService();
    }

    public String getBucketName() {
        return environment.getProperty("gcp.bucket-name");
    }
}
