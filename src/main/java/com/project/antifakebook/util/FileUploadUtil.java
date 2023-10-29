package com.project.antifakebook.util;



import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
    public static String checkFileType(MultipartFile file) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        if (contentType != null) {
            if (contentType.startsWith("image")) {
                return "Image";
            } else if (contentType.startsWith("video")) {
                return "Video";
            } else {
                return "Unknown";
            }
        } else {

            if (fileName != null && (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif"))) {
                return "Image";
            } else if (fileName != null && (fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mkv") || fileName.endsWith(".mov"))) {
                return "Video";
            } else {
                return "Unknown";
            }
        }
    }
    public static Set<String> fileTypeSet(MultipartFile[] files) {
        Set<String> stringSet = new HashSet<>();
        Arrays.asList(files).forEach(file -> stringSet.add(FileUploadUtil.checkFileType(file)));
        return stringSet;
    }
}

