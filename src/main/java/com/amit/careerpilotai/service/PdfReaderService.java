package com.amit.careerpilotai.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PdfReaderService {

    public String extractText(String filePath) {

        try {

            File file = new File(filePath);

            PDDocument document = Loader.loadPDF(file);

            PDFTextStripper pdfStripper = new PDFTextStripper();

            String text = pdfStripper.getText(document);

            document.close();

            return text;

        } catch (IOException e) {

            e.printStackTrace();

            return "Unable to read PDF";

        }
    }
}
