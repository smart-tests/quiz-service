package com.smarttest.quizservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    public String processWordFile(MultipartFile file) {
        String content;

        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            content = extractor.getText();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return content;
    }
}
