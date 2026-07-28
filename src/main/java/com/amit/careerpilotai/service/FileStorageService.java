package com.amit.careerpilotai.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

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

        return destination.getAbsolutePath();
    }
    public String readResume(String filePath) throws IOException {

        File file = new File(filePath);

        try (PDDocument document = Loader.loadPDF(file)) {

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(document);
        }
    }
}