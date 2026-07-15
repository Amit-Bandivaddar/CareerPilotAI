package com.amit.careerpilotai.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") + File.separator + "uploads";

    public String uploadResume(MultipartFile file) throws IOException {

        File uploadFolder = new File(UPLOAD_DIR);

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        String fileName = file.getOriginalFilename();

        File destination = new File(uploadFolder, fileName);

        file.transferTo(destination);

        return fileName;
    }
}