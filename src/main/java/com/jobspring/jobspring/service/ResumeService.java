package com.jobspring.jobspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private static final String BASE_DIR = "resumes";
    private Path foundFile;

    public String storeResume(Long accountId, MultipartFile multipartFile) throws IOException{
        String ext = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        if (ext == null || !ext.equalsIgnoreCase("pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed for resume upload.");
        }

        String filename = accountId + ".pdf";
        Path uploadDir = Paths.get(BASE_DIR);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(filename);
        Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }

    public void deleteResume(String filename) throws IOException {
        Path path = Paths.get(BASE_DIR, filename);
        Files.deleteIfExists(path);
    }

    public String getResumeUrl(String filename) {
        return "/" + BASE_DIR + "/" + filename;
        // Accessible via MvcConfig (like photos)
    }

    public Resource getFileAsResource(String fileName) throws IOException {
        Path path = Paths.get(BASE_DIR);
        Files.list(path).forEach(
                file -> {
                    if(file.getFileName().toString().equals(fileName)){
                        foundFile = file;
                    }
                }
        );
        if(foundFile!=null){
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }
}
