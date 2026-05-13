package com.nutria.nutria_api.shared.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.PublicAccessType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AzureBlobService {
    private final BlobServiceClient blobServiceClient;

    @Value("${azure.storage.container-name}")
    private String containerName;

    public AzureBlobService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    public String uploadFile(MultipartFile file, Long userId) {
        BlobContainerClient container = blobServiceClient.getBlobContainerClient(containerName);

        if (!container.exists()) {
            container.createWithResponse(null, PublicAccessType.BLOB, null, null);
        } else {
            container.setAccessPolicy(PublicAccessType.BLOB, null);
        }

        String fileName = "user-" + userId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        BlobClient blobClient = container.getBlobClient(fileName);

        try {
            blobClient.upload(file.getInputStream(), file.getSize(), true);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return blobClient.getBlobUrl();
    }

    public void deleteFile(String blobPath) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobPath);
        blobClient.deleteIfExists();
    }
}
