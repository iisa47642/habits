package ru.habits.spring.habits.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String saveFile(MultipartFile file) {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadPath, filename);

        try {
            Files.createDirectories(filePath.getParent());
            file.transferTo(filePath);
        }  catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return "/uploads/" + filename; // Возвращаем относительный URL
    }
}
