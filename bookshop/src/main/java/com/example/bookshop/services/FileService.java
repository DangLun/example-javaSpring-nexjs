package com.example.bookshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    private final String imagePath = "src/main/resources/static/images";

    public String saveFile(MultipartFile file) throws Exception {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path directoryPath = Paths.get(imagePath);
            Path filePath = directoryPath.resolve(fileName);

            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Lỗi khi lưu tệp: " + e.getMessage());
        }
    }

    public void removeFile(String fileName) throws Exception {
        try {
            Files.deleteIfExists(Paths.get(imagePath, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Lỗi khi xóa tệp: " + e.getMessage());
        }
    }
}

