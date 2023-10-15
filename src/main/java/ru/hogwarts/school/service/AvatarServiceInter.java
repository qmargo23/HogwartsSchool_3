package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.nio.file.Path;

public interface AvatarServiceInter {
    void uploadAvatar(Long id, MultipartFile avatar) throws IOException;

    byte[] generateImagePreview(Path filePath) throws IOException;

    Avatar findAvatar(Long studentId);

    String getExtension(String fileName);
}