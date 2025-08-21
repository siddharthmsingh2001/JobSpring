package com.jobspring.jobspring.service;

import com.jobspring.jobspring.entity.AccountType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ProfilePhotoService {

    private static final String BASE_DIR = "profile-photos";

    public String storeProfilePhoto(Long accountId, MultipartFile multipartFile, AccountType accountType) throws IOException {
        String ext = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        if (ext == null || ext.isBlank()) {
            ext = "jpeg"; // default
        }
        String filename = accountId + "." + ext.toLowerCase();
        Path uploadDir = Paths.get(BASE_DIR, accountType.getName().toLowerCase());
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(filename);
        Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }

    public void deleteProfilePhoto(AccountType accountType, String filename) throws IOException {
        Path path = Paths.get(BASE_DIR, accountType.getName().toLowerCase() ,filename);
        Files.deleteIfExists(path);
    }

    public String getPhotoUrl(AccountType accountType, String filename) {
        return "/" + BASE_DIR + "/" + accountType.getName().toLowerCase() + "/" + filename;
        // Accessible because of MvcConfig
    }
}
